package controller;

import entidade.Administrador;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "RegistrarController", urlPatterns = {"/RegistrarController"})
public class RegistrarController extends HttpServlet {

    private final AdministradorDAO administradorDao = new AdministradorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/views/registro/formRegistro.jsp");
        rd.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String senha2 = request.getParameter("senha2");
        String aprovado = request.getParameter("aprovado");

        if (nome == null || nome.isEmpty()
                || endereco == null || endereco.isEmpty()
                || cpf == null || cpf.isEmpty()
                || senha == null || senha.isEmpty()) {

            request.setAttribute("msgError", "Todos os campos são obrigatórios!");
            RequestDispatcher rd = request.getRequestDispatcher("/views/registro/formRegistro.jsp");
            rd.forward(request, response);
            return;
        }

        if (!senha.equals(senha2)) {
            request.setAttribute("msgError", "As senhas não coincidem!");
            RequestDispatcher rd = request.getRequestDispatcher("/views/registro/formRegistro.jsp");
            rd.forward(request, response);
            return;
        }

        Administrador admin = new Administrador();
        admin.setNome(nome);
        admin.setEndereco(endereco);
        admin.setCpf(cpf);
        admin.setSenha(senha);
        admin.setAprovado(aprovado);

        boolean sucesso = false;
        try {
            sucesso = administradorDao.Inserir(admin);
        } catch (Exception ex) {
            Logger.getLogger(RegistrarController.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestDispatcher rd;
        if (sucesso) {
            request.setAttribute("msgOperacaoRealizada", "Administrador cadastrado com sucesso!");
            request.setAttribute("link", "home");
            rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
        } else {
            request.setAttribute("msgError", "Erro ao cadastrar administrador. Tente novamente.");
            rd = request.getRequestDispatcher("/views/registro/formRegistro.jsp");
        }
        rd.forward(request, response);
    }

}
