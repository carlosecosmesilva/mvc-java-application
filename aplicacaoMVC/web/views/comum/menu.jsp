<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Administrador" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/aplicacaoMVC/home">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <%
                    // Verificar se há uma sessão ativa
                    HttpSession sessao = request.getSession(false);
                    if (sessao != null) {
                        Administrador AdministradorLogado = (Administrador) sessao.getAttribute("administrador");
                        if (AdministradorLogado != null) { %>
                            <!-- Links visíveis somente para administradores -->
                            <!-- <a class="nav-link" href="/aplicacaoMVC/admin/dashboard">Dashboard</a> -->
                            <a class="nav-link" href="/aplicacaoMVC/admin/AlunoController?acao=Listar">Alunos</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/DisciplinaController?acao=Listar">Disciplinas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/ProfessorController?acao=Listar">Professores</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/TurmaController?acao=Listar">Turmas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/RelatorioController?acao=gerarRelatorio">Relatórios</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/logOut">Logout</a>
                <%  } else { %>
                            <!-- Links visíveis para visitantes -->
                            <a class="nav-link" href="/aplicacaoMVC/public/PublicController?acao=listarDisciplinas">Disciplinas Disponíveis</a>
                            <a class="nav-link" href="/aplicacaoMVC/AutenticaController?acao=Login">Login</a>
                <%    }
                    } else { %>
                        <!-- Links visíveis caso a sessão seja inexistente -->
                        <a class="nav-link" href="/aplicacaoMVC/public/PublicController?acao=listarDisciplinas">Disciplinas Disponíveis</a>
                        <a class="nav-link" href="/aplicacaoMVC/AutenticaController?acao=Login">Login</a>
                <% } %>
            </div>
        </div>
    </div>
</nav>
