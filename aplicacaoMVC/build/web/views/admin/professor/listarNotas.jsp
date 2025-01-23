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
        <title>Lista de Notas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Lista de Notas dos Alunos</h2>

                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Email</th>
                                <th scope="col">Celular</th>
                                <th scope="col">CPF</th>
                                <th scope="col">Nota</th>
                                <th scope="col">Turma</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                // Recuperando a lista de alunos e turmas da requisição
                                ArrayList<Aluno> listaAlunos = (ArrayList<Aluno>) request.getAttribute("listaAlunos");
                                ArrayList<Turma> listaTurmas = (ArrayList<Turma>) request.getAttribute("listaTurmas");

                                if (listaAlunos != null && !listaAlunos.isEmpty()) {
                                    for (Aluno aluno : listaAlunos) {
                                        Turma turma = null;
                                        // Buscando a turma associada ao aluno
                                        for (Turma t : listaTurmas) {
                                            if (t.getAlunoId() == aluno.getId()) {
                                                turma = t;
                                                break;
                                            }
                                        }
                                        out.println("<tr>");
                                        out.println("<th>" + aluno.getId() + "</th>");
                                        out.println("<td>" + aluno.getNome() + "</td>");
                                        out.println("<td>" + aluno.getEmail() + "</td>");
                                        out.println("<td>" + aluno.getCelular() + "</td>");
                                        out.println("<td>" + aluno.getCpf() + "</td>");
                                        out.println("<td>" + (turma != null && turma.getNota() != 0.0 ? turma.getNota() : "Não lançada") + "</td>");
                                        out.println("<td>" + (turma != null ? turma.getId() : "Não atribuída") + "</td>");
                                    }
                                } else {
                                    out.println("<tr><td colspan='7'>Nenhum aluno encontrado.</td></tr>");
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
