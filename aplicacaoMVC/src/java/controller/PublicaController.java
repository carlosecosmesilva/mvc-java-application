package controller;

import entidade.Disciplina;
import model.DisciplinaDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

@WebServlet(name = "PublicController", urlPatterns = "/public/PublicController")
public class PublicaController extends HttpServlet {

    private final DisciplinaDAO disciplinaDAO = new DisciplinaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acao = request.getParameter("acao");
        try {
            if ("listarDisciplinas".equals(acao)) {
                ArrayList<Disciplina> disciplinas = disciplinaDAO.listarDisciplinasComVagas();
                request.setAttribute("listaDisciplinas", disciplinas);
                RequestDispatcher dispatcher = request.getRequestDispatcher("/views/public/dashboard.jsp");
                dispatcher.forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("mensagemErro", "Erro ao carregar disciplinas: " + e.getMessage());
            request.getRequestDispatcher("/views/comum/erro.jsp").forward(request, response);
        }
    }

}
