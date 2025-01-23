package controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.RelatorioDAO;

@WebServlet(name = "DashboardController", urlPatterns = {"/admin/dashboard"})
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        try {
            if ("relatorio".equals(acao)) {
                request.getRequestDispatcher("views/admin/dashboard/relatorio.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/views/admin/dashboard/areaRestrita.jsp").forward(request, response);
            }
        } catch (IOException | ServletException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Falha em uma query para gerar relatórios.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/admin/dashboard/areaRestrita.jsp")
                .forward(request, response);
    }

}
