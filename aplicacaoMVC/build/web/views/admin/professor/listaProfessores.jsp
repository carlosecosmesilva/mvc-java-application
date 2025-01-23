<%@page import="entidade.Professor"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lista de Professores</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Lista de Professores</h1>
                <a href="/aplicacaoMVC/admin/ProfessorController?acao=Incluir" class="mb-2 btn btn-primary">Incluir</a>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Email</th>
                                <th>CPF</th>
                                <th>Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Professor> listaProfessores = (ArrayList<Professor>) request.getAttribute("listaProfessores");

                                if (listaProfessores != null && !listaProfessores.isEmpty()) {
                                    for (Professor professor : listaProfessores) {
                            %>
                            <tr>
                                <th><%= professor.getId()%></th>
                                <td><%= professor.getNome()%></td>
                                <td><%= professor.getEmail()%></td>
                                <td><%= professor.getCpf()%></td>
                                <td>
                                    <a href="/aplicacaoMVC/admin/ProfessorController?acao=Alterar&id=<%= professor.getId()%>" class="btn btn-warning">Alterar</a>
                                    <a href="/aplicacaoMVC/admin/ProfessorController?acao=Excluir&id=<%= professor.getId()%>" class="btn btn-danger">Excluir</a>
                                </td>
                            </tr>
                            <%
                                }
                            } else {
                            %>
                            <tr>
                                <td colspan="5" class="text-center">Nenhum professor encontrado.</td>
                            </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
