<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Aluno"%>
<%@page import="entidade.Turma"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Lançar Nota</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Lançar Nota para Aluno</h2>

                <!-- Formulário para lançar a nota -->
                <form action="/aplicacaoMVC/admin/TurmaController?acao=LancarNota" method="POST">
                    <div class="mb-3">
                        <label for="alunoId" class="form-label">Aluno</label>
                        <select class="form-select" id="alunoId" name="alunoId" required>
                            <option value="">Selecione um Aluno</option>
                            <%
                                // Recuperando a lista de alunos da requisição
                                ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");

                                if (listaAlunos != null) {
                                    for (Aluno aluno : listaAlunos) {
                            %>
                            <option value="<%= aluno.getId() %>"><%= aluno.getNome() %></option>
                            <%
                                    }
                                }
                            %>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="nota" class="form-label">Nota</label>
                        <input type="number" class="form-control" id="nota" name="nota" step="0.01" min="0" max="10" required>
                    </div>

                    <input type="hidden" name="turmaId" value="<%= request.getAttribute("turmaId") %>">

                    <button type="submit" class="btn btn-primary">Lançar Nota</button>
                </form>

            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
