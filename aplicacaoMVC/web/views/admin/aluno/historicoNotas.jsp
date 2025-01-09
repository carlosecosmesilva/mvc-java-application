<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Histórico de Notas</title>
        <link rel="stylesheet" href="/aplicacaoMVC/css/bootstrap.min.css">
    </head>
    <body>
        <div class="container">
            <h2>Histórico de Notas</h2>
            <c:if test="${not empty listaNotas}">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Disciplina/Turma</th>
                            <th>Nota</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="nota" items="${listaNotas}">
                        <tr>
                            <td>${nota.disciplina}</td>
                            <td>${nota.nota != null ? nota.nota : "Não lançada"}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <c:if test="${empty listaNotas}">
                <p>Você ainda não tem notas lançadas.</p>
            </c:if>
        </div>
    </body>
</html>
