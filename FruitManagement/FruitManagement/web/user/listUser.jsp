<%-- 
    Document   : UserList
    Created on : Feb 13, 2025, 1:17:40 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <center>
        <h1>User Management</h1>
    </center>
    <center>
        <form action="users" method="get">
            <input type="hidden" name="action" value="search">
            Enter User Name: <input type="text" name="name">
            <input type="submit" value="search">
        </form>
    </center>
    <div align="center">
        <table border="1" cellpadding="5">

            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Password</th>
                <th>Role</th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td><c:out value="${user.userID}"/></td>
                    <td><c:out value="${user.userName}"/></td>
                    <td><c:out value="${user.password}"/></td>
                    <td><c:out value="${user.role}"/></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</body>
</html>
