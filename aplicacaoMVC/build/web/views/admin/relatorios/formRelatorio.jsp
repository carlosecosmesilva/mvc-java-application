<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entidade.Disciplina"%>
<%@page import="entidade.Turma"%>

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

                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                            <div class="alert alert-danger" role="alert">
                                <%= msgError %>
                            </div>
                        <% } %>

                    <form action="/aplicacaoMVC/admin/RelatorioController" method="GET">

                        <!-- Filtro por Período -->
                        <div class="mb-3">
                            <label for="dataInicio" class="form-label">Data de Início</label>
                            <input type="date" name="dataInicio" class="form-control">
                        </div>

                        <div class="mb-3">
                            <label for="dataFim" class="form-label">Data de Fim</label>
                            <input type="date" name="dataFim" class="form-control">
                        </div>

                        <!-- Filtro por Disciplina -->
                        <div class="mb-3">
                            <label for="disciplinaId" class="form-label">Disciplina</label>
                            <select name="disciplinaId" class="form-select">
                                <option value="">Selecione uma disciplina</option>
                                <% for (Disciplina disciplina : disciplinas) { %>
                                    <option value="<%= disciplina.getId() %>"><%= disciplina.getNome() %></option>
                                <% } %>
                            </select>
                        </div>

                        <!-- Filtro por Turma -->
                        <div class="mb-3">
                            <label for="turmaId" class="form-label">Turma</label>
                            <select name="turmaId" class="form-select">
                                <option value="">Selecione uma turma</option>
                                <% for (Turma turma : turmas) { %>
                                    <option value="<%= turma.getId() %>"><%= turma.getCodigoTurma() %></option>
                                <% } %>
                            </select>
                        </div>

                        <div>
                            <input type="submit" name="btGerar" value="Gerar Relatório" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/dashboard" class="btn btn-secondary">Voltar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
