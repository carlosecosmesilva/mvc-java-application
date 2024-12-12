<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Disciplina"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Dashboard</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="/views/comum/menu.jsp" />
            <div class="mt-5">
                <h1 class="mb-4">Dashboard</h1>

                <div class="mb-3">
                    <a href="/views/public/home" class="btn btn-secondary">Retornar</a>
                </div>

                <div class="card mb-4">
                    <div class="card-header">
                        <h2 class="h5">Disciplinas Disponíveis</h2>
                    </div>
                    <div class="card-body">
                        <%
                            ArrayList<Disciplina> listaDisciplinas = (ArrayList<Disciplina>) request.getAttribute("listaDisciplinas");
                            if (listaDisciplinas != null && !listaDisciplinas.isEmpty()) {
                        %>

                        <div class="table-responsive">
                            <table class="table table-striped table-bordered">
                                <thead class="table-dark">
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">Nome</th>
                                        <th scope="col">Vagas Disponíveis</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for (Disciplina disciplina : listaDisciplinas) {
                                    %>
                                    <tr>
                                        <td><%= disciplina.getId()%></td>
                                        <td><%= disciplina.getNome()%></td>
                                        <td><%= disciplina.getVagasDisponiveis()%></td>
                                    </tr>
                                    <%
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                        <%
                        } else {
                        %>
                        <div class="alert alert-warning" role="alert">
                            Nenhuma disciplina disponível no momento.
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
