package controller.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.ProfessorDAO;
import model.TurmaDAO;
import entidade.Professor;
import entidade.Turma;

@WebServlet(name = "ProfessorController", urlPatterns = "/admin/ProfessorController")
public class ProfessorController extends HttpServlet {

    private TurmaDAO turmaDAO = new TurmaDAO(); 
    private ProfessorDAO professorDAO = new ProfessorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        professorDAO = new ProfessorDAO();
        turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        try {
            if (acao != null) {
                switch (acao) {
                    case "Listar":
                        ArrayList<Professor> listaProfessores = professorDAO.getAll();
                        request.setAttribute("listaProfessores", listaProfessores);
                        rd = request.getRequestDispatcher("/views/admin/professor/listaProfessores.jsp");
                        rd.forward(request, response);
                        break;

                    case "ListarNotas":
                        int professorId = Integer.parseInt(request.getParameter("professorId"));
                        ArrayList<Turma> turmas = turmaDAO.getTurmasProfessor(professorId);
                        request.setAttribute("turmas", turmas);
                        rd = request.getRequestDispatcher("/views/admin/professor/listaNotas.jsp");
                        rd.forward(request, response);
                        break;

                    case "LancarNota":
                        int professor_id = Integer.parseInt(request.getParameter("professorId"));
                        int turmaId = Integer.parseInt(request.getParameter("turmaId"));
                        Turma turma = turmaDAO.getTurma(turmaId);
                        
                        if (turma != null && turma.getProfessorId() == professor_id) {
                            request.setAttribute("turma", turma);
                            rd = request.getRequestDispatcher("/views/admin/professor/formLancarNota.jsp");
                            rd.forward(request, response);
                        } else {
                            request.setAttribute("mensagemErro", "Você não tem permissão para lançar notas nesta turma.");
                            rd = request.getRequestDispatcher("/views/comum/erro.jsp");
                            rd.forward(request, response);
                        }
                        break;

                    default:
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada.");
                        break;
                }
            }
        } catch (SQLException | ServletException | IOException | NumberFormatException e) {
            request.setAttribute("mensagemErro", "Erro ao processar a ação: " + e.getMessage());
            request.getRequestDispatcher("/views/comum/erro.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        professorDAO = new ProfessorDAO();
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String cpf = request.getParameter("cpf");
            String senha = request.getParameter("senha");
            String btEnviar = request.getParameter("btEnviar");

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

            request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
            Logger.getLogger(ProfessorController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
