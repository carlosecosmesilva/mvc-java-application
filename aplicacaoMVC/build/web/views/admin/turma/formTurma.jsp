<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="entidade.Professor"%>
<%@page import="entidade.Disciplina"%>
<%@page import="entidade.Aluno"%>
<%@page import="entidade.Turma"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Cadastro de Turmas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-8 offset-2">
                    <%
                        Turma turma = (Turma) request.getAttribute("turma");
                        String acao = (String) request.getAttribute("acao");
                        List<Professor> professores = (List<Professor>) request.getAttribute("listaProfessores");
                        List<Disciplina> disciplinas = (List<Disciplina>) request.getAttribute("listaDisciplinas");
                        List<Aluno> alunos = (List<Aluno>) request.getAttribute("listaAlunos");

                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Turma</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Turma</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Turma</h1>");
                                break;
                        }

                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                            <div class="alert alert-danger" role="alert">
                                <%= msgError %>
                            </div>
                        <% } %>

                    <form action="/aplicacaoMVC/admin/TurmaController" method="POST">
                        <input type="hidden" name="id" value="<%= turma != null ? turma.getId() : 0 %>" class="form-control">
                        
                        <!-- Campo para Selecionar Professor -->
                        <div class="mb-3">
                            <label for="professorId" class="form-label">Professor</label>
                            <select name="professorId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %> required>
                                <option value="">Selecione um professor</option>
                                <% for (Professor professor : professores) { %>
                                    <option value="<%= professor.getId() %>" <%= (turma != null && turma.getProfessorId() == professor.getId()) ? "selected" : "" %>>
                                        <%= professor.getNome() %>
                                    </option>
                                <% } %>
                            </select>
                        </div>

                        <!-- Campo para Selecionar Disciplina -->
                        <div class="mb-3">
                            <label for="disciplinaId" class="form-label">Disciplina</label>
                            <select name="disciplinaId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %> required>
                                <option value="">Selecione uma disciplina</option>
                                <% for (Disciplina disciplina : disciplinas) { %>
                                    <option value="<%= disciplina.getId() %>" <%= (turma != null && turma.getDisciplinaId() == disciplina.getId()) ? "selected" : "" %>>
                                        <%= disciplina.getNome() %>
                                    </option>
                                <% } %>
                            </select>
                        </div>

                        <!-- Campo para Selecionar Aluno -->
                        <div class="mb-3">
                            <label for="alunoId" class="form-label">Aluno</label>
                            <select name="alunoId" class="form-select" <%= acao.equals("Excluir") ? "disabled" : "" %> required>
                                <option value="">Selecione um aluno</option>
                                <% for (Aluno aluno : alunos) { %>
                                    <option value="<%= aluno.getId() %>" <%= (turma != null && turma.getAlunoId() == aluno.getId()) ? "selected" : "" %>>
                                        <%= aluno.getNome() %>
                                    </option>
                                <% } %>
                            </select>
                        </div>

                        <!-- Campo para Código da Turma -->
                        <div class="mb-3">
                            <label for="codigoTurma" class="form-label">Código da Turma</label>
                            <input type="text" name="codigoTurma" class="form-control" 
                                   value="<%= turma != null ? turma.getCodigoTurma() : "" %>" 
                                   <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                        </div>

                        <!-- Campo para Nota -->
                        <div class="mb-3">
                            <label for="nota" class="form-label">Nota</label>
                            <input type="number" step="0.01" name="nota" class="form-control" 
                                   value="<%= turma != null ? turma.getNota() : 0.0 %>" 
                                   <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                        </div>

                        <div>
                            <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/TurmaController?acao=listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
