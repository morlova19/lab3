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
            <a href="#">
                <img src="images/ic_action_help.png" id="help" alt="Help" >
            </a>
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
        <li class="header-link">
            <c:choose>
                <c:when test="${type =='stat'}"> <a href="?type=stat" class="selected" >STATISTICS</a></c:when>
                <c:when test="${type!='stat'}"><a href="?type=stat" class="menu" >STATISTICS</a></c:when>
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
                                <div id="select-div">
                                    <label>Status</label>
                                    <select>
                                        <option selected>All</option>
                                        <option>${constants.fullNew}</option>
                                        <option>${constants.fullPerforming}</option>
                                        <option>${constants.fullCancelled}</option>
                                        <option>${constants.fullCompleted}</option>
                                    </select>
                                </div>
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
                                               <td><a href="emp.jsp?id=${t.ex_id}">Me</a></td>
                                           </c:if>
                                           <c:if test="${username != t.ex_id}">
                                               <td><a href="emp.jsp?id=${t.ex_id}">${emp.getEmp(t.ex_id).name}</a></td>
                                           </c:if>
                                           <c:if test="${b==true}">
                                               <td class="last-cell">
                                                   <form action="deletetask"  class="delete-form" method="post">
                                                       <button  class="delete-button" type="submit" name="id" value="${t.ID}"></button>
                                                   </form>
                                                   <form action="copytask" method="post">
                                                       <button  class="copy-button" type="submit" name="id" value="${t.ID}"></button>
                                                   </form>
                                                   <button  class="edit-exec" type="button" name="id" value="${t.ID}"></button>
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
                                <div id="select-div">
                                    <label for="status">Status</label>
                                    <select id="status">
                                        <option selected>All</option>
                                        <option>${constants.fullNew}</option>
                                        <option>${constants.fullPerforming}</option>
                                        <option>${constants.fullCancelled}</option>
                                        <option>${constants.fullCompleted}</option>
                                    </select>
                                </div>
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
                                        <c:set var="b" value="${username == t.cr_id}" scope="page"/>
                                        <tr>
                                            <td>${t.ID}</td>
                                            <td>
                                                <a href="task.jsp?taskid=${t.ID}" target="_blank">${t.name}</a>
                                            </td>
                                            <td class="cell-date"><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${t.date}"/></td>
                                            <td>${t.fullStatus}</td>
                                            <c:if test="${b==true}">
                                                <td class="last-cell">
                                                    <button class="edit-exec" type="button" name="id" value="${t.ID}"></button>
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
                            <c:when test="${empty emp.emps_map}">
                                You don't have available employees.
                            </c:when>
                            <c:when test="${!empty emp.emps_map}">

                                <c:set var="tasks" value="${emp.journalManager.empsTasks}"/>
                                <c:choose>
                                    <c:when test="${empty tasks}"> Your employees don't have tasks.</c:when>
                                    <c:when test="${!empty tasks}">
                                        <div id="select-div">
                                            <label>Status</label>
                                            <select>
                                                <option selected>All</option>
                                                <option>${constants.fullNew}</option>
                                                <option>${constants.fullPerforming}</option>
                                                <option>${constants.fullCancelled}</option>
                                                <option>${constants.fullCompleted}</option>
                                            </select>
                                        </div>
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
                                                   <td><a href="task.jsp?taskid=${t.ID}" target="_blank">${t.name}</a></td>
                                                   <td class="cell-date"><fmt:formatDate type="both" pattern="dd.MM.yyyy HH:mm" value="${t.date}"/></td>
                                                   <td>${t.fullStatus}</td>
                                                   <c:if test="${username != t.ex_id}"><td><a href="emp.jsp?id=${t.ex_id}">${emp.getEmp(t.ex_id).name}</a></td></c:if>
                                                   <c:if test="${b==true}">
                                                       <td class="last-cell">
                                                           <button  class="edit-exec" type="button" name="id" value="${t.ID}"></button>
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
                    <c:when test="${type=='org'}">
                        <c:set var="boss" value="${emp.boss}"/>
                        <c:if test="${!empty boss}">
                            <c:set var="node" value="${emp.boss}" scope="request"/>
                            <ul class="tree">
                                <c:if test="${emp.ID == boss.ID}">
                                    <li class="current"> ${boss.name} <jsp:include page="node.jsp"/></li>
                                </c:if>
                                <c:if test="${emp.ID != boss.ID}">
                                    <li> <input type="button" value="-" class="hide-child"> <a href="emp.jsp?id=${node.ID}">${boss.name}</a>  <jsp:include page="node.jsp"/></li>
                                </c:if>
                            </ul>
                        </c:if>
                    </c:when>
                    <c:when test="${type=='stat'}">
                        <c:set var="id" value="${emp.ID}"/>
                        <c:set var="total" value="${emp.total_count(id)}"/>
                        <c:set var="cur" value="${emp.cur_count(id)}"/>
                        <c:set var="comp" value="${emp.comp_count(id)}"/>
                        <c:set var="cancelled" value="${emp.cancelled_count(id)}"/>
                        <c:set var="failed" value="${emp.failed_count(id)}"/>
                            <table id="tasks">
                                <tr>
                                    <th>Employee</th>
                                    <th>Current</th>
                                    <th>Completed</th>
                                    <th>Failed</th>
                                    <th>Cancelled</th>
                                    <th>Total</th>
                                </tr>
                                <tr>
                                    <c:set var="emp_total" value="${emp.total_count(id)}"/>
                                    <c:set var="emp_cur" value="${emp.cur_count(id)}"/>
                                    <c:set var="emp_comp" value="${emp.comp_count(id)}"/>
                                    <c:set var="emp_cancelled" value="${emp.cancelled_count(id)}"/>
                                    <c:set var="emp_failed" value="${emp.failed_count(id)}"/>
                                   <c:choose>
                                       <c:when test="${emp_total==0}">
                                           <fmt:formatNumber value="0" type="percent" maxFractionDigits="2" var="t"/>
                                           <td><a href="emp.jsp?id=${emp.ID}">Me</a></td>
                                           <td>${emp_cur} (${t})</td>
                                           <td>${emp_comp} (${t})</td>
                                           <td>${emp_failed} (${t})</td>
                                           <td>${emp_cancelled} (${t})</td>
                                           <td>${emp_total} (${t})</td>
                                       </c:when>
                                       <c:otherwise>
                                           <fmt:formatNumber value="${emp_cur/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                           <td><a href="emp.jsp?id=${emp.ID}">Me</a></td>
                                           <td>${emp_cur} (${t})</td>
                                           <fmt:formatNumber value="${emp_comp/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                           <td>${emp_comp} (${t})</td>
                                           <fmt:formatNumber value="${emp_failed/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                           <td>${emp_failed} (${t})</td>
                                           <fmt:formatNumber value="${emp_cancelled/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                           <td>${emp_cancelled} (${t})</td>
                                           <fmt:formatNumber value="1" type="percent" maxFractionDigits="2" var="t"/>
                                           <td>${emp_total} (${t})</td>
                                       </c:otherwise>
                                   </c:choose>

                                </tr>
                                <c:forEach var="e" items="${emp.emps_map.values()}">
                                    <c:set var="eid" value="${e.ID}"/>
                                    <c:set var="emp_total" value="${e.total_count(eid)}"/>

                                    <c:choose>
                                        <c:when test="${emp_total==0}">
                                            <tr>
                                                <td><a href="emp.jsp?id=${e.ID}">${e.name}</a></td>
                                                <fmt:formatNumber value="0" type="percent" maxFractionDigits="2" var="t"/>
                                                <td>0 (${t})</td>
                                                <td>0 (${t})</td>
                                                <td>0 (${t})</td>
                                                <td>0 (${t})</td>

                                                <td>0 (${t})</td>
                                            </tr>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="emp_cur" value="${e.cur_count(eid)}"/>
                                            <c:set var="emp_comp" value="${e.comp_count(eid)}"/>
                                            <c:set var="emp_cancelled" value="${e.cancelled_count(eid)}"/>
                                            <c:set var="emp_failed" value="${e.failed_count(eid)}"/>
                                            <c:set var="total" value="${total+emp_total}"/>
                                            <c:set var="cur" value="${cur+emp_cur}"/>
                                            <c:set var="comp" value="${comp+emp_comp}"/>
                                            <c:set var="cancelled" value="${cancelled+emp_cancelled}"/>
                                            <c:set var="failed" value="${failed+emp_failed}"/>
                                            <tr>
                                                <td><a href="emp.jsp?id=${e.ID}">${e.name}</a></td>
                                                <fmt:formatNumber value="${emp_cur/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                                <td>${emp_cur} (${t})</td>
                                                <fmt:formatNumber value="${emp_comp/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                                <td>${emp_comp} (${t})</td>
                                                <fmt:formatNumber value="${emp_failed/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                                <td>${emp_failed} (${t})</td>
                                                <fmt:formatNumber value="${emp_cancelled/emp_total}" type="percent" maxFractionDigits="2" var="t"/>
                                                <td>${emp_cancelled} (${t})</td>
                                                <fmt:formatNumber value="1" type="percent" maxFractionDigits="2" var="t"/>
                                                <td>${emp_total} (${t})</td>
                                            </tr>
                                        </c:otherwise>
                                    </c:choose>

                                </c:forEach>
                                <tr>
                                    <c:choose>
                                        <c:when test="${total==0}">
                                            <td>Total</td>
                                            <fmt:formatNumber value="0" type="percent" maxFractionDigits="2" var="t"/>
                                            <td>0 (${t})</td>
                                            <td>0 (${t})</td>
                                            <td>0 (${t})</td>
                                            <td>0 (${t})</td>
                                            <td>${total} (${t})</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>Total</td>
                                            <fmt:formatNumber value="${cur/total}" type="percent" maxFractionDigits="2" var="t"/>
                                            <td>${cur} (${t})</td>
                                            <fmt:formatNumber value="${comp/total}" type="percent" maxFractionDigits="2" var="t"/>
                                            <td>${comp} (${t})</td>
                                            <fmt:formatNumber value="${failed/total}" type="percent" maxFractionDigits="2" var="t"/>
                                            <td>${failed} (${t})</td>
                                            <fmt:formatNumber value="${cancelled/total}" type="percent" maxFractionDigits="2" var="t"/>
                                            <td>${cancelled} (${t})</td>
                                            <fmt:formatNumber value="1" type="percent" maxFractionDigits="2" var="t"/>
                                            <td>${total} (${t})</td>
                                        </c:otherwise>
                                    </c:choose>
                                </tr>
                            </table>
                    </c:when>
                </c:choose>
            </div>
    </div>
<div id="test" hidden>
    <input type="text" value="cccc"/>
</div>
</c:if>
<c:if test="${empty username}">
    Please <a href="start.jsp">Sign in</a>
    or <a href="registration.jsp">Sign up</a>
</c:if>
<script src="js/reg_service_worker.js"></script>
</body>
</html>
