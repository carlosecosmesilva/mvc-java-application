<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Objects"%>

<!DOCTYPE html>
<html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Erro</title>
        <link href="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <jsp:include page="/views/comum/menu.jsp" />
            <div class="mt-5">
                <h1 class="text-danger">Ocorreu um Erro</h1>
                
                <div class="alert alert-danger mt-3" role="alert">
                    <% 
                        String mensagemErro = (String) request.getAttribute("mensagemErro");
                        if (Objects.nonNull(mensagemErro) && !mensagemErro.isEmpty()) {
                            out.println(mensagemErro);
                        } else {
                            out.println("Um erro inesperado ocorreu. Por favor, tente novamente.");
                        }
                    %>
                </div>
                
                <a href="/aplicacaoMVC/home" class="btn btn-primary">Voltar ao Início</a>
            </div>
        </div>

        <script src="http://localhost:8080/aplicacaoMVC/views/bootstrap/bootstrap.bundle.min.js"></script>
    </body>
</html>
