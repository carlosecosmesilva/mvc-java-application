package controller.admin;

import entidade.Aluno;
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
import model.AlunoDAO;

@WebServlet(name = "AlunoController", urlPatterns = {"/admin/AlunoController"})
public class AlunoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = (String) request.getParameter("acao");
        Aluno aluno = new Aluno();
        AlunoDAO alunoDAO = new AlunoDAO();
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
                default:
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Ação não encontrada.");
            }
        } catch (IOException | NumberFormatException | ServletException ex) {
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

            // Verificar se os campos necessários foram preenchidos
            if (nome.isEmpty() || email.isEmpty() || celular.isEmpty() || cpf.isEmpty()) {
                Aluno aluno = (btEnviar.equals("Alterar") || btEnviar.equals("Excluir")) ? new Aluno(id) : new Aluno();
                request.setAttribute("aluno", aluno);
                request.setAttribute("acao", btEnviar);
                request.setAttribute("msgError", "É necessário preencher todos os campos obrigatórios.");
                request.getRequestDispatcher("/views/admin/aluno/formAluno.jsp").forward(request, response);
                return;
            }

            Aluno aluno = new Aluno(id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep);
            AlunoDAO alunoDAO = new AlunoDAO();
            RequestDispatcher rd;

            switch (btEnviar) {
                case "Incluir":
                    alunoDAO.insert(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso.");
                    break;
                case "Alterar":
                    alunoDAO.update(aluno);
                    request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso.");
                    break;
                case "Excluir":
                    aluno = new Aluno(id);  // Apenas o id para excluir
                    alunoDAO.delete(id);  // Certifique-se de que o método 'delete' está excluindo corretamente
                    request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso.");
                    break;
            }

            // Redirecionamento para a página de listagem de alunos
            request.setAttribute("link", "/aplicacaoMVC/admin/AlunoController?acao=Listar");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);

        } catch (IOException | NumberFormatException | ServletException  ex) {
            // Redirecionar para a página de erro
            request.setAttribute("errorMessage", "Erro ao processar a operação: " + ex.getMessage());
            request.getRequestDispatcher("/erro.jsp").forward(request, response);
        }
    }

}
