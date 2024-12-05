package controller.admin;

import entidade.Professor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ProfessorDAO;

@WebServlet(name = "ProfessorController", urlPatterns = "/admin/ProfessorController")
public class ProfessorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = (String) request.getParameter("acao");
        ProfessorDAO professorDAO = new ProfessorDAO();
        Professor professor = new Professor();
        RequestDispatcher rd;
        try {
            if (null != acao) {
                switch (acao) {
                    case "Listar":
                        ArrayList<Professor> listaProfessores = professorDAO.getAll();
                        request.setAttribute("listaProfessores", listaProfessores);
                        rd = request.getRequestDispatcher("/views/admin/professor/listaProfessores.jsp");
                        rd.forward(request, response);
                        break;
                    case "Alterar":
                    case "Excluir":
                        int id = Integer.parseInt(request.getParameter("id"));
                        professor = professorDAO.getProfessor(id);
                        request.setAttribute("professor", professor);
                        request.setAttribute("msgError", "");
                        request.setAttribute("acao", "Alterar");
                        rd = request.getRequestDispatcher("/views/admin/professor/formProfessor.jsp");
                        rd.forward(request, response);
                        break;
                    case "Incluir":
                        request.setAttribute("professor", professor);
                        request.setAttribute("msgError", "");
                        request.setAttribute("acao", acao);
                        rd = request.getRequestDispatcher("/views/admin/professor/formProfessor.jsp");
                        rd.forward(request, response);
                        break;
                    default:
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada.");
                        break;
                }
            }
        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
            request.setAttribute("mensagemErro", "Erro ao processar a ação: " + e.getMessage());
            request.getRequestDispatcher("/views/comum/erro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String cpf = request.getParameter("cpf");
            String senha = request.getParameter("senha");
            String btEnviar = request.getParameter("btEnviar");

            // Verificar se os campos necessários foram preenchidos
            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
                Professor professor = (btEnviar.equals("Alterar") || btEnviar.equals("Excluir"))
                        ? new Professor(id)
                        : new Professor();
                request.setAttribute("professor", professor);
                request.setAttribute("acao", btEnviar);
                request.setAttribute("msgError", "É necessário preencher todos os campos obrigatórios.");
                request.getRequestDispatcher("/views/admin/professor/formProfessor.jsp").forward(request, response);
                return;
            }

            Professor professor = new Professor(id, nome, email, cpf, senha);
            ProfessorDAO professorDAO = new ProfessorDAO();
            RequestDispatcher rd;

            switch (btEnviar) {
                case "Incluir":
                    professorDAO.inserir(professor);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;
                case "Alterar":
                    professorDAO.atualizar(professor);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;
                case "Excluir":
                    professorDAO.excluir(id);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }

            // Redirecionamento para a página de listagem de professores
            request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | SQLException | ServletException ex) {
            request.setAttribute("errorMessage", "Erro ao processar a solicitação: " + ex.getMessage());
            request.getRequestDispatcher("/views/comum/erro.jsp").forward(request, response);
        }
    }

}
