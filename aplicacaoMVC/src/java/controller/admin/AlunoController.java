package controller.admin;

import entidade.Aluno;
import entidade.Turma;
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
import model.AlunoDAO;
import model.TurmaDAO;

@WebServlet(name = "AlunoController", urlPatterns = {"/admin/AlunoController"})
public class AlunoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        Aluno aluno = new Aluno();
        AlunoDAO alunoDAO = new AlunoDAO();
        TurmaDAO turmaDAO = new TurmaDAO();
        RequestDispatcher rd;
        try {
            switch (acao) {
                case "Listar":
                    ArrayList<Aluno> listaAlunos = alunoDAO.getAll();
                    request.setAttribute("listaAlunos", listaAlunos);
                    rd = request.getRequestDispatcher("/views/admin/aluno/listaAlunos.jsp");
                    rd.forward(request, response);
                    break;
                case "Alterar":
                case "Excluir":
                    int id = Integer.parseInt(request.getParameter("id"));
                    aluno = alunoDAO.get(id);
                    request.setAttribute("aluno", aluno);
                    request.setAttribute("acao", acao);
                    rd = request.getRequestDispatcher("/views/admin/aluno/formAluno.jsp");
                    rd.forward(request, response);
                    break;
                case "Inscrever":
                    ArrayList<Turma> turmasDisponiveis = (ArrayList<Turma>) turmaDAO.listar();
                    request.setAttribute("turmasDisponiveis", turmasDisponiveis);
                    rd = request.getRequestDispatcher("/views/admin/aluno/formInscricao.jsp");
                    rd.forward(request, response);
                    break;
                case "Historico":
                    int alunoId = Integer.parseInt(request.getParameter("id"));
                    ArrayList<Turma> turmasAluno = turmaDAO.getTurmasAluno(alunoId);
                    request.setAttribute("turmasAluno", turmasAluno);
                    rd = request.getRequestDispatcher("/views/admin/aluno/historicoNotas.jsp");
                    rd.forward(request, response);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada.");
            }
        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("errorMessage", "Erro ao processar a operação: " + ex.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
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
            String acao = request.getParameter("acao");
            Aluno aluno = new Aluno(id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
            AlunoDAO alunoDAO = new AlunoDAO();
            TurmaDAO turmaDAO = new TurmaDAO();
            RequestDispatcher rd;

            switch (acao) {
                case "Inscrever":
                    int turmaId = Integer.parseInt(request.getParameter("turmaId"));
                    Turma turma = turmaDAO.getTurma(turmaId);
                    if (turma != null && turma.getNumeroVagas() > 0) {
                        alunoDAO.insert(aluno);  // Inscrição do aluno
                        turmaDAO.adicionarAluno(turmaId, aluno.getId());  // Adiciona aluno à turma
                        request.setAttribute("msgOperacaoRealizada", "Inscrição realizada com sucesso.");
                    } else {
                        request.setAttribute("msgError", "Não há vagas disponíveis para esta turma.");
                    }
                    rd = request.getRequestDispatcher("/views/admin/aluno/formInscricao.jsp");
                    rd.forward(request, response);
                    break;
                case "Alterar":
                    alunoDAO.update(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;
                case "Excluir":
                    alunoDAO.delete(id);  // Exclui o aluno
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }

            // Redirecionamento para a página de listagem de alunos
            request.setAttribute("link", "/aplicacaoMVC/admin/AlunoController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | ServletException ex) {
            request.setAttribute("errorMessage", "Erro ao processar a operação: " + ex.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AlunoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
