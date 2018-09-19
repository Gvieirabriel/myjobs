<%-- 
    Document   : atividades
    Created on : Jun 4, 2017, 12:05:14 PM
    Author     : guilh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<c:if test="${sessionScope.funcionarioatoa.cargo.nomeCargo == 'Gerente' || empty sessionScope.funcionarioatoa.cargo.nomeCargo}">
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
        <title>Atividades</title>
    </head>
    <body>
        <center>
                <nav class="navbar navbar-inverse" style="background-color: #494949;">
                    <div class="container-fluid">
                      <div class="navbar-header">
                        <a class="navbar-brand" href="/MYJOBS/Atividades"><img src="/MYJOBS/css/logo2.png"style="width: 76%; margin: -0.5vh"> </a>
                      </div>
                      <ul class="nav navbar-nav">
                        <li><a href="/MYJOBS/Atividades">Atividades</a></li>
                      </ul>
                      <ul class="nav navbar-nav navbar-right">
                        <li>
                            <div style="margin-top: 2vh; color: #ccc;">
                                Bem vindo, <c:out value="${sessionScope.funcionarioatoa.nomeFuncionario}"/><span style="float:right;"></span>
                            </div>
                        </li>
                        <li><a href="/MYJOBS/ProcessaLogout"><span class="glyphicon glyphicon-log-in"></span> Logout</a></li>
                      </ul>
                    </div>
                  </nav>
            <h1 style=" margin-top: 20vh;">Atividades</h1>
            <br/>
            <c:choose>
                <c:when test="${!empty lista}">
                <div class="container">
                    <table border="1" cellspacing="1" class="table">
                        <tr>
                            <th>ID</th>
                            <th>Tipo</th>
                            <th>Descrição</th>
                            <th>Início</th>
                            <th>Fim</th>
                            <th>Status</th>
                            <th>Alterar</th>
                            <th>Finalizar</th>                            
                        </tr>
                        <c:forEach items="${lista}" var="item">
                            <tr>
                                <td>${item.idAtividade}</td>
                                <td>${item.tipoAtividade.nome}</td>
                                <td>${item.descricao}</td>
                                <td><fmt:setLocale value = "pt_BR"/><fmt:formatDate value="${item.inicio}" type="date"/></td>
                                <td><fmt:setLocale value = "pt_BR"/><fmt:formatDate value="${item.fim}" type="date"/></td>
                                <td>${item.estado}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.idEstado == 1}">
                                            <a href="AlterarAtividade?atv=<c:out value="${item.idAtividade}"/>"><span class="glyphicon glyphicon-pencil" name="Alterar" value="Alterar" style="font-size: 1.5vw;"></span></a>
                                        </c:when>
                                        <c:when test="${item.idEstado == 2}">
                                            <a href="AlterarAtividade?atv=<c:out value="${item.idAtividade}"/>"><span class="glyphicon glyphicon-pencil" name="Alterar" value="Alterar" style="font-size: 1.5vw;"></span></a>
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${item.idEstado == 1}">
                                            <a href="FinalizarAtividade?atv=<c:out value="${item.idAtividade}"/>"><span class="glyphicon glyphicon-ok" name="Remover" value="Remover" onmouseover="this.style.color='#009900'" onmouseout="this.style.color='#00ff00'" style="color: red; font-size: 1.5vw;"></span></a>
                                        </c:when>
                                        <c:when test="${item.idEstado == 2}">
                                            <a href="FinalizarAtividade?atv=<c:out value="${item.idAtividade}"/>&est=<c:out value="${item.idEstado}"/>"><span class="glyphicon glyphicon-ok" name="Remover" value="Remover" onmouseover="this.style.color='#009900'" onmouseout="this.style.color='#00ff00'" style="color: red; font-size: 1.5vw;"></span></a>
                                        </c:when>
                                        <c:otherwise>
                                            -
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                    <a href="CadastrarAtividade"><input type="button" class="btn btn-warning" name="Cadastrar" value="Cadastrar" style="margin-bottom: 10vh;"/></a>
                </div>
                </c:when>
                <c:otherwise>
                    <h4>Você ainda não possui atividades!</h4>
                    <br/><br/>
                    <a href="CadastrarAtividade"><input type="button" class="btn btn-warning" name="Cadastrar" value="Cadastrar" style="margin-bottom: 10vh;"/></a>
                </c:otherwise>
            </c:choose>
        </center>
    </body>
</html>
