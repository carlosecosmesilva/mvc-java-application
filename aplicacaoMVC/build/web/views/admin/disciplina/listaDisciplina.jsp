<%@page import="java.util.List"%>
<%@page import="entidade.Disciplina"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Disciplinas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Lista de Disciplinas</h1>
                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Incluir" class="mb-2 btn btn-primary">Incluir Disciplina</a>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Requisito</th>
                                <th scope="col">Ementa</th>
                                <th scope="col">Carga Horária</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                List<Disciplina> listaDisciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
                                if (listaDisciplinas != null && !listaDisciplinas.isEmpty()) {
                                    for (Disciplina disciplina : listaDisciplinas) { %>
                                        <tr>
                                            <td><%= disciplina.getId() %></td>
                                            <td><%= disciplina.getNome() %></td>
                                            <td><%= disciplina.getRequisito() %></td>
                                            <td><%= disciplina.getEmenta() %></td>
                                            <td><%= disciplina.getCargaHoraria() %></td>
                                            <td>
                                                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Alterar&id=<%= disciplina.getId() %>" class="btn btn-warning">Alterar</a>
                                                <a href="/aplicacaoMVC/admin/DisciplinaController?acao=Excluir&id=<%= disciplina.getId() %>" class="btn btn-danger">Excluir</a>
                                            </td>
                                        </tr>
                                    <% }
                                } else { %>
                                    <tr>
                                        <td colspan="6" class="text-center">Nenhuma disciplina encontrada.</td>
                                    </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
