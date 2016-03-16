<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tasks</title>
    <link rel="icon" href="../images/icon.png" type="image/png">
    <link rel="manifest" href="../manifest.json">
    <link rel="stylesheet" type="text/css" href="../css/tasks_page.css">
    <link rel="stylesheet" type="text/css" href="../css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="../js/validation.js"></script>
    <script src="../js/1.js"></script>

</head>
<body>
<c:set var="username" value="${sessionScope.username}"/>
<c:if test="${!empty username}">
        <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <c:set var="type" value="${param.type}"/>
    <form id="search-form">
        <div id="search-block">
            <input type="text"  id='search-input' name="search" placeholder="Search my tasks...">
            <input type="submit" value="Search" />
            <label>${emp.name} |</label>
            <a href="logout">Log out</a>
        </div>
    </form>
        <ul id="list">
            <li class="header">MY TASKS</li>
            <li class="header-link"><a href="newtask.jsp" class="menu">+ New task</a> </li>
            <li class="header-link">
                <c:choose>
                    <c:when test="${type=='comp'}"> <a href="?type=comp" class="selected">Completed</a> </c:when>
                    <c:when test="${type!='comp'}"> <a href="?type=comp" class="menu" >Completed</a></c:when>
                </c:choose>
            </li>
            <li class="header-link">
                <c:choose>
                    <c:when test="${type =='cur'}"> <a href="?type=cur" class="selected" >Current</a></c:when>
                    <c:when test="${type!='cur'}"><a href="?type=cur" class="menu" >Current</a></c:when>
                </c:choose>
            </li>
            <li class="header">TASKS OF EMPLOYEES</li>
            <li class="header-link"><a href="../emp/tasks.jsp?type=comp" class="menu">Completed</a></li>
            <li class="header-link"><a href="../emp/tasks.jsp?type=cur" class="menu">Current</a></li>
        </ul>
        <div id="menu-container">
            <c:set var="search" value="${param.search}"/>
            <form action="deletetask" id="delete-form" method="post">
                <div id="sort-delete">
                    <ul id="sort-delete-list">
                        <c:if test="${empty search}">
                            <li><input type="submit"  value="DELETE" id="delete-submit"></li>
                        </c:if>
                        <li>Sort by
                            <select id="sort">
                                <option id="select-param">Select parameter</option>
                                <option>Name</option>
                                <option>Date</option>
                                <option>ID</option>
                            </select></li>
                    </ul>
                </div>
                <div id="table-container">
                            <c:choose>
                                <c:when test="${!empty search.trim()}">
                                    <c:set var="tasks" value="${emp.journalManager.searchTasks(search)}"/>
                                    <c:choose>
                                        <c:when test="${empty tasks}">
                                            Unfortunately, your search "${search}" did not match any tasks. Please try again.
                                        </c:when>
                                        <c:when test="${!empty tasks}">
                                            <table id="tasks" >
                                                <tr>
                                                    <th>ID</th>
                                                    <th>Name</th>
                                                    <th>Date</th>
                                                </tr>
                                                <c:forEach var="t" items="${tasks}">
                                                    <tr>
                                                        <td>${t.ID}</td>
                                                        <td><c:if test="${t.completed == true}">
                                                            <a href="comp_task.jsp?taskid=${t.ID}" target="_blank">${t.name}</a></td>
                                                        </c:if>
                                                        <c:if test="${t.completed == false}">
                                                            <a href="cur_task.jsp?taskid=${t.ID}" target="_blank">${t.name}</a>
                                                        </c:if>
                                                        <td><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${t.date}"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                                <c:when test="${type=='comp'}">
                                    <c:set var="tasks" value="${emp.journalManager.completedTasks}"/>
                                    <c:choose>
                                        <c:when test="${empty tasks}">
                                            You don't have completed tasks.
                                        </c:when>
                                        <c:when test="${!empty tasks}">
                                            <table id="tasks" >
                                                <tr>
                                                    <th><input type="checkbox" class="select-all"/></th>
                                                    <th>ID</th>
                                                    <th>Name</th>
                                                    <th>Date</th>
                                                </tr>
                                                <c:forEach var="task" items="${tasks}">
                                                    <tr>
                                                        <td><input type="checkbox" value="${task.ID}" name="id"/></td>
                                                        <td>${task.ID}</td>
                                                        <td><a href="comp_task.jsp?taskid=${task.ID}" target="_blank">${task.name}</a></td>
                                                        <td><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${task.date}"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                                <c:when test="${type=='cur'}">
                                    <c:set var="tasks" value="${emp.journalManager.currentTasks}"/>
                                    <c:choose>
                                        <c:when test="${empty tasks}"> You don't have current tasks.</c:when>
                                        <c:when test="${!empty tasks}">
                                            <table id="tasks" >
                                             <tr>
                                                 <th><input type="checkbox" class="select-all"/></th>
                                                 <th>ID</th>
                                                 <th>Name</th>
                                                 <th>Date</th>
                                             </tr>
                                                <c:forEach var="task" items="${emp.journalManager.currentTasks}">
                                                    <tr>
                                                        <td><input type="checkbox" value="${task.ID}" name="id"/></td>
                                                        <td>${task.ID}</td>
                                                        <td><a href="cur_task.jsp?taskid=${task.ID}" target="_blank">${task.name}</a></td>
                                                        <td><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${task.date}"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </c:when>
                                    </c:choose>
                                </c:when>
                            </c:choose>
                </div>
            </form>
        </div>

</c:if>
<c:if test="${empty username}">
    Please <a href="../start.jsp">Sign in</a>
    or <a href="../registration.jsp">Sign up</a>
</c:if>
<script src="../js/reg_service_worker.js"></script>
</body>
</html>
