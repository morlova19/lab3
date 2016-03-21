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
<div class="task-form">
    <form method="post" action="newtask" id="newtask-form">

        <c:if test="${!empty param.pt_id}">
            <c:set var="pt" value="${emp.journalManager.get(param.pt_id)}"/>
            <fmt:formatDate value="${pt.date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>

            <label for="pt_id">Parent task</label>
            <input name="pt_id" type="text" id="pt_id" readonly value="${param.pt_id}"/>

            <label for="task-date">Parent task date</label>
            <input class="date-cell" type="text" id="task-date" readonly value="${formattedDate}"/>
        </c:if>

        <label for="executor">Executor</label>
        <select name="ex_id" id="executor">
            <option value="${emp.ID}" selected>Me</option>
            <c:forEach items="${emp.emps}" var="e">
                <option value="${e.ID}">${e.name}</option>
            </c:forEach>
        </select>

        <label for="name">Name</label>
        <input name="name" type="text" id="name"/>
        <label id="name-error" class="error" hidden >Name should not be empty</label>
        <br/>
        <label for="desc">Description</label>
        <textarea name="desc" rows="5" id="desc"></textarea>

        <label for="tdate">Date</label>
        <input name="date" type="text" class="date-cell" id="tdate">
        <label id="date-error" class="error" hidden>Please enter correct date</label>
        <br/>
        <label for="contacts">Contacts</label>
        <textarea name="contacts" rows="5" id="contacts"></textarea>

        <label for="priority">Priority</label>
        <select name="priority" id="priority">
            <option value="${constants.LOW}" selected>Low</option>
            <option value="${constants.MEDIUM}">Medium</option>
            <option value="${constants.HIGH}" selected>High</option>
        </select>

        <input type="submit" value="OK"/>
    </form>
</div>

</body>
</html>
