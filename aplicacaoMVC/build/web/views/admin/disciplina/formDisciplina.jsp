<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="entidade.Disciplina"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Cadastro de Disciplinas</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-6 offset-3">
                    <%
                        Disciplina disciplina = (Disciplina) request.getAttribute("disciplina");
                        String acao = (String) request.getAttribute("acao");

                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Disciplina</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Disciplina</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Disciplina</h1>");
                                break;
                        }

                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                            <div class="alert alert-danger" role="alert">
                                <%= msgError %>
                            </div>
                        <% } %>

                    <form action="/aplicacaoMVC/admin/DisciplinaController" method="POST">
                        <input type="hidden" name="id" value="<%= disciplina != null ? disciplina.getId() : 0 %>" class="form-control">

                        <!-- Campo Nome -->
                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" name="nome" class="form-control"
                                   value="<%= disciplina != null ? disciplina.getNome() : "" %>"
                                   <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                        </div>

                        <!-- Campo Requisito -->
                        <div class="mb-3">
                            <label for="requisito" class="form-label">Requisito</label>
                            <input type="text" name="requisito" class="form-control"
                                   value="<%= disciplina != null ? disciplina.getRequisito() : "" %>"
                                   <%= acao.equals("Excluir") ? "readonly" : "" %>>
                        </div>

                        <!-- Campo Ementa -->
                        <div class="mb-3">
                            <label for="ementa" class="form-label">Ementa</label>
                            <textarea name="ementa" class="form-control"
                                      rows="3"
                                      <%= acao.equals("Excluir") ? "readonly" : "" %>>
                                <%= disciplina != null ? disciplina.getEmenta() : "" %>
                            </textarea>
                        </div>

                        <!-- Campo Carga Horária -->
                        <div class="mb-3">
                            <label for="cargaHoraria" class="form-label">Carga Horária</label>
                            <input type="number" name="cargaHoraria" class="form-control"
                                   value="<%= disciplina != null ? disciplina.getCargaHoraria() : 0 %>"
                                   <%= acao.equals("Excluir") ? "readonly" : "" %> required>
                        </div>

                        <div>
                            <input type="submit" name="btEnviar" value="<%= acao %>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/DisciplinaController?acao=listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
