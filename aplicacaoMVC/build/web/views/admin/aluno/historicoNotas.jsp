<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entidade.Turma" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

                <!-- Verificação se a lista está vazia -->
                <c:choose>
                    <c:when test="${not empty turmasAluno}">
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th scope="col">Disciplina/Turma</th>
                                        <th scope="col">Nota</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="turma" items="${turmasAluno}">
                                        <tr>
                                            <td>${turma.disciplinaNome != null ? turma.disciplinaNome : "Desconhecida"} - ${turma.codigoTurma != null ? turma.codigoTurma : "Código Não Disponível"}</td>
                                            <td>${turma.nota != null ? turma.nota : "Não lançada"}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="alert alert-warning">Você ainda não tem notas lançadas.</p>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <script src="/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
