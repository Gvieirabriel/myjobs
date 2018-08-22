<%-- 
    Document   : cadastrar_atividade
    Created on : Jun 5, 2017, 3:41:07 PM
    Author     : guilh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:if test="${sessionScope.funcionarioMYJOBS.cargo.nomeCargo == 'Gerente' || empty sessionScope.funcionarioMYJOBS.cargo.nomeCargo}">
    <c:redirect url="/erro.jsp">
        <c:param name="msg" value="Acesso negado!"/>
    </c:redirect>
</c:if>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>-->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <c:if test="${!empty requestScope.msg}">
            <script>
                alert("${requestScope.msg}");
            </script>
        </c:if>
        <title>Nova Atividade</title>
    </head>
    <body>
        <center>
            <nav class="navbar navbar-inverse" style="background-color: #494949;">
                <div class="container-fluid">
                  <div class="navbar-header">
                    <a class="navbar-brand" href="/MYJOBS/Atividades"><img src="/MYJOBS/css/logo2.png"style="width: 76%; margin: -0.5vh"></a>
                  </div>
                  <ul class="nav navbar-nav">
                    <li><a href="/MYJOBS/Atividades">Atividades</a></li>
                  </ul>
                  <ul class="nav navbar-nav navbar-right">
                    <li>
                        <div style="margin-top: 2vh; color: #ccc;">
                            Bem vindo, <c:out value="${sessionScope.funcionarioMYJOBS.nomeFuncionario}"/><span style="float:right;"></span>
                        </div>
                    </li>
                    <li><a href="/MYJOBS/ProcessaLogout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
                  </ul>
                </div>
              </nav>
              <br/><br/><br/>
            <h1>Nova Atividade</h1>
            <br/>
            <div class="container " style="width: 50%">
                <form action="CadastrarAtividade" class="form-group jumbotron" method="POST" role="form" style="text-align: left">
                    <label>Tipo:</label> 
                    <select class="form-control" name="Tipo">
                        <c:forEach items="${lista}" var="tipo">
                            <option value="${tipo.idTipoAtividade}">${tipo.nome}</option>
                        </c:forEach>
                    </select>
                    <br/>
                    <label>Descrição:</label> <input type="text" class="form-control" name="Descricao" placeholder="Descrição">
                    <br/>
                    <input type="reset" class="btn btn-warning" value="Limpar">
                    <input type="submit" class="btn btn-warning" value="Cadastrar">
                </form>
            </div>
        </center>
    </body>
</html>
