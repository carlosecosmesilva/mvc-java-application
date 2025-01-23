<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="entidade.Turma" %>
<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="#">
    <title>Inscrição em Disciplina</title>
    <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
</head>

<body>
    <div class="container">
        <jsp:include page="../../comum/menu.jsp" />
        <div class="row mt-5">
            <div class="col-sm-6 offset-3">
                <h2>Inscrição em Disciplina</h2>

                <form action="/aplicacaoMVC/aluno/InscricaoController" method="post">
                    <div class="form-group">
                        <label for="turmaId">Escolha a Disciplina/Turma:</label>
                        <select class="form-control" id="turmaId" name="turmaId" required>
                            <%
                                // Recupera a lista de turmas do atributo da requisição
                                List<Turma> listaTurmas = (List<Turma>) request.getAttribute("listaTurmas");
                                if (listaTurmas != null && !listaTurmas.isEmpty()) {
                                    for (Turma turma : listaTurmas) {
                            %>
                                <option value="<%= turma.getId() %>">
                                    <%= turma.getDisciplinaNome() %> - Turma: <%= turma.getCodigoTurma() %> 
                                    (Professor: <%= turma.getProfessorNome() %>)
                                </option>
                            <%
                                    }
                                } else {
                            %>
                                <option disabled>Nenhuma turma disponível</option>
                            <%
                                }
                            %>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary">Inscrever-se</button>
                </form>
            </div>
        </div>
    </div>

    <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
</body>

</html>
