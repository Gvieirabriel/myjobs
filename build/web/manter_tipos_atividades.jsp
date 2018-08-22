<%-- 
    Document   : manter_tipos_atividades
    Created on : Jun 4, 2017, 12:04:57 PM
    Author     : guilh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:if test="${sessionScope.funcionarioMYJOBS.cargo.nomeCargo != 'Gerente'}">
    <c:redirect url="/erro.jsp">
        <c:param name="msg" value="Acesso negado!"/>
    </c:redirect>
</c:if>
<!DOCTYPE html>
<html style="height: 100%;">
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
        <title>Manter Tipos Atividades</title>
    </head>
    <body style="height: 100%;">
        <center style="height: 100%;">
                <nav class="navbar navbar-inverse" style="background-color: #494949;">
                    <div class="container-fluid">
                      <div class="navbar-header">
                        <a class="navbar-brand" href="/MYJOBS/manter_tipos_atividades.jsp"><img src="/MYJOBS/css/logo2.png"style="width: 76%; margin: -0.5vh"></a>
                      </div>
                      <ul class="nav navbar-nav">
                        <li><a href="/MYJOBS/manter_tipos_atividades.jsp">Tipos Atividades</a></li>
                        <li><a href="/MYJOBS/ManterAtividades">Atividades</a></li>
                        <li><a href="/MYJOBS/ManterRelatorios">Relatórios</a></li>
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
            <h1>Manter Tipos Atividades</h1>
            <br/>
            <div class="container " style="width: 35%">
                <div align="center" class="form-group jumbotron">
                    <div align="left"><label>Buscar: </label></div>
                    <form action="BuscarTiposAtividades" method="POST" style="display: flex">
                            <input type="text" name="buscaTipoAtividade" class="form-control" placeholder="Buscar por nome" style="margin-right: 10px">
                            <input type="submit" class="btn btn-warning" value="Buscar">
                    </form>
                    <br/><br/>
                    <a href="cadastrar_tipo_atividade.jsp"><input class="btn btn-warning" type="submit" value="Cadastrar"></a>
                </div>
            </div>
        </center>
    </body>
</html>
