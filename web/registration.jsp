<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link rel="icon" href="images/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/reg_page.css">
    <link rel="stylesheet" type="text/css" href="css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="js/validation.js"></script>
    <script src="js/ajax.js"></script>
</head>
<body>
<jsp:useBean id="empDAO" class="DAO.EmpDAO"/>
<c:set value="${empDAO.depts}" var="depts"/>
<c:set var="error" value="${sessionScope.error}"/>
<form method="post" action="registration" id="register-form">
    <table>
        <tr>
            <th>Department: </th>
            <td>
                <select name="dept" id="dept">
                    <option id="select-dept">Select dept</option>
                    <c:forEach items="${depts}" var="dept">
                        <option value="${dept}">${dept}</option>
                    </c:forEach>
                </select>
            </td>
            <td id="dept-error" class="error" hidden>Please select department</td>
        </tr>
        <tr>
            <th>Job: </th>
            <td>
                <select name="job" id="job">
                    <option id="select-job" selected>Select job</option>
                </select>
            </td>
            <td id="job-error" class="error" hidden>Please select job</td>
        </tr>
        <tr>
            <th>First Name:</th>
            <td><input type="text" name="fname" id="fname"/></td>
            <td id="fname-error" class="error" hidden>Please enter correct first name</td>
            <c:choose>
                <c:when test="${empty error}">
                    <td id="name-error" class="error" hidden rowspan="2">No such employee</td>
                </c:when>
                <c:when test="${error =='nameerror'}">
                    <td id="name-error" class="error" rowspan="2">No such employee</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <th>Last Name:</th>
            <td><input type="text" name="lname" id="lname"/></td>
            <td id="lname-error" class="error" hidden>Please enter correct last name</td>
        </tr>
        <tr>
            <th>Username:</th>
            <td><input type="text" name="login" id="username"/></td>
            <td id="username-error" class="error" hidden>Please enter username</td>
            <c:choose>
                <c:when test="${empty error}">
                    <td id="login-error" class="error" hidden rowspan="2">Username already exists</td>
                </c:when>
                <c:when test="${error =='loginerror'}">
                    <td id="login-error" class="error" rowspan="2">Username already exists</td>
                </c:when>
            </c:choose>
        </tr>
        <tr>
            <th>Password:</th>
            <td><input type="password" name="pass" id="pass" /></td>
            <td id="pass-error" class="error" hidden>Please enter password</td>
        </tr>
        <tr id="last">
            <td  colspan="2" rowspan="2" align="right" id="submit" ><input type="submit" value="OK"/></td>
        </tr>
    </table>
</form>

</body>
</html>
