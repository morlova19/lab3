<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Task</title>
    <link rel="icon" href="../images/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="../css/task_page.css">
    <link rel="stylesheet" type="text/css" href="../css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="../js/1.js"></script>
</head>
<body>
<jsp:useBean id="emp" class="emp.Employee" scope="session"/>
<c:set var="taskid" value="${param.taskid}"/>
<div class="task-form">
    <c:set var="task" value="${emp.journalManager.get(taskid)}"/>
    <fmt:formatDate value="${task.date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>

        <table>
            <tr>
                <th>ID</th>
                <td><input type="text" value="${taskid}" readonly/></td>
            </tr>
            <tr>
                <th>Name</th>
                <td><input  type="text" value="${task.name}" readonly/></td>
            </tr>
            <tr>
                <th>Description</th>
                <td><textarea  rows="7" readonly>${task.description}</textarea></td>
            </tr>
            <tr>
                <th>Date</th>
                <td><input  type="text" class="date-cell" placeholder="dd.mm.yyyy hh:mm" value="${formattedDate}" readonly/></td>
            </tr>
            <tr>
                <th>Contacts</th>
                <td><textarea  rows="7" readonly>${task.contacts}</textarea></td>
            </tr>
        </table>
</div>
<div id="menu-container">
    <form action="deletesubtask" method="post">
        <div class="actions">
            <li><input type="submit"  value="DELETE" id="delete-submit"></li>
            <label>
                Sort by
                <select id="sort">
                    <option>Select parameter</option>
                    <option>Name</option>
                    <option>Date</option>
                    <option>ID</option>
                </select>
            </label>
        </div>

        <div class="table-container">
            <c:set var="type" value="${param.type}"/>
            <table id="tasks">
                <tr>
                    <th><input type="checkbox" class="select-all"/></th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Date</th>
                </tr>
                <c:forEach var="task" items="${emp.journalManager.getSubtasks(taskid)}">
                    <tr>
                        <td><input type="checkbox" value="${task.ID}" name="id"/></td>
                        <td>${task.ID}</td>
                        <td><a href="comp_subtask.jsp?taskid=${taskid}&stid=${task.ID}">${task.name}</a> </td>
                        <td>
                            <fmt:formatDate type="both" dateStyle="medium" timeStyle="short" value="${task.date}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    </form>
</div>
<div>

</div>
</body>
</html>
