package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import entidade.Administrador;
import entidade.Aluno;
import entidade.Professor;
import model.AdministradorDAO;
import model.AlunoDAO;
import model.ProfessorDAO;

/**
 *
 * @author Leonardo
 */
@WebServlet(name = "AutenticaController", urlPatterns = {"/AutenticaController"})
public class AutenticaController extends HttpServlet {

    private final AdministradorDAO administradorDAO = new AdministradorDAO();
    private final ProfessorDAO professorDAO = new ProfessorDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cpf_user = request.getParameter("cpf");
        String senha_user = request.getParameter("senha");
        RequestDispatcher rd;

        // Validação de campos obrigatórios
        if (cpf_user == null || cpf_user.isEmpty() || senha_user == null || senha_user.isEmpty()) {
            request.setAttribute("msgError", "CPF e/ou senha devem ser preenchidos.");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
            return;
        }

        try {
            HttpSession session = request.getSession();

            Administrador administradorObtido = administradorDAO.Logar(new Administrador(cpf_user, senha_user));
            if (administradorObtido != null) {
                session.setAttribute("usuario", administradorObtido);
                response.sendRedirect("/aplicacaoMVC/home");
                return;
            }

            Professor professorObtido = professorDAO.Logar(new Professor(cpf_user, senha_user));
            if (professorObtido != null) {
                session.setAttribute("usuario", professorObtido);
                session.setAttribute("professorId", professorObtido.getId()); 
                response.sendRedirect("/aplicacaoMVC/home");
                return;
            }

            Aluno alunoObtido = alunoDAO.Logar(new Aluno(cpf_user, senha_user));
            if (alunoObtido != null) {
                session.setAttribute("usuario", alunoObtido);
                session.setAttribute("alunoId", alunoObtido.getId());
                response.sendRedirect("/aplicacaoMVC/home");
                return;
            }
            
            request.setAttribute("msgError", "Usuário e/ou senha incorretos.");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            request.setAttribute("msgError", "Erro no sistema. Tente novamente mais tarde.");
            rd = request.getRequestDispatcher("/views/comum/erro.jsp");
            rd.forward(request, response);
        }
    }
}
