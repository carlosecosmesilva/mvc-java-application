package controller.admin;

import entidade.Professor;
import entidade.Turma;
import model.ProfessorDAO;
import model.TurmaDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ProfessorController", urlPatterns = "/admin/ProfessorController")
public class ProfessorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        ProfessorDAO professorDAO = new ProfessorDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;

        try {
            int professorId = Integer.parseInt(request.getParameter("professorId"));

            if (acao != null) {
                switch (acao) {
                    case "Listar":
                        ArrayList<Professor> listaProfessores = professorDAO.getAll();
                        request.setAttribute("listaProfessores", listaProfessores);
                        rd = request.getRequestDispatcher("/views/admin/professor/listaProfessores.jsp");
                        rd.forward(request, response);
                        break;
                    case "ListarNotas":
                        ArrayList<Turma> turmas = turmaDAO.getTurmasPorProfessor(professorId);
                        request.setAttribute("turmas", turmas);
                        rd = request.getRequestDispatcher("/views/admin/professor/listaNotas.jsp");
                        rd.forward(request, response);
                        break;
                    case "LancarNota":
                        int turmaId = Integer.parseInt(request.getParameter("turmaId"));
                        Turma turma = turmaDAO.getTurma(turmaId);
                        if (turma != null && turma.getProfessorId() == professorId) {
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String acao = request.getParameter("acao");

            if ("LancarNota".equals(acao)) {
                int alunoId = Integer.parseInt(request.getParameter("alunoId"));
                int professorId = Integer.parseInt(request.getParameter("professorId"));
                int disciplinaId = Integer.parseInt(request.getParameter("disciplinaId"));
                String codigoTurma = request.getParameter("codigoTurma");
                double nota = Double.parseDouble(request.getParameter("nota"));

                Turma turma = new Turma(professorId, disciplinaId, alunoId, codigoTurma, nota);
                TurmaDAO turmaDAO = new TurmaDAO();
                turmaDAO.lancarNota(turma);

                request.setAttribute("msgOperacaoRealizada", "Nota lançada com sucesso.");
                request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=ListarNotas&professorId=" + professorId);
                RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);
                return;
            }

            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String cpf = request.getParameter("cpf");
            String senha = request.getParameter("senha");
            String btEnviar = request.getParameter("btEnviar");
            int id = Integer.parseInt(request.getParameter("id"));

            Professor professor = new Professor(id, nome, email, cpf, senha);
            ProfessorDAO professorDAO = new ProfessorDAO();
            RequestDispatcher rd;

            switch (btEnviar) {
                case "Incluir":
                    professorDAO.inserir(professor);
                    request.setAttribute("msgOperacaoRealizada", "Professor incluído com sucesso.");
                    break;
                case "Alterar":
                    professorDAO.atualizar(professor);
                    request.setAttribute("msgOperacaoRealizada", "Professor alterado com sucesso.");
                    break;
                case "Excluir":
                    professorDAO.excluir(id);
                    request.setAttribute("msgOperacaoRealizada", "Professor excluído com sucesso.");
                    break;
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/ProfessorController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (SQLException | ServletException | NumberFormatException ex) {
            request.setAttribute("errorMessage", "Erro ao processar a solicitação: " + ex.getMessage());
            request.getRequestDispatcher("/views/comum/erro.jsp").forward(request, response);
        }
    }
}
