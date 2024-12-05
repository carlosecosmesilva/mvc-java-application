package controller.admin;

import entidade.Relatorio;
import java.io.IOException;
import java.util.List;
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
        String action = request.getParameter("action");

        if ("relatorio".equals(action)) {
            RelatorioDAO relatorioDAO = new RelatorioDAO();
            List<Relatorio> relatorios = relatorioDAO.gerarRelatorioDisciplinas();
            request.setAttribute("relatorios", relatorios);
            request.getRequestDispatcher("views/admin/dashboard/relatorio.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/views/admin/dashboard/areaRestrita.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/views/admin/dashboard/areaRestrita.jsp")
                .forward(request, response);
    }

}
