<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="js/1.js"></script>
    <link rel="stylesheet" type="text/css" href="css/1.css">
</head>
<body>
<jsp:useBean id="emp" class="emp.Employee" scope="session"/>
<c:set var="name" value="${param.emp}"/>
<form action="updateexec" method="post" id="update-exec-form" style="margin: 0">
    <input type="text" value="${param.taskid}" name="id" hidden/>
    <select name="ex_id" id="ex_id">
        <option selected>Select executor</option>
        <option value='${emp.ID}'>Me</option>
        <c:forEach items="${emp.emps_map.values()}" var="e">
            <option value='${e.ID}'>${e.name}</option>
        </c:forEach>
    </select>
</form>
</body>
</html>
