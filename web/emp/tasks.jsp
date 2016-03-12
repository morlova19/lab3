<%@ page import="jm.JournalManager" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <title>Tasks</title>
    <link rel="icon" href="../images/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="../css/tasks_page.css">
    <link rel="stylesheet" type="text/css" href="../css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="../js/1.js"></script>
</head>
<body>

<c:if test="${!empty sessionScope.username}">
    <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <c:set var="type" value="${param.type}"/>
    <div id="main">
        <form id="search-form" action="../my/tasks.jsp">
            <div id="search-block">
                <input type="text"  name="search" placeholder="Search my tasks...">
                <input type="submit" value="Search" />
                <label>${emp.name} |</label>
                <a href="../start.jsp">Log out</a>
            </div>
        </form>

        <div>

        </div>
        <ul id="list">
            <li class="header">MY TASKS</li>
            <li class="header-link">
                <a href="../my/newtask.jsp" class="menu" target="_blank">+ New task</a> </li>
            <li class="header-link"><a href="../my/tasks.jsp?type=comp" class="menu" >Completed</a>
            <li class="header-link"><a href="../my/tasks.jsp?type=cur" class="menu" >Current</a>
            <li class="header">TASKS OF EMPLOYEES</li>
            <li class="header-link">
                <c:choose>
                    <c:when test="${type =='comp'}"> <a href="?type=comp" class="selected">Completed</a> </c:when>
                    <c:when test="${type!='comp'}"> <a href="?type=comp" class="menu" >Completed</a></c:when>
                </c:choose>
            </li>
            <li class="header-link">
                <c:choose>
                    <c:when test="${type =='cur'}"> <a href="?type=cur" class ="selected" >Current</a></c:when>
                    <c:when test="${type!='cur'}"><a href="?type=cur" class="menu" >Current</a></c:when>
                </c:choose>
            </li>

        </ul>
        <div id="menu-container">
            <c:set var="search" value="${param.search}"/>
            <c:choose>
                <c:when test="${empty emp.emps}">
                    <label >You don't have available employees.</label>
                </c:when>
                <c:when test="${!empty emp.emps}">
                <form>

                    <ul id="sort-delete-list">
                        <li> Sorted by
                            <select id="sort">
                                <option>Select parameter</option>
                                <option>Name</option>
                                <option>Date</option>
                                <option>ID</option>
                                <option>Employee</option>
                            </select></li>
                    </ul>
                    <div id="table-container">
                        <c:choose>
                            <c:when test="${type=='cur'}">
                                <table id="tasks">
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Date</th>
                                        <th>Employee</th>
                                    </tr>
                                    <c:forEach var="emp" items="${emp.emps}">
                                        <c:forEach var="task" items="${emp.journalManager.currentTasks}">
                                            <tr>
                                                <td>${task.ID}</td>
                                                <td><a href="task.jsp?empid=${emp.ID}&taskid=${task.ID}">${task.name}</a> </td>
                                                <td><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${task.date}"/></td>
                                                <td>${emp.name}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:forEach>
                                </table>
                                <label id="message" hidden>Your employees don't have current tasks.</label>
                            </c:when>
                            <c:when test="${type=='comp'}">
                                <table id="tasks">
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Date</th>
                                        <th>Employee</th>
                                    </tr>
                                    <c:forEach var="emp" items="${emp.emps}">
                                        <c:forEach var="task" items="${emp.journalManager.completedTasks}">
                                            <tr>
                                                <td>${task.ID}</td>
                                                <td><a href="task.jsp?empid=${emp.ID}&taskid=${task.ID}">${task.name}</a> </td>
                                                <td><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${task.date}"/></td>
                                                <td>${emp.name}</td>
                                            </tr>
                                        </c:forEach>
                                    </c:forEach>
                                </table>
                                <label id="message" hidden>Your employees don't have completed tasks.</label>
                            </c:when>
                        </c:choose>
                    </div>
                </form>
        </div>
        </c:when>
        </c:choose>
    </div>
</c:if>
<c:if test="${empty sessionScope.username}">
    Please <a href="../start.jsp">Sign in</a>
    or <a href="../registration.jsp">Sign up</a>
</c:if>
</body>
</html>
