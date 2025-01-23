package controller.admin;

import entidade.Disciplina;
import entidade.Relatorio;
import entidade.Turma;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DisciplinaDAO;
import model.RelatorioDAO;
import model.TurmaDAO;

@WebServlet(name = "RelatorioController", urlPatterns = {"/admin/RelatorioController"})
public class RelatorioController extends HttpServlet {

    private final TurmaDAO turmaDAO = new TurmaDAO();
    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String disciplinaId = request.getParameter("disciplinaId");
            String turmaId = request.getParameter("turmaId");

            List<Disciplina> listaDisciplinas = disciplinaDAO.listar();
            List<Turma> listaTurmas = turmaDAO.listar();

            request.setAttribute("listaDisciplinas", listaDisciplinas);
            request.setAttribute("listaTurmas", listaTurmas);

            String acao = request.getParameter("acao");
            if ("gerarRelatorio".equals(acao)) {
                List<Relatorio> relatorios = RelatorioDAO.gerarRelatorioDisciplinas();
                request.setAttribute("relatorios", relatorios);
            }

            request.getRequestDispatcher("/views/admin/relatorios/formRelatorio.jsp").forward(request, response);

        } catch (IOException | SQLException | ServletException e) {
            Logger.getLogger(RelatorioController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
