<%-- 
    Document   : index
    Created on : May 30, 2017, 8:21:09 PM
    Author     : guilh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html style="height: 100%;">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
        <title>MYJOBS</title>
    </head>
    <body  style="height: 100%;">
        <center style="background-image: url(css/MYJOBS.jpg); background-size: 100%; background-position: center; height: 100%;">
            <h4>${msg}</h4>
            </br></br></br>
            <div class="container " style="width: 36%">
                <img src="css/logo1.png" style="width: 50%;" > 
                <form action="http://localhost:8080/MYJOBS/ProcessaLogin" method="post">
                    <div align="center" class="form-group jumbotron">
                        Email:<input class="form-control" type="text" name="email" id="email"><br/>
                        Senha:<input class="form-control" type="password" name="senha" id="senha"><br/>
                        <input type="submit" class="btn btn-warning btn-lg btn-block" value="Logar">
                    </div>
                </form>
            </div>
        </center>
    </body>
</html>
