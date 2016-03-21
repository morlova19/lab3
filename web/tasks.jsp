<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Tasks</title>
    <link rel="icon" href="images/icon.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <link rel="stylesheet" type="text/css" href="css/tasks_page.css">
    <link rel="stylesheet" type="text/css" href="css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="jquery/inputmask.js"></script>
    <script src="jquery/jquery.inputmask.js"></script>
    <script src="jquery/inputmask.date.extensions.js"></script>
    <script src="jquery/jquery.tablesorter.js"></script>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <script src="js/validation.js"></script>
    <script src="js/1.js"></script>

</head>
<body>
<c:set var="username" value="${sessionScope.username}"/>
<jsp:useBean id="constants" class="utils.Constants"/>
<c:if test="${!empty username}">
    <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <c:set var="type" value="${param.type}"/>
    <form id="search-form">
        <div id="search-block">
            <input type="text"  id='search-input' name="search" placeholder="Search tasks...">
            <input type="submit" value="Search"/>
            <label>${emp.name} |</label>
            <a href="logout">Log out</a>
        </div>
    </form>
    <ul id="list">
        <li class="header-link"><a href="newtask.jsp" class="menu">+ New task</a> </li>
        <li class="header-link">
            <c:choose>
                <c:when test="${type=='my'}"> <a href="?type=my" class="selected">MY TASKS</a> </c:when>
                <c:when test="${type!='my'}"> <a href="?type=my" class="menu" >MY TASKS</a></c:when>
            </c:choose>
        </li>
        <li class="header-link">
            <c:choose>
                <c:when test="${type =='emp'}"> <a href="?type=emp" class="selected" >TASKS OF EMPLOYEES</a></c:when>
                <c:when test="${type!='emp'}"><a href="?type=emp" class="menu" >TASKS OF EMPLOYEES</a></c:when>
            </c:choose>
        </li>
        <li class="header-link">
            <c:choose>
                <c:when test="${type =='org'}"> <a href="?type=org" class="selected" >HIERARCHY OF EMPLOYEES</a></c:when>
                <c:when test="${type!='org'}"><a href="?type=org" class="menu" >HIERARCHY OF EMPLOYEES</a></c:when>
            </c:choose>
        </li>
    </ul>

    <div id="menu-container">
        <c:set var="search" value="${param.search}"/>

            <div id="table-container">
                <c:choose>
                    <c:when test="${!empty search.trim()}">
                        <c:set var="tasks" value="${emp.journalManager.searchTasks(search)}" scope="page"/>
                        <c:choose>
                            <c:when test="${empty tasks}">
                                Unfortunately, your search "${search}" did not match any tasks. Please try again.
                            </c:when>
                            <c:when test="${!empty tasks}">
                                <table id="tasks" class="tablesorter">
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Date</th>
                                        <th>Status</th>
                                        <th>Employee</th>
                                        <th class="noSort">Action</th>
                                    </tr>
                                    </thead>
                                   <tbody>
                                   <c:forEach var="t" items="${tasks}">
                                       <c:set var="b" value="${username == t.cr_id}" scope="page"/>
                                       <tr>
                                           <td>${t.ID}</td>
                                           <td>
                                               <a href="task.jsp?taskid=${t.ID}" target="_blank">${t.name}</a>
                                           </td>
                                           <td><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${t.date}"/></td>
                                           <td>${t.fullStatus}</td>
                                           <c:if test="${username==t.ex_id}">
                                               <td>Me</td>
                                           </c:if>
                                           <c:if test="${username != t.ex_id}">
                                               <td>
                                                   <a href="emp.jsp?id=${t.ex_id}">${emp.getEmp(t.ex_id).name}</a>
                                               </td>
                                           </c:if>
                                           <c:if test="${b==true}">
                                               <td class="last-cell">
                                                   <form action="deletetask"  class="delete-form" method="post">
                                                       <button  class="delete-button" type="submit" name="id" value="${t.ID}"></button>
                                                   </form>
                                                   <form action="copytask" method="post">
                                                       <button  class="copy-button" type="submit" name="id" value="${t.ID}"></button>
                                                   </form>
                                               </td>

                                           </c:if>
                                           <c:if test="${b==false}">
                                               <td> <form action="copytask" method="post">
                                                   <button  class="copy-button" type="submit" name="id" value="${t.ID}"></button>
                                               </form></td>
                                           </c:if>

                                       </tr>
                                   </c:forEach>
                                   </tbody>

                                </table>
                            </c:when>
                        </c:choose>
                    </c:when>
                    <c:when test="${type=='my'}">
                        <c:set var="tasks" value="${emp.journalManager.tasks}"/>
                        <c:set var="b" value="${username == t.cr_id}" scope="page"/>
                        <c:choose>
                            <c:when test="${empty tasks}">
                                You don't have tasks.
                            </c:when>
                            <c:when test="${!empty tasks}">
                                <table id="tasks" class="tablesorter" >
                                    <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Date</th>
                                        <th>Status</th>
                                        <th class="noSort">Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="t" items="${tasks}">
                                        <c:set var="b" value="${username == t.ex_id}" scope="page"/>
                                        <tr>
                                            <td>${t.ID}</td>
                                            <td class="cell-date">
                                                <a href="task.jsp?taskid=${t.ID}" target="_blank">${t.name}</a>
                                            </td>
                                            <td class="cell-date"><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${t.date}"/></td>
                                            <td>${t.fullStatus}</td>


                                            <c:if test="${b==true}">

                                                <td class="last-cell">
                                                    <form action="deletetask"  class="delete-form" method="post">
                                                        <button  class="delete-button" type="submit" name="id" value="${t.ID}"></button>
                                                    </form>
                                                    <form action="copytask" method="post">
                                                        <button  class="copy-button" type="submit" name="id" value="${t.ID}"></button>
                                                    </form>
                                                </td>

                                            </c:if>
                                            <c:if test="${b==false}">
                                                <td class="last-cell">
                                                    <form action="copytask" method="post">
                                                        <button  class="copy-button" type="submit" name="id" value="${t.ID}"></button>
                                                    </form>
                                                </td>
                                            </c:if>

                                        </tr>
                                    </c:forEach>
                                    </tbody>

                                </table>
                            </c:when>
                        </c:choose>
                    </c:when>
                    <c:when test="${type=='emp'}">
                        <c:choose>
                            <c:when test="${empty emp.emps}">
                                You don't have available employees.
                            </c:when>
                            <c:when test="${!empty emp.emps}">
                                <c:set var="tasks" value="${emp.journalManager.empsTasks}"/>
                                <c:choose>
                                    <c:when test="${empty tasks}"> Your employees don't have tasks.</c:when>
                                    <c:when test="${!empty tasks}">
                                        <table id="tasks" class="tablesorter">
                                            <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>Date</th>
                                                <th>Status</th>
                                                <th>Employee</th>
                                                <th class="noSort">Action</th>
                                            </tr>
                                            </thead>
                                           <tbody>
                                           <c:forEach var="t" items="${tasks}">
                                               <c:set var="b" value="${username == t.cr_id}" scope="page"/>

                                               <tr>
                                                   <td>${t.ID}</td>
                                                   <td>
                                                       <a href="task.jsp?taskid=${t.ID}" target="_blank">${t.name}</a>
                                                   </td>
                                                   <td><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${t.date}"/></td>
                                                   <td>${t.fullStatus}</td>
                                                   <c:if test="${username != t.ex_id}">
                                                       <td>
                                                           <a href="emp.jsp?id=${t.ex_id}">${emp.getEmp(t.ex_id).name}</a>
                                                       </td>
                                                   </c:if>
                                                   <c:if test="${b==true}">

                                                       <td class="last-cell">
                                                           <form action="deletetask"  class="delete-form" method="post">
                                                               <button  class="delete-button" type="submit" name="id" value="${t.ID}"></button>
                                                           </form>
                                                           <form action="copytask" method="post">
                                                               <button  class="copy-button" type="submit" name="id" value="${t.ID}"></button>
                                                           </form>
                                                       </td>

                                                   </c:if>
                                                   <c:if test="${b==false}">
                                                       <td class="last-cell">
                                                           <form action="copytask" method="post">
                                                               <button  class="copy-button" type="submit" name="id" value="${t.ID}"></button>
                                                           </form>
                                                       </td>
                                                   </c:if>

                                               </tr>
                                           </c:forEach>
                                           </tbody>

                                        </table>
                                    </c:when>
                                </c:choose>
                            </c:when>
                        </c:choose>
                    </c:when>
                </c:choose>
            </div>
    </div>

</c:if>
<c:if test="${empty username}">
    Please <a href="start.jsp">Sign in</a>
    or <a href="registration.jsp">Sign up</a>
</c:if>
<script src="js/reg_service_worker.js"></script>
</body>
</html>
