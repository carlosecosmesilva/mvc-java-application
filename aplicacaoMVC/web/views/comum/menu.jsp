<%@page contentType="text/html" pageEncoding="UTF-8" import="entidade.Administrador, entidade.Aluno, entidade.Professor" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="/aplicacaoMVC/home">Home</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <% 
                    HttpSession sessao = request.getSession(false);
                    if (sessao != null && sessao.getAttribute("usuario") != null) {
                        Object usuarioLogado = sessao.getAttribute("usuario");
                        if (usuarioLogado instanceof Administrador) { %>
                            <a class="nav-link" href="/aplicacaoMVC/admin/AlunoController?acao=Listar">Alunos</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/DisciplinaController?acao=Listar">Disciplinas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/ProfessorController?acao=Listar">Professores</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/TurmaController?acao=Listar">Turmas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/AdministradorController?acao=ListarPendentes">Administradores Pendentes</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/RelatorioController?acao=gerarRelatorio">Relatórios</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/logOut">Logout</a>
                        <% } else if (usuarioLogado instanceof Professor) { %>
                            <a class="nav-link" href="/aplicacaoMVC/admin/TurmaController?acao=listarNotas">Notas dos Alunos</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/TurmaController?acao=lancarNotas">Lançar Notas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/logOut">Logout</a>
                        <% } else if (usuarioLogado instanceof Aluno) { %>
                            <a class="nav-link" href="/aplicacaoMVC/admin/AlunoController?acao=ListarDisciplinasAluno">Disciplinas</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/AlunoController?acao=Inscrever">Inscrição</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/AlunoController?acao=Historico">Histórico</a>
                            <a class="nav-link" href="/aplicacaoMVC/admin/logOut">Logout</a>
                        <% } else { %>
                            <a class="nav-link" href="/aplicacaoMVC/home">Home</a>
                        <% }
                    } else { %>
                        <a class="nav-link" href="/aplicacaoMVC/public/PublicController?acao=listarDisciplinas">Disciplinas Disponíveis</a>
                        <a class="nav-link" href="/aplicacaoMVC/AutenticaController?acao=Login">Login</a>
                    <% } %>
            </div>
        </div>
    </div>
</nav>
