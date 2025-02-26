<%-- 
    Document   : login
    Created on : Feb 17, 2025, 9:44:33 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Đăng nhập</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: whitewhite;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .login-container {
                display: flex;
                flex-direction: column;
                background: white;
                padding: 30px;
                width: 500px;
                text-align: center;
            }

            .login-container h2 {
                margin-bottom: 20px;
            }

            .input-group {
                margin-bottom: 15px;
                text-align: center;
                position: relative;
            }

            .input-group input {
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
                width: 96%;
                margin: auto;
                display: block;
            }

            .input-group .toggle-password {
                position: absolute;
                right: 10px;
                top: 50%;
                transform: translateY(-50%);
                cursor: pointer;
            }
            .remember-me {
                display: flex;
                align-items: center;
                justify-content: flex-start;
                width: 90%;
                margin: auto;
                font-size: 14px;
                text-align: left;
            }
            .remember-me input {
                margin-right: 5px;
            }

            button {
                width: 100%;
                padding: 10px;
                border-radius: 5px;
                cursor: pointer;
                font-size: 16px;
                margin: 10px auto;
                display: block;
            }

            .login-btn {
                background-color: orange;
                border: none;
                color: white;
            }

            .sign-up-btn {
                background-color: white;
                border: 1px solid rgb(83, 83, 83);
                color: rgb(83, 83, 83);
            }

            .sign-up-btn:hover {
                color: orange;
                border-color: orange;
                box-shadow: 0 0 3px orange;
            }

            .login-btn:hover {
                background-color: rgb(220, 143, 0);
                box-shadow: 0 0 3px orange;
            }

            .forget-password {
                display: block;
                font-size: 14px;
                color: blue;
                text-decoration: none;
                text-align: right;
                width: 90%;
                margin: 0 0 30px auto;
            }


        </style>
    </head>
    <body>
        <div class="login-container">
            <h2>Đăng nhập</h2>
            <form action="login" method="POST">

                <div class="input-group">
                    <input type="text" id="username" name="userName" placeholder="Nhập email hoặc số điện thoại" required>
                </div>
                <div class="input-group">
                    <input type="password" id="password" name="password" placeholder="Mật khẩu" required>
                    <span class="toggle-password" onclick="togglePassword()">
                        <i class="bi bi-eye"></i>
                    </span>
                </div>
                <div class="forget-password">
                    <a href="#" name="forget-password">Quên mật khẩu?</a>
                </div>
                <div class="remember-me">
                    <input type="checkbox" name="remember-me" id="remember-me">
                    <label for="remember-me">Remember me</label>
                </div>

                <button type="submit" class="login-btn">Đăng nhập</button>
            </form>
            <button type="button" class="sign-up-btn">Đăng ký</button>
        </div>

        <script>
            function togglePassword() {
                var passwordInput = document.getElementById("password");
                var toggleIcon = document.querySelector(".toggle-password i");
                if (passwordInput.type === "password") {
                    passwordInput.type = "text";
                    toggleIcon.classList.remove("bi-eye");
                    toggleIcon.classList.add("bi-eye-slash");
                } else {
                    passwordInput.type = "password";
                    toggleIcon.classList.remove("bi-eye-slash");
                    toggleIcon.classList.add("bi-eye");
                }
            }
        </script>
    </body>
</html>
