<%-- 
    Document   : manter_atividades
    Created on : Jun 6, 2017, 9:53:50 PM
    Author     : guilh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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
        <title>Manter Atividades</title>
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
            <h1>Manter Atividades</h1>
            <br/>
            <c:choose>
                <c:when test="${!empty lista}">
                <div class="container">
                    <table border="1" cellspacing="1" class="table">
                        <tr>
                            <th>ID</th>
                            <th>Funcionário</th>
                            <th>Tipo</th>
                            <th>Descrição</th>
                            <th>Início</th>
                            <th>Fim</th>
                            <th>Status</th>
                            <th>Aprovar</th>
                            <th>Recusar</th>                            
                        </tr>
                        <c:forEach items="${lista}" var="item">
                            <tr>
                                <td>${item.idAtividade}</td>
                                <td>
                                    <c:forEach items="${listaFuncionario}" var="itemf">
                                        <c:if test="${itemf.idFuncionario == item.funcionario.idFuncionario}">
                                            <c:out value="${itemf.nomeFuncionario}"/>
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <td>${item.tipoAtividade.nome}</td>
                                <td>${item.descricao}</td>
                                <td><fmt:setLocale value = "pt_BR"/><fmt:formatDate value="${item.inicio}" type="date"/></td>
                                <td><fmt:setLocale value = "pt_BR"/><fmt:formatDate value="${item.fim}" type="date"/></td>
                                <td>${item.estado}</td>
                                <td><a href="ConsolidarAtividade?atv=<c:out value="${item.idAtividade}"/>&est=<c:out value="${item.idEstado}"/>"><input type="button" class="btn btn-warning" name="Consolidar" value="Aprovar" /></a></td>
                                <td><a href="RecusarAtividade?atv=<c:out value="${item.idAtividade}"/>"><input type="button" name="Recusar" class="btn btn-warning" value="Recusar" /></a></td>
                            </tr>
                        </c:forEach>
                    </table>
                    <a href="ConsolidarAtividade?atv=todas"><input type="button" class="btn btn-warning" name="Consolidar" value="Aprovar Todas" /></a>
                </div>
                </c:when>
                <c:otherwise>
                    <h4>Nenhum funcionário deste departamento possui atividades!</h4>
                    <br/><br/>
                </c:otherwise>
            </c:choose>
        </center>
    </body>
</html>
