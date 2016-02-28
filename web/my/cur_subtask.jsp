<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Subtask</title>
    <link rel="stylesheet" type="text/css" href="../css/task_page.css">
    <link rel="stylesheet" type="text/css" href="../css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="../js/1.js"></script>
    <script src="../js/validation.js"></script>
</head>
<body>
<c:set var="taskid" value="${param.taskid}"/>
<c:set var="st_id" value="${param.stid}"/>
<div class="task-form">

    <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <c:set var="task" value="${emp.journalManager.getSubtask(taskid,st_id)}"/>
    <fmt:formatDate value="${task.date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>
    <form method="post" action="savesubtask">
        <table>
            <tr>
                <th>Task ID</th>
                <td><input type="text" value="${taskid}" readonly name="taskid"/></td>
            </tr>
            <tr>
                <th>ID</th>
                <td><input type="text" value="${st_id}" readonly name="stid"/></td>
            </tr>
            <tr>
                <th>Name</th>
                <td><input name="name" type="text" value="${task.name}"/></td>
            </tr>
            <tr>
                <th>Description</th>
                <td><textarea name="desc" rows="7">${task.description}</textarea></td>
            </tr>
            <tr>
                <th>Date</th>
                <td><input name="date" type="text" class="date-cell" placeholder="dd.mm.yyyy hh:mm" value="${formattedDate}"/></td>
            </tr>
            <tr>
                <th>Contacts</th>
                <td><textarea name="desc" rows="7">${task.contacts}</textarea></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="OK" name="save"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
