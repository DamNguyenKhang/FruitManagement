<%-- 
    Document   : register
    Created on : Feb 25, 2025, 10:07:54 AM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="model.User" scope="session"/>
<jsp:setProperty name="user" property="*" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>
        <div>
            <span>Đăng ký</span>
            <h5 style="color:red">${requestScope.error}</h5>
            <h5 style="color:green">${requestScope.success}</h5>
            <form action="register" method="post">
                <div>
                    <input type="text" name="userName" placeholder="Tên đăng nhập" value="${user.userName}"/>

                </div>
                <div>
                    <input type="password" name="password" placeholder="Mật khẩu" value="${user.password}"/>
                </div>
                <div>
                    <input type="password" name="re-password" placeholder="Nhập lại mật khẩu"/>
                </div>
                <div>
                    <input type="text" name="email" placeholder="Email" value="${user.email}"/>
                </div>
                <div>
                    <input type="text" name="phone" placeholder="Số điện thoại" value="${user.phone}"/>
                </div>
                <button type="submit">Đăng ký</button>

            </form>

        </div> 
    </body>
</html>
