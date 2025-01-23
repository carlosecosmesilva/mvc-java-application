package filtro;

import entidade.Administrador;
import entidade.Aluno;
import entidade.Professor;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "filtroRestrito", urlPatterns = {"/admin/*"})
public class filtroRestrito implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Verifica o atributo "usuario" na sessão
        Object usuario = req.getSession().getAttribute("usuario");

        if (usuario instanceof Administrador || usuario instanceof Professor || usuario instanceof Aluno) {
            chain.doFilter(request, response); // Usuário autorizado, continua
        } else {
            res.sendRedirect(req.getContextPath() + "/home"); // Redireciona para a página inicial
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
