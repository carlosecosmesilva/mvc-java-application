package controller.admin;

import entidade.Disciplina;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.DisciplinaDAO;

@WebServlet(name = "DisciplinaController", urlPatterns = "/admin/DisciplinaController")
public class DisciplinaController extends HttpServlet {

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        RequestDispatcher rd;
        try {
            if (null != acao) {
                switch (acao) {
                    case "Listar":
                        List<Disciplina> disciplinas = disciplinaDAO.listar();
                        request.setAttribute("listaDisciplinas", disciplinas);
                        rd = request.getRequestDispatcher("/views/admin/disciplina/listaDisciplina.jsp");
                        rd.forward(request, response);
                        break;
                    case "Alterar":
                    case "Excluir":
                        int id = Integer.parseInt(request.getParameter("id"));
                        Disciplina disciplina = disciplinaDAO.getDisciplina(id);
                        request.setAttribute("disciplina", disciplina);
                        request.setAttribute("acao", "Alterar");
                        rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
                        rd.forward(request, response);
                        break;
                    case "Incluir":
                        request.setAttribute("acao", acao);
                        rd = request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp");
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
        String btEnviar = request.getParameter("btEnviar");
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String requisito = request.getParameter("requisito");
            String ementa = request.getParameter("ementa");
            int cargaHoraria = Integer.parseInt(request.getParameter("cargaHoraria"));

            // Verificar se os campos obrigatórios estão preenchidos
            if (nome.isEmpty() || requisito.isEmpty() || ementa.isEmpty()) {
                request.setAttribute("disciplina", new Disciplina(id, nome, requisito, ementa, cargaHoraria));
                request.setAttribute("btEnviar", btEnviar);
                request.setAttribute("msgError", "É necessário preencher todos os campos obrigatórios.");
                request.getRequestDispatcher("/views/admin/disciplina/formDisciplina.jsp").forward(request, response);
                return;
            }

            Disciplina disciplina = new Disciplina(id, nome, requisito, ementa, cargaHoraria);
            RequestDispatcher rd;
            switch (btEnviar) {
                case "Incluir":
                    disciplinaDAO.inserir(disciplina);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;
                case "Alterar":
                    disciplinaDAO.atualizar(disciplina);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;
                case "Excluir":
                    disciplinaDAO.excluir(id);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
                default:
                    break;
            }

            // Redirecionamento para a página de listagem de disciplinas
            request.setAttribute("link", "/aplicacaoMVC/admin/DisciplinaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | SQLException | ServletException ex) {
            request.setAttribute("errorMessage", "Erro ao processar a solicitação: " + ex.getMessage());
            request.getRequestDispatcher("/views/comum/erro.jsp").forward(request, response);
        }
    }
}
