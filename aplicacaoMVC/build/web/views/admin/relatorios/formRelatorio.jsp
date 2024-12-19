<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entidade.Disciplina"%>
<%@page import="entidade.Turma"%>
<%@page import="entidade.Relatorio"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Gerar Relatório</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-6 offset-3">
                    <h1>Gerar Relatório</h1>

                    <%
                        String msgError = (String) request.getAttribute("msgError");
                        List<Disciplina> disciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
                        List<Turma> turmas = (List<Turma>) request.getAttribute("listaTurmas");
                        List<Relatorio> relatorios = (List<Relatorio>) request.getAttribute("relatorios");

                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                    <div class="alert alert-danger" role="alert">
                        <%= msgError%>
                    </div>
                    <% } %>

                    <form action="/aplicacaoMVC/admin/RelatorioController" method="GET">
                        <!-- Filtro por Disciplina -->
                        <div class="mb-3">
                            <label for="disciplinaId" class="form-label">Disciplina</label>
                            <select name="disciplinaId" class="form-select">
                                <option value="">Selecione uma disciplina</option>
                                <% if (disciplinas != null) {
                                        for (Disciplina disciplina : disciplinas) {%>
                                <option value="<%= disciplina.getId()%>"><%= disciplina.getNome()%></option>
                                <%  }
                                    } %>
                            </select>
                        </div>

                        <!-- Filtro por Turma -->
                        <div class="mb-3">
                            <label for="turmaId" class="form-label">Turma</label>
                            <select name="turmaId" class="form-select">
                                <option value="">Selecione uma turma</option>
                                <% if (turmas != null) {
                                        for (Turma turma : turmas) {%>
                                <option value="<%= turma.getId()%>"><%= turma.getCodigoTurma()%></option>
                                <%  }
                                    } %>
                            </select>
                        </div>

                        <div>
                            <input type="submit" name="acao" value="gerarRelatorio" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/dashboard" class="btn btn-secondary">Voltar</a>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Exibição do Relatório -->
            <div class="row mt-5">
                <div class="col-12">
                    <%
                        if (relatorios != null && !relatorios.isEmpty()) { %>
                    <h2>Resultados do Relatório</h2>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Disciplina</th>
                                <th>Turma</th>
                                <th>Aluno</th>
                                <th>Nota</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Relatorio rel : relatorios) {%>
                            <tr>
                                <td><%= rel.getDisciplina()%></td>
                                <td><%= rel.getTurma()%></td>
                                <td><%= rel.getAluno()%></td>
                                <td><%= rel.getNota()%></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% } else if (request.getAttribute("relatorios") != null) { %>
                    <div class="alert alert-warning" role="alert">
                        Nenhum dado encontrado para os filtros aplicados.
                    </div>
                    <% }%>
                </div>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
