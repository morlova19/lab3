<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New task</title>
    <link rel="icon" href="images/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/newtask_page.css">
    <link rel="stylesheet" type="text/css" href="css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="jquery/inputmask.js"></script>
    <script src="jquery/jquery.inputmask.js"></script>
    <script src="jquery/inputmask.date.extensions.js"></script>
    <script src="js/newtask.js"></script>
    <script src="js/validation.js"></script>
</head>
<body>
<jsp:useBean id="emp" class="emp.Employee" scope="session"/>
<jsp:useBean id="constants" class="utils.Constants"/>
<form method="post" action="newtask" id="newtask-form">
    <table>
        <c:if test="${!empty param.pt_id}">
            <c:set var="pt" value="${emp.journalManager.get(param.pt_id)}"/>
            <fmt:formatDate value="${pt.date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>
            <tr>
                <th>Parent task</th>
                <td><input name="pt_id" type="text" id="pt_id" readonly value="${param.pt_id}"/></td>
            </tr>
            <tr>
                <th>Parent task date</th>
                <td><input class="date-cell" type="text" id="task-date" readonly value="${formattedDate}"/></td>
            </tr>
        </c:if>
        <tr>
            <th>Executor</th>
            <td>
                <select name="ex_id">
                    <option value="${emp.ID}" selected>Me</option>
                    <c:forEach items="${emp.emps}" var="e">
                        <option value="${e.ID}">${e.name}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>Name</th>
            <td><input name="name" type="text" id="name"/></td>
            <td id="name-error" class="error" hidden>Name should not be empty</td>
        </tr>
        <tr>
            <th>Description</th>
            <td><textarea name="desc" rows="7"></textarea></td>
        </tr>
        <tr>
            <th>Date</th>
            <td>
                <input name="date" type="text" class="date-cell" id="tdate">
            </td>
            <td id="date-error" class="error" hidden>Please enter correct date</td>
        </tr>
        <tr>
            <th>Contacts</th>
            <td><textarea name="contacts" rows="7" ></textarea></td>
        </tr>
        <tr>
            <th>Priority</th>
            <td><select name="priority">
                    <option value="${constants.LOW}" selected>Low</option>
                    <option value="${constants.MEDIUM}">Medium</option>
                    <option value="${constants.HIGH}" selected>High</option>
            </select>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="OK"/></td>
        </tr>
    </table>
</form>
</body>
</html>
