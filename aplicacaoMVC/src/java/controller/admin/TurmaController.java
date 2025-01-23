package controller.admin;

import entidade.Turma;
import model.TurmaDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entidade.Aluno;
import entidade.Disciplina;
import entidade.Professor;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ProfessorDAO;
import model.DisciplinaDAO;
import model.AlunoDAO;

@WebServlet(name = "TurmaController", urlPatterns = "/admin/TurmaController")
public class TurmaController extends HttpServlet {

    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        RequestDispatcher rd;
        try {
            HttpSession session = request.getSession();
            Integer professorId = (Integer) session.getAttribute("professorId");
            if (acao != null) {
                switch (acao) {
                    case "Listar":
                        List<Turma> listaTurmas = turmaDAO.listar();
                        request.setAttribute("listaTurmas", listaTurmas);
                        rd = request.getRequestDispatcher("/views/admin/turma/listaTurma.jsp");
                        rd.forward(request, response);
                        break;

                    case "listarNotas":
                        if (professorId != null) {
                            ArrayList<Turma> turmasProfessor = turmaDAO.getTurmasProfessor(professorId);

                            if (turmasProfessor != null && !turmasProfessor.isEmpty()) {
                                ArrayList<Aluno> listaAlunos = new ArrayList<>();
                                for (Turma turma : turmasProfessor) {
                                    List<Aluno> alunosDaTurma = alunoDAO.getAlunosDaTurma(turma.getId(), professorId);
                                    listaAlunos.addAll(alunosDaTurma);
                                }

                                request.setAttribute("listaAlunos", listaAlunos);
                                request.setAttribute("listaTurmas", turmasProfessor);
                                response.sendRedirect("/aplicacaoMVC/admin/professor/listarNotas");
                            } else {
                                request.setAttribute("erro", "Nenhuma turma associada a esse professor.");
                            }
                        } else {
                            response.sendRedirect("/aplicacaoMVC/login");
                        }
                        break;
                    case "lancarNotas":
                        int turmaIdLancar = Integer.parseInt(request.getParameter("turmaId"));
                        List<Aluno> alunosParaLancamento = alunoDAO.getAlunosDaTurma(turmaIdLancar, professorId);
                        request.setAttribute("alunos", alunosParaLancamento);
                        request.setAttribute("turmaId", turmaIdLancar);
                        rd = request.getRequestDispatcher("/views/admin/turma/lancarNotas.jsp");
                        rd.forward(request, response);
                        break;

                    case "Incluir":
                        Turma novaTurma = new Turma();
                        request.setAttribute("turma", novaTurma);
                        request.setAttribute("acao", "Incluir");
                        carregarListasParaFormulario(request);
                        rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
                        rd.forward(request, response);
                        break;

                    case "Alterar":
                    case "Excluir":
                        int id = Integer.parseInt(request.getParameter("id"));
                        Turma turmaExistente = turmaDAO.getTurma(id);
                        request.setAttribute("turma", turmaExistente);
                        request.setAttribute("acao", acao);
                        carregarListasParaFormulario(request);
                        rd = request.getRequestDispatcher("/views/admin/turma/formTurma.jsp");
                        rd.forward(request, response);
                        break;

                    default:
                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada.");
                        break;
                }
            }
        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
            Logger.getLogger(TurmaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    private void carregarListasParaFormulario(HttpServletRequest request) throws SQLException {
        List<Professor> listaProfessores = professorDAO.getAll();
        List<Disciplina> listaDisciplinas = disciplinaDAO.listar();
        List<Aluno> listaAlunos = alunoDAO.getAll();
        request.setAttribute("listaProfessores", listaProfessores);
        request.setAttribute("listaDisciplinas", listaDisciplinas);
        request.setAttribute("listaAlunos", listaAlunos);
    }

    public void lancarNota(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int alunoId = Integer.parseInt(request.getParameter("alunoId"));
            double nota = Double.parseDouble(request.getParameter("nota"));
            int turmaId = Integer.parseInt(request.getParameter("turmaId"));

            if (!turmaDAO.isAlunoNaTurma(alunoId, turmaId)) {
                request.setAttribute("erro", "O aluno não está inscrito nesta turma.");
                request.getRequestDispatcher("/erro.jsp").forward(request, response);
                return;
            }

            boolean sucesso = turmaDAO.atualizarNota(alunoId, turmaId, nota);
            if (!sucesso) {
                request.setAttribute("erro", "Erro ao lançar a nota.");
                request.getRequestDispatcher("/erro.jsp").forward(request, response);
                return;
            }

            response.sendRedirect("/aplicacaoMVC/admin/TurmaController?acao=ListarNotas&turmaId=" + turmaId);
        } catch (NumberFormatException e) {
            request.setAttribute("erro", "Parâmetros inválidos.");
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("erro", "Erro no banco de dados: " + e.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("erro", "Erro inesperado: " + e.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
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

            int totalTurmasProfessor = turmaDAO.contarTurmasDoProfessor(professorId);
            if (totalTurmasProfessor >= 2) {
                request.setAttribute("msgError", "O professor já está associado a duas turmas. Não é possível adicionar mais.");
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

            request.setAttribute("link", "/aplicacaoMVC/admin/TurmaController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | SQLException | ServletException e) {
            Logger.getLogger(TurmaController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
