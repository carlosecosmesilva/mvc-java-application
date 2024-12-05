<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.ArrayList"%>
<%@page import="entidade.Aluno"%>

<!DOCTYPE html>
<html lang="pt-br">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="shortcut icon" href="#">
        <title>Aluno</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>

    <body>

        <div class="container">
            <jsp:include page="../../comum/menu.jsp" />
            <div class="row mt-5">
                <div class="col-sm-6 offset-3">
                    <%
                        Aluno aluno = (Aluno) request.getAttribute("aluno");
                        String acao = (String) request.getAttribute("acao");
                        switch (acao) {
                            case "Incluir":
                                out.println("<h1>Incluir Aluno</h1>");
                                break;
                            case "Alterar":
                                out.println("<h1>Alterar Aluno</h1>");
                                break;
                            case "Excluir":
                                out.println("<h1>Excluir Aluno</h1>");
                                break;
                        }

                        String msgError = (String) request.getAttribute("msgError");
                        if ((msgError != null) && (!msgError.isEmpty())) {%>
                    <div class="alert alert-danger" role="alert">
                        <%= msgError%>
                    </div>
                    <% }%>
                    <form action="/aplicacaoMVC/admin/AlunoController" method="POST" accept-charset="UTF-8">
                        <input type="hidden" name="id" value="<%=aluno.getId()%>" class="form-control">

                        <div class="mb-3">
                            <label for="nome" class="form-label">Nome</label>
                            <input type="text" name="nome" value="<%=aluno.getNome()%>" 
                                   class="form-control" required maxlength="50" 
                                   oninput="validateLength(this, 50)" <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" name="email" value="<%=aluno.getEmail()%>" 
                                   class="form-control" required maxlength="50" 
                                   oninput="validateLength(this, 50)" <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div class="mb-3">
                            <label for="celular" class="form-label">Celular</label>
                            <input type="text" name="celular" value="<%=aluno.getCelular()%>" 
                                   class="form-control" required maxlength="14" 
                                   placeholder="(XX) XXXXX-XXXX" oninput="validateLength(this, 14)"
                                   <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div class="mb-3">
                            <label for="cpf" class="form-label">CPF</label>
                            <input type="text" name="cpf" value="<%=aluno.getCpf()%>" 
                                   class="form-control" required maxlength="14" 
                                   placeholder="XXX.XXX.XXX-XX" oninput="validateLength(this, 14)"
                                   <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div class="mb-3">
                            <label for="senha" class="form-label">Senha</label>
                            <input type="password" name="senha" value="<%=aluno.getSenha()%>" 
                                   class="form-control" required maxlength="255" 
                                   oninput="validateLength(this, 255)" <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div class="mb-3">
                            <label for="endereco" class="form-label">Endereço</label>
                            <input type="text" name="endereco" value="<%=aluno.getEndereco()%>" 
                                   class="form-control" maxlength="50" 
                                   oninput="validateLength(this, 50)" <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div class="mb-3">
                            <label for="cidade" class="form-label">Cidade</label>
                            <input type="text" name="cidade" value="<%=aluno.getCidade()%>" 
                                   class="form-control" maxlength="30" oninput="validateLength(this, 30)" <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div class="mb-3">
                            <label for="bairro" class="form-label">Bairro</label>
                            <input type="text" name="bairro" value="<%=aluno.getBairro()%>" 
                                   class="form-control" maxlength="30" 
                                   oninput="validateLength(this, 30)" <%=acao.equals("Excluir") ? "readonly" : ""%>>
                            <small>Máximo de 30 caracteres, incluindo espaços.</small>
                        </div>

                        <script>
                            function validateLength(input, maxLength) {
                                if (input.value.length > maxLength) {
                                    input.value = input.value.slice(0, maxLength); // Corta o excesso de caracteres.
                                }
                            }
                        </script>


                        <div class="mb-3">
                            <label for="cep" class="form-label">CEP</label>
                            <input type="text" name="cep" value="<%=aluno.getCep()%>" 
                                   class="form-control" maxlength="9" placeholder="XXXXX-XXX"
                                   oninput="validateLength(this, 9)" 
                                   <%=acao.equals("Excluir") ? "readonly" : ""%>>
                        </div>

                        <div>
                            <input type="submit" name="btEnviar" value="<%=acao%>" class="btn btn-primary">
                            <a href="/aplicacaoMVC/admin/AlunoController?acao=Listar" class="btn btn-danger">Retornar</a>
                        </div>
                    </form>


                </div>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>

</html>
