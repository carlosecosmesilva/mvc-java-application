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

@WebFilter(filterName = "filtroRestrito", urlPatterns = {"/admin/*", "/professor/*", "/aluno/*"})
public class filtroRestrito implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Object usuarioLogado = httpRequest.getSession().getAttribute("usuario");

        if (usuarioLogado == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }

        if (usuarioLogado instanceof Administrador) {
            if (httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/admin/")) {
                chain.doFilter(request, response);
                return;
            }
        } else if (usuarioLogado instanceof Professor) {
            if (httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/professor/")) {
                chain.doFilter(request, response);
                return;
            }
        } else if (usuarioLogado instanceof Aluno) {
            if (httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/aluno/")) {
                chain.doFilter(request, response);
                return;
            }
        }

        httpResponse.sendRedirect(httpRequest.getContextPath() + "/acessoNegado.jsp");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
