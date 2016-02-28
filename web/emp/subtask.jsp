<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task of employee</title>
    <link rel="stylesheet" type="text/css" href="../css/task_page.css">
    <link rel="stylesheet" type="text/css" href="../css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="../js/1.js"></script>
</head>
<body>
<jsp:useBean id="emp" class="emp.Employee" scope="session"/>
<c:set var="taskid" value="${param.taskid}"/>
<c:set var="empid" value="${param.empid}"/>
<c:set var="st_id" value="${param.stid}"/>
<div class="task-form">
    <c:set var="emp1" value="${emp.getEmp(empid)}"/>
    <c:set var="subtask" value="${emp1.journalManager.getSubtask(taskid,st_id)}"/>
    <fmt:formatDate value="${subtask.date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>

    <table>
        <tr>
            <th>Employee</th>
            <td><input type="text" value="${emp1.name}" readonly name="taskid"/></td>
        </tr>
        <tr>
            <th>ID</th>
            <td><input type="text" value="${st_id}" readonly name="taskid"/></td>
        </tr>
        <tr>
            <th>Name</th>
            <td><input name="name" type="text" value="${subtask.name}" readonly/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><textarea name="desc" rows="7" readonly>${subtask.description}</textarea></td>
        </tr>
        <tr>
            <th>Date</th>
            <td><input name="date" type="text" class="date-cell" placeholder="dd.mm.yyyy hh:mm" value="${formattedDate}" readonly/></td>
        </tr>
        <tr>
            <th>Contacts</th>
            <td><textarea name="desc" rows="7" readonly>${subtask.contacts}</textarea></td>
        </tr>
    </table>
</div>
</body>
</html>
