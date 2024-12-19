package controller;

import entidade.Administrador;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.AdministradorDAO;

/**
 *
 * @author Leonardo
 */
@WebServlet(name = "AutenticaController", urlPatterns = {"/AutenticaController"})
public class AutenticaController extends HttpServlet {

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

        if (cpf_user.isEmpty() || senha_user.isEmpty()) {
            // Dados obrigatórios não preenchidos
            request.setAttribute("msgError", "CPF e/ou senha devem ser preenchidos.");
            rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
            rd.forward(request, response);
            return;
        }

        Administrador administrador = new Administrador(cpf_user, senha_user);
        AdministradorDAO administradorDAO = new AdministradorDAO();
        try {
            Administrador administradorObtido = administradorDAO.Logar(administrador);

            if (administradorObtido != null) {
                // Login bem-sucedido
                HttpSession session = request.getSession();
                session.setAttribute("administrador", administradorObtido);
                response.sendRedirect("/aplicacaoMVC/admin/dashboard");
            } else {
                // Login falhou
                request.setAttribute("msgError", "Usuário e/ou senha incorretos.");
                rd = request.getRequestDispatcher("/views/autenticacao/formLogin.jsp");
                rd.forward(request, response);
            }
        } catch (Exception ex) {
            request.setAttribute("msgError", "Erro no sistema. Tente novamente mais tarde.");
            rd = request.getRequestDispatcher("/views/comum/erro.jsp");
            rd.forward(request, response);
        }
    }

}
