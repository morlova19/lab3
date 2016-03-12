<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task of employee</title>
    <link rel="icon" href="../images/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="../css/task_page.css">
    <link rel="stylesheet" type="text/css" href="../css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="../js/1.js"></script>
</head>
<body>
<jsp:useBean id="emp" class="emp.Employee" scope="session"/>
<c:set var="taskid" value="${param.taskid}"/>
<c:set var="empid" value="${param.empid}"/>
<div class="task-form">
    <c:set var="emp1" value="${emp.getEmp(empid)}"/>
    <c:set var="task" value="${emp1.journalManager.get(taskid)}"/>
    <fmt:formatDate value="${task.date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>

    <table>
        <tr>
            <th>Employee</th>
            <td><input type="text" value="${emp1.name}" readonly name="taskid"/></td>
        </tr>
        <tr>
            <th>ID</th>
            <td><input type="text" value="${taskid}" readonly name="taskid"/></td>
        </tr>
        <tr>
            <th>Name</th>
            <td><input name="name" type="text" value="${task.name}" readonly/></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><textarea name="desc" rows="7" readonly>${task.description}</textarea></td>
        </tr>
        <tr>
            <th>Date</th>
            <td><input name="date" type="text" class="date-cell" placeholder="dd.mm.yyyy hh:mm" value="${formattedDate}" readonly/></td>
        </tr>
        <tr>
            <th>Contacts</th>
            <td><textarea name="desc" rows="7" readonly>${task.contacts}</textarea></td>
        </tr>
    </table>
</div>
<div id="menu-container">
        <div class="actions">
            <ul id="sort-delete-list">
                <li>Sort by
                    <select id="sort">
                        <option id="select-param">Select parameter</option>
                        <option>Name</option>
                        <option>Date</option>
                        <option>ID</option>
                    </select></li>
            </ul>
            <br/>
            <br/>
            <label id="message" hidden>Employee doesn't have subtasks.</label>
        </div>
        <div class="table-container">
            <c:set var="type" value="${param.type}"/>
            <table id="tasks">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Date</th>
                </tr>
                <c:forEach var="task" items="${emp1.journalManager.getSubtasks(taskid)}">
                    <tr>
                        <td>${task.ID}</td>
                        <td><a href="subtask.jsp?empid=${empid}&taskid=${taskid}&stid=${task.ID}">${task.name}</a> </td>
                        <td><fmt:formatDate type="both" dateStyle="medium" timeStyle="short" value="${task.date}"/></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
</div>
</body>
</html>
