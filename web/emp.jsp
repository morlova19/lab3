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
    <table>

        <tr>
            <th>ID</th>
            <td><input type="text" value="${param.id}" disabled name="stid" readonly/></td>
        </tr>
        <tr>
            <th>Name</th>
            <td><input name="name" type="text" value="${e.name}" readonly/></td>
        </tr>
        <tr>
            <th>Job</th>
            <td><textarea name="desc" rows="7" readonly>${e.job}</textarea></td>
        </tr>
        <tr>
            <th>Date</th>
            <td><input name="date" type="text" class="date-cell"  value="${formattedDate}" readonly/></td>
        </tr>
        <tr>
            <th>Contacts</th>
            <td><textarea name="desc" rows="7" readonly>${task.contacts}</textarea></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="button" value="OK"/>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
