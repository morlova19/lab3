<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>New subtask</title>
    <link rel="icon" href="../images/icon.png" type="image/png">

    <link rel="stylesheet" type="text/css" href="../css/newtask_page.css">
    <link rel="stylesheet" type="text/css" href="../css/1.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="../jquery/inputmask.js"></script>
    <script src="../jquery/jquery.inputmask.js"></script>
    <script src="../jquery/inputmask.date.extensions.js"></script>

    <script src="../js/validation.js"></script>
    <script src="../js/1.js"></script>
</head>
<body>
<jsp:useBean id="emp" class="emp.Employee" scope="session"/>
<form method="post" action="newsubtask" id="newsubtask-form">

    <c:set var="taskdate" value="${emp.journalManager.get(param.taskid).date}"/>
    <fmt:formatDate value="${taskdate}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>
    <input name="taskid" value="${param.taskid}" hidden/>
    <table>
        <tr>
            <th>Task date</th>
            <td><input type="text" class="date-cell" id="task-date" value="${formattedDate}" readonly/></td>
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
            <td><input name="date" type="text" id=date class="date-cell" value="${formattedDate}"/></td>
            <td id="date-error" class="error" hidden>Please enter correct date</td>
        </tr>
        <tr>
            <th>Contacts</th>
            <td><textarea name="contacts" rows="7"></textarea></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="OK"/></td>
        </tr>
    </table>
</form>
</body>
</html>
