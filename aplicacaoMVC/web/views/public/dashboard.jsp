<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Disciplinas Disponíveis</title>
    <link rel="stylesheet" href="../bootstrap/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h1 class="text-center">Disciplinas Disponíveis</h1>
        <table class="table table-striped mt-3">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Nome da Disciplina</th>
                    <th>Vagas Disponíveis</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="disciplina" items="${listaDisciplinas}">
                    <tr>
                        <td>${disciplina.id}</td>
                        <td>${disciplina.nome}</td>
                        <td>${disciplina.vagasDisponiveis}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <script src="../bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
