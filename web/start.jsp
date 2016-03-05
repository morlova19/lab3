<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="./css/auth_page.css">
    <link rel="stylesheet" type="text/css" href="./css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="js/validation.js"></script>
</head>
<body>
<form method="post"  action="login" id="auth-form">
    <table>
        <tr>
            <td>Username:</td>
            <td><input type="text" name="username" id="username"/></td>
            <td id="username-error" class="error" hidden>Please enter username</td>
        </tr>
        <tr>
            <td>Password:</td>
            <td><input type="password" name="pass" id="pass" /></td>
            <td id="pass-error" class="error" hidden>Please enter password</td>
        </tr>
        <tr>
            <c:if test="${!empty cookie.username.value  && cookie.username.value == 'error'}" scope="session" var="q">
                <td class="error" colspan="2">
                    <c:out value="Incorrect login or password"/>
                </td>
            </c:if>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Sign in"/></td>
        </tr>
        <tr>
            <td colspan="2"><a href="registration.jsp">Sign up</a> </td>
        </tr>
    </table>
</form>

</body>
</html>
