package controller.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entidade.Aluno;
import entidade.Disciplina;
import entidade.Turma;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.AlunoDAO;
import model.DisciplinaDAO;
import model.TurmaDAO;

@WebServlet(name = "AlunoController", urlPatterns = {"/admin/AlunoController"})
public class AlunoController extends HttpServlet {

    private AlunoDAO alunoDAO = new AlunoDAO();
    private TurmaDAO turmaDAO = new TurmaDAO();
    private DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = (String) request.getParameter("acao");
        Aluno aluno = new Aluno();
        RequestDispatcher rd;
        try {
            HttpSession session = request.getSession();
            Integer alunoId = (Integer) session.getAttribute("alunoId");
            String alunoCpf = (String) session.getAttribute("alunoCpf");

            switch (acao) {
                case "Listar":
                    ArrayList<Aluno> listaAlunos = alunoDAO.getAll();
                    request.setAttribute("listaAlunos", listaAlunos);
                    rd = request.getRequestDispatcher("/views/admin/aluno/listaAlunos.jsp");
                    rd.forward(request, response);
                    break;
                case "Alterar":
                case "Excluir":
                    alunoId = Integer.parseInt(request.getParameter("id"));
                    aluno = alunoDAO.get(alunoId);
                    request.setAttribute("aluno", aluno);
                    request.setAttribute("msgError", "");
                    request.setAttribute("acao", acao);
                    rd = request.getRequestDispatcher("/views/admin/aluno/formAluno.jsp");
                    rd.forward(request, response);
                    break;
                case "Incluir":
                    request.setAttribute("aluno", aluno);
                    request.setAttribute("msgError", "");
                    request.setAttribute("acao", acao);
                    rd = request.getRequestDispatcher("/views/admin/aluno/formAluno.jsp");
                    rd.forward(request, response);
                    break;
                case "Inscrever":
                    List<Turma> turmasDisponiveis = turmaDAO.listar();
                    request.setAttribute("turmasDisponiveis", turmasDisponiveis);
                    rd = request.getRequestDispatcher("/views/admin/aluno/formInscricao.jsp");
                    rd.forward(request, response);
                    break;
                case "Historico":
                    ArrayList<Turma> turmasAluno = turmaDAO.getTurmasAluno(alunoId);
                    request.setAttribute("turmasAluno", turmasAluno);
                    rd = request.getRequestDispatcher("/views/admin/aluno/historicoNotas.jsp");
                    rd.forward(request, response);
                    break;
                case "ListarDisciplinasAluno":
                    List<Disciplina> disciplinas = disciplinaDAO.listarDisciplinasAluno(alunoId);
                    request.setAttribute("listaDisciplinas", disciplinas);
                    rd = request.getRequestDispatcher("/views/admin/disciplina/listaDisciplina.jsp");
                    rd.forward(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada.");
            }
        } catch (IOException | NumberFormatException | ServletException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        } catch (SQLException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String celular = request.getParameter("celular");
            String cpf = request.getParameter("cpf");
            String senha = request.getParameter("senha");
            String endereco = request.getParameter("endereco");
            String cidade = request.getParameter("cidade");
            String bairro = request.getParameter("bairro");
            String cep = request.getParameter("cep");
            String btEnviar = request.getParameter("btEnviar");

            if (nome.isEmpty() || email.isEmpty() || celular.isEmpty() || cpf.isEmpty()) {
                Aluno aluno = (btEnviar.equals("Alterar") || btEnviar.equals("Excluir")) ? new Aluno(id) : new Aluno();
                request.setAttribute("aluno", aluno);
                request.setAttribute("acao", btEnviar);
                request.setAttribute("msgError", "É necessário preencher todos os campos obrigatórios.");
                request.getRequestDispatcher("/views/admin/aluno/formAluno.jsp").forward(request, response);
                return;
            }

            Aluno aluno = new Aluno(id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
            alunoDAO = new AlunoDAO();
            turmaDAO = new TurmaDAO();
            disciplinaDAO = new DisciplinaDAO();
            RequestDispatcher rd;

            switch (btEnviar) {
                case "Incluir":
                    alunoDAO.insert(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;
                case "Inscrever":
                    List<Turma> turmasDisponiveis = turmaDAO.listar();
                    request.setAttribute("turmasDisponiveis", turmasDisponiveis);
                    rd = request.getRequestDispatcher("/views/admin/aluno/formInscricao.jsp");
                    rd.forward(request, response);
                    break;
                case "Alterar":
                    alunoDAO.update(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;
                case "Excluir":
                    aluno = new Aluno(id);
                    alunoDAO.delete(id);
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
                case "Historico":
                    int alunoId = Integer.parseInt(request.getParameter("id"));
                    ArrayList<Turma> turmasAluno = turmaDAO.getTurmasAluno(alunoId);
                    request.setAttribute("turmasAluno", turmasAluno);
                    rd = request.getRequestDispatcher("/views/admin/aluno/historicoNotas.jsp");
                    rd.forward(request, response);
                    break;
                case "ListarDisciplinasAluno":
                    List<Disciplina> disciplinas = disciplinaDAO.listarDisciplinasAluno(id);
                    request.setAttribute("listaDisciplinas", disciplinas);
                    rd = request.getRequestDispatcher("/views/admin/disciplina/listaDisciplina.jsp");
                    rd.forward(request, response);
                    break;
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/AlunoController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
