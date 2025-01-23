<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entidade.Turma" %>
<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Histórico de Notas</title>
        <link href="/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="mt-5">
                <h1>Área Restrita</h1>
                <h2>Histórico de Notas</h2>

                <%
                    List<Turma> turmasAluno = (List<Turma>) request.getAttribute("turmasAluno");
                    if (turmasAluno != null && !turmasAluno.isEmpty()) {
                %>
                <div class="table-responsive">
                    <table class="table table-bordered table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Disciplina/Turma</th>
                                <th scope="col">Nota</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for (Turma turma : turmasAluno) {
                                    String disciplinaNome = turma.getDisciplinaNome() != null ? turma.getDisciplinaNome() : "Desconhecida";
                                    String codigoTurma = turma.getCodigoTurma() != null ? turma.getCodigoTurma() : "Código Não Disponível";
                                    String nota = turma.getNota() > 0 ? String.valueOf(turma.getNota()) : "Não lançada";
                            %>
                            <tr>
                                <td><%= disciplinaNome %> - <%= codigoTurma %></td>
                                <td><%= nota %></td>
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
                <p class="alert alert-warning">Você ainda não tem notas lançadas.</p>
                <%
                    }
                %>
            </div>
        </div>

        <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
