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

@WebServlet(name="RelatorioController", urlPatterns = {"/admin/relatorios"})
public class RelatorioController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        try {
            if (acao != null) {
                switch (acao) {
                    case "ListarDisciplinas":
                        RelatorioDAO relatorioDAO = new RelatorioDAO();
                        List<Relatorio> relatorios = relatorioDAO.gerarRelatorioDisciplinas();
                        request.setAttribute("relatorios", relatorios);
                        request.getRequestDispatcher("views/admin/dashboard/relatorio.jsp").forward(request, response);
                        break;
                }
            }
        } catch (IOException | ServletException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Falha ao gerar relatório.");
        }
    }
}
