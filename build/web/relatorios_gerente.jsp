<%-- 
    Document   : relatorios_gerente
    Created on : Jun 6, 2017, 9:54:09 PM
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
        <title>Relatórios</title>
    </head>
    <body>
        <center>
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
            <h1>Relatórios</h1>
            <br/>
            <div class="container " style="width: 28%">
                <div align="center" class="form-group jumbotron">
                    <h3>Funcionário</h3>
                    <form action="RelatorioFuncionario">
                        <input type="hidden" name="rel" value="1">
                        <label>Nome: </label>
                        <select class="form-control" name="funcionario">
                            <c:forEach items="${listaFuncionario}" var="f">
                                <option value="${f.idFuncionario}">${f.nomeFuncionario}</option>
                            </c:forEach>
                        </select>
                        <br/><br/>
                        <input class="btn btn-warning" type="submit" value="Gerar">
                    </form>
                </div>
                <div align="center" class="form-group jumbotron">
                    <h3>Departamento</h3>
                    <form action="RelatorioDepartamento">
                        <input type="hidden" name="rel" value="2">
                        <div style="flex-flow: row wrap; display: flex;">
                            <label>Dia: </label>
                            <input type="date" class="form-control" name="data">
                        </div>
                        <br/><br/>
                        <input class="btn btn-warning" type="submit" value="Gerar">
                    </form>
                </div>
                <br/>
            </div>
        </center>
    </body>
</html>
