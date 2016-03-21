<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
</head>
<body>
<div class="task-form">
    <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <c:set var="e" value="${emp.getEmp(param.id)}"/>


        <label for="ename">Name</label>
        <input id="ename" name="ename" type="text" value="${e.name}" readonly/>

        <label for="job">Job</label>
        <input id="job" name="job" type="text" value="${e.job}" readonly/>

        <th>Date</th>
        <td><input name="date" type="text" class="date-cell"  value="${formattedDate}" readonly/></td>


        <label>Contacts</label>
        <td><textarea name="desc" rows="7" readonly>${task.contacts}</textarea></td>


        <td colspan="2">
            <input type="button" value="OK"/>
        </td>

</div>
</body>
</html>
