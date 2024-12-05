<%@page import="java.util.List"%>
<%@page import="entidade.Turma"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Lista de Turmas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Lista de Turmas</h1>
                <a href="/aplicacaoMVC/admin/TurmaController?acao=Incluir" class="mb-2 btn btn-primary">Incluir Turma</a>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">ID</th>
                                <th scope="col">Código da Turma</th>
                                <th scope="col">Professor</th>
                                <th scope="col">Disciplina</th>
                                <th scope="col">Aluno</th>
                                <th scope="col">Nota</th>
                                <th scope="col">Ações</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% 
                                List<Turma> listaTurmas = (List<Turma>) request.getAttribute("listaTurmas");
                                if (listaTurmas != null && !listaTurmas.isEmpty()) {
                                    for (Turma turma : listaTurmas) { %>
                                        <tr>
                                            <td><%= turma.getId() %></td>
                                            <td><%= turma.getCodigoTurma() %></td>
                                            <td><%= turma.getProfessorId() %></td>
                                            <td><%= turma.getDisciplinaId() %></td>
                                            <td><%= turma.getAlunoId() %></td>
                                            <td><%= turma.getNota() %></td>
                                            <td>
                                                <a href="/aplicacaoMVC/admin/TurmaController?acao=Alterar&id=<%= turma.getId() %>" class="btn btn-warning">Alterar</a>
                                                <a href="/aplicacaoMVC/admin/TurmaController?acao=Excluir&id=<%= turma.getId() %>" class="btn btn-danger">Excluir</a>
                                            </td>
                                        </tr>
                                    <% }
                                } else { %>
                                    <tr>
                                        <td colspan="7" class="text-center">Nenhuma turma encontrada.</td>
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
