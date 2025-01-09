package controller.admin;

import entidade.Administrador;
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
import model.AdministradorDAO;

@WebServlet(name = "AdministradorController", urlPatterns = {"/admin/AdministradorController"})
public class AdministradorController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String acao = request.getParameter("acao");
            RequestDispatcher rd;

            switch (acao) {
                case "Listar":
                    listarAdministradores(request, response);
                    break;
                case "ListarPendentes":
                    listarAdministradoresPendentes(request, response);
                    break;
                case "Aprovar":
                    aprovarAdministrador(request, response);
                    break;
                case "Alterar":
                case "Excluir":
                    carregarFormularioAdministrador(request, response, acao);
                    break;
                case "Incluir":
                    prepararIncluirAdministrador(request, response);
                    break;
                default:
                    request.setAttribute("msgError", "Ação inválida.");
                    rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                    rd.forward(request, response);
                    break;
            }
        } catch (IOException | ServletException ex) {
            Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void listarAdministradores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdministradorDAO administradorDAO = new AdministradorDAO();
            ArrayList<Administrador> listaAdministradores = administradorDAO.ListaDeAdministrador();
            request.setAttribute("listaAdministradores", listaAdministradores);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/administrador/listaAdministradores.jsp");
            rd.forward(request, response);
        } catch (IOException | ServletException ex) {
            throw new RuntimeException("Erro ao listar administradores.", ex);
        }
    }

    private void listarAdministradoresPendentes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            AdministradorDAO administradorDAO = new AdministradorDAO();
            ArrayList<Administrador> pendentes = administradorDAO.listarAdministradoresPendentes();
            request.setAttribute("listaPendentes", pendentes);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/administrador/listaPendentes.jsp");
            rd.forward(request, response);
        } catch (IOException | SQLException | ServletException ex) {
            throw new RuntimeException("Erro ao listar administradores pendentes.", ex);
        }
    }

    private void aprovarAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            AdministradorDAO administradorDAO = new AdministradorDAO();
            boolean usuarioAprovado = true; // Simular verificação de permissões

            if (usuarioAprovado) {
                administradorDAO.AprovarCadastro(id);
                request.setAttribute("msgOperacaoRealizada", "Administrador aprovado com sucesso.");
            } else {
                request.setAttribute("msgError", "Somente administradores aprovados podem realizar essa operação.");
            }

            request.setAttribute("link", "/aplicacaoMVC/admin/AdministradorController?acao=ListarPendentes");
            RequestDispatcher rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
            rd.forward(request, response);
        } catch (IOException | NumberFormatException | ServletException ex) {
            throw new RuntimeException("Erro ao aprovar administrador.", ex);
        }
    }

    private void carregarFormularioAdministrador(HttpServletRequest request, HttpServletResponse response, String acao)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            AdministradorDAO administradorDAO = new AdministradorDAO();
            Administrador administrador = administradorDAO.getAdministrador(id);

            request.setAttribute("administrador", administrador);
            request.setAttribute("acao", acao);
            RequestDispatcher rd = request.getRequestDispatcher("/views/admin/administrador/formAdministrador.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao carregar formulário de administrador.", ex);
        }
    }

    private void prepararIncluirAdministrador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Administrador administrador = new Administrador();
        request.setAttribute("administrador", administrador);
        request.setAttribute("msgError", "");
        request.setAttribute("acao", "Incluir");

        RequestDispatcher rd = request.getRequestDispatcher("/views/admin/administrador/formAdministrador.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String senha = request.getParameter("senha");
        String endereco = request.getParameter("endereco");
        String aprovado = request.getParameter("aprovado");
        String btEnviar = request.getParameter("btEnviar");

        RequestDispatcher rd;

        if (nome.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
            Administrador administrador = new Administrador();
            switch (btEnviar) {
                case "Alterar":
                case "Excluir":
                    try {
                        AdministradorDAO administradorDAO = new AdministradorDAO();
                        administrador = administradorDAO.getAdministrador(id);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        throw new RuntimeException("Falha em uma operação de administrador.");
                    }
                    break;
            }

            request.setAttribute("administrador", administrador);
            request.setAttribute("acao", btEnviar);
            request.setAttribute("msgError", "É necessário preencher todos os campos");

            rd = request.getRequestDispatcher("/views/admin/administrador/formAdministrador.jsp");
            rd.forward(request, response);
        } else {
            Administrador administrador = new Administrador(nome, cpf, endereco, senha, aprovado);
            AdministradorDAO administradorDAO = new AdministradorDAO();

            try {
                switch (btEnviar) {
                    case "Incluir":
                        administradorDAO.Inserir(administrador);
                        request.setAttribute("msgOperacaoRealizada", "Inclusão realizada com sucesso");
                        break;
                    case "Alterar":
                        administradorDAO.Alterar(administrador);
                        request.setAttribute("msgOperacaoRealizada", "Alteração realizada com sucesso");
                        break;
                    case "Excluir":
                        administradorDAO.Excluir(administrador);
                        request.setAttribute("msgOperacaoRealizada", "Exclusão realizada com sucesso");
                        break;
                }

                request.setAttribute("link", "/aplicacaoMVC/admin/AdministradorController?acao=Listar");
                rd = request.getRequestDispatcher("/views/comum/showMessage.jsp");
                rd.forward(request, response);

            } catch (IOException | ServletException ex) {
                System.out.println(ex.getMessage());
                throw new RuntimeException("Falha em uma operação de administrador.");
            } catch (Exception ex) {
                Logger.getLogger(AdministradorController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
