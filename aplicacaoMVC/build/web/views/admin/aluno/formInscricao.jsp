<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Inscrição em Disciplina</title>
    <link rel="stylesheet" href="/aplicacaoMVC/css/bootstrap.min.css">
</head>
<body>
    <div class="container">
        <h2>Inscrição em Disciplina</h2>
        <form action="/aplicacaoMVC/aluno/InscricaoController" method="post">
            <div class="form-group">
                <label for="turmaId">Escolha a Disciplina/Turma:</label>
                <select class="form-control" id="turmaId" name="turmaId" required>
                    <c:forEach var="turma" items="${listaTurmas}">
                        <option value="${turma.id}">${turma.nome} - ${turma.numeroVagas} vagas restantes</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Inscrever-se</button>
        </form>
    </div>
</body>
</html>
