<%-- 
    Document   : buscar_tipos_atividades
    Created on : Jun 4, 2017, 8:46:43 PM
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
        <title>Buscar Tipos Atividades</title>
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
            <h1>Buscar Tipos Atividades</h1>
            <br/>
            <c:choose>
                <c:when test="${!empty lista}">
                <div class="container">
                    <table border="1" cellspacing="1" class="table">
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Descrição</th>
                            <th>Alterar</th>
                            <th>Deletar</th>                            
                        </tr>
                        <c:forEach items="${lista}" var="item">
                            <tr>
                                <td>${item.idTipoAtividade}</td>
                                <td>${item.nome}</td>
                                <td>${item.descricao}</td>
                                <td><a href="AlterarTiposAtividades?tipo=<c:out value="${item.idTipoAtividade}"/>"><span class="glyphicon glyphicon-pencil" name="Alterar" value="Alterar" style="font-size: 1.5vw;"></span></a></td>
                                <td><a href="RemoverTiposAtividades?tipoa=<c:out value="${item.idTipoAtividade}"/>"><span class="glyphicon glyphicon-remove" name="Remover" value="Remover" onmouseover="this.style.color='#ca0202'" onmouseout="this.style.color='red'" style="color: red; font-size: 1.5vw;"></span></a></td>                                
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                </c:when>
                <c:otherwise>
                    <h4>Não foram encontrados tipos de atividades com este nome!</h4>
                </c:otherwise>
            </c:choose>
        </center>
    </body>
</html>
