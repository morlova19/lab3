<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
    <link rel="stylesheet" type="text/css" href="css/emp.css">
    <link rel="icon" href="images/icon.png" type="image/png">
</head>
<body>
<div class="task-form">
    <a href="task.jsp?taskid="${param.pt_id}>< Back</a>
    <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <c:set var="e" value="${emp.getEmp(param.id)}"/>
        <label for="ename">Name</label>
        <input id="ename" name="ename" type="text" value="${e.name}" readonly/>

        <label for="job">Job</label>
        <input id="job"   type="text" value="${e.job}" readonly/>

    <label for="mgr">Manager</label>
    <input id="mgr"  type="text" value="${e.mgr}" readonly/>
</div>
<c:if test="${emp.contains(e.ID)==true}">
    <table id="tasks-statistics">
        <c:set var="total" value="${e.total_count(e.ID)}"/>
        <tr>
            <th>Tasks</th>
            <th>Count</th>
            <th>Percent</th>
        </tr>
        <c:choose>
            <c:when test="${total==0}">
                <tr>
                    <td>Current</td>
                    <td>0</td>
                    <fmt:formatNumber value="0" var="formatted_count" type="percent" maxFractionDigits="2"/>
                    <td>${formatted_count}</td>
                </tr>
                <tr>
                    <td>Completed</td>
                    <td>0</td>
                    <fmt:formatNumber maxFractionDigits="2" value="0" var="formatted_count" type="percent"/>
                    <td>${formatted_count}</td>
                </tr>
                <tr>
                    <td>Failed</td>
                    <td>0</td>
                    <fmt:formatNumber value="0" var="formatted_count" maxFractionDigits="2" type="percent"/>
                    <td> ${formatted_count}</td>
                </tr>
                <tr>
                    <td>Cancelled</td>
                    <td>0</td>
                    <fmt:formatNumber value="0" var="formatted_count" maxFractionDigits="2" type="percent"/>
                    <td>${formatted_count}</td>
                </tr>
                <tr>
                    <td>Total</td>
                    <td>${total}</td>
                    <fmt:formatNumber maxFractionDigits="2" value="0" var="formatted_count" type="percent"/>
                    <td> ${formatted_count}</td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr>
                    <c:set var="cur" value="${e.cur_count(e.ID)}"/>
                    <c:set var="p" value="${cur/total}"/>
                    <td>Current</td>
                    <td>${cur}</td>
                    <fmt:formatNumber value="${p}" var="formatted_count" type="percent" maxFractionDigits="2"/>
                    <td>${formatted_count}</td>
                </tr>
                <tr>
                    <c:set var="comp" value="${e.comp_count(e.ID)}"/>
                    <c:set var="p" value="${comp/total}"/>
                    <td>Completed</td>
                    <td>${comp}</td>
                    <fmt:formatNumber maxFractionDigits="2" value="${p}" var="formatted_count" type="percent"/>
                    <td>${formatted_count}</td>
                </tr>
                <tr>
                    <c:set var="failed" value="${e.failed_count(e.ID)}"/>
                    <c:set var="p" value="${failed/total}"/>
                    <td>Failed</td>
                    <td>${failed}</td>
                    <fmt:formatNumber value="${p}" var="formatted_count" maxFractionDigits="2" type="percent"/>
                    <td> ${formatted_count}</td>
                </tr>
                <tr>
                    <c:set var="cancelled" value="${e.cancelled_count(e.ID)}"/>
                    <c:set var="p" value="${cancelled/total}"/>
                    <td>Cancelled</td>
                    <td>${cancelled}</td>
                    <fmt:formatNumber value="${p}" var="formatted_count" maxFractionDigits="2" type="percent"/>
                    <td> ${formatted_count}</td>
                </tr>
                <tr>
                    <td>Total</td>
                    <td>${total}</td>
                    <fmt:formatNumber maxFractionDigits="2" value="1" var="formatted_count" type="percent"/>
                    <td> ${formatted_count}</td>
                </tr>
            </c:otherwise>
        </c:choose>


    </table>
</c:if>

</body>
</html>
