package controller.admin;

import entidade.Turma;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.TurmaDAO;

@WebServlet(name = "TurmaController", urlPatterns = "/admin/TurmaController")
public class TurmaController extends HttpServlet {

    private final TurmaDAO turmaDAO = new TurmaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        RequestDispatcher rd;
        try {
            if (acao != null) {
                switch (acao) {
                    case "Listar":
                        List<Turma> listaTurmas = turmaDAO.listar();
                        request.setAttribute("listaTurmas", listaTurmas);
                        rd = request.getRequestDispatcher("/views/admin/turma/listaTurma.jsp");
                        rd.forward(request, response);
                        break;

                    case "Incluir":
                        Turma novaTurma = new Turma();
                        request.setAttribute("turma", novaTurma);
                        request.setAttribute("acao", "Incluir");
                        rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
                        rd.forward(request, response);
                        break;

                    case "Alterar":
                    case "Excluir":
                        int id = Integer.parseInt(request.getParameter("id"));
                        Turma turmaExistente = turmaDAO.getTurma(id);
                        request.setAttribute("turma", turmaExistente);
                        request.setAttribute("acao", acao);
                        rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            int professorId = Integer.parseInt(request.getParameter("professorId"));
            int disciplinaId = Integer.parseInt(request.getParameter("disciplinaId"));
            int alunoId = Integer.parseInt(request.getParameter("alunoId"));
            String codigoTurma = request.getParameter("codigoTurma");
            double nota = Double.parseDouble(request.getParameter("nota"));
            String btEnviar = request.getParameter("btEnviar");

            // Verificar se os campos obrigatórios estão preenchidos
            if (codigoTurma.isEmpty()) {
                Turma turma = (btEnviar.equals("Alterar") || btEnviar.equals("Excluir"))
                        ? turmaDAO.getTurma(id)
                        : new Turma();
                request.setAttribute("turma", turma);
                request.setAttribute("acao", btEnviar);
                request.setAttribute("msgError", "É necessário preencher todos os campos obrigatórios.");
                request.getRequestDispatcher("/views/admin/turma/formTurma.jsp").forward(request, response);
                return;
            }

            Turma turma = new Turma(id, professorId, disciplinaId, alunoId, codigoTurma, nota);
            RequestDispatcher rd;

            switch (btEnviar) {
                case "Incluir":
                    turmaDAO.inserir(turma);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;

                case "Alterar":
                    turmaDAO.atualizar(turma);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;

                case "Excluir":
                    turmaDAO.excluir(id);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }

            // Redirecionamento para a página de listagem de turmas
            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
            request.setAttribute("errorMessage", "Erro ao processar a solicitação: " + e.getMessage());
            request.getRequestDispatcher("/views/comum/erro.jsp").forward(request, response);
        }
    }
}

