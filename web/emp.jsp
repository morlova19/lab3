<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
    <link rel="stylesheet" type="text/css" href="css/task_page.css">
</head>
<body>
<div class="task-form">
    <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <c:set var="e" value="${emp.getEmp(param.id)}"/>

        <label for="ename">Name</label>
        <input id="ename" name="ename" type="text" value="${e.name}" readonly/>

        <label for="job">Job</label>
        <input id="job"   type="text" value="${e.job}" readonly/>

    <label for="mgr">Manager</label>
    <input id="mgr"  type="text" value="${e.mgr}" readonly/>

    <label for="total">Total count</label>
    <input id="total"  type="text" value="${e.task_count}" readonly/>

    <label for="current">Current count</label>
    <input id="current"  type="text" value="${e.current_tasks}" readonly/>
    <label for="completed">Completed count</label>
    <input id="completed"  type="text" value="${e.completed_tasks}" readonly/>

</div>
</body>
</html>
