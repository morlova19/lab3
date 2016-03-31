<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Task</title>
    <link rel="icon" href="images/icon.png" type="image/png">
    <link rel="stylesheet" type="text/css" href="css/task_page.css">
    <link rel="stylesheet" type="text/css" href="css/1.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="jquery/inputmask.js"></script>
    <script src="jquery/jquery.inputmask.js"></script>
    <script src="jquery/inputmask.date.extensions.js"></script>
    <script src="jquery/jquery.tablesorter.js"></script>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <script src="js/1.js"></script>
    <script src="js/validation.js"></script>
</head>
<body>
<c:if test="${!empty sessionScope.username}">
    <jsp:useBean id="emp" class="emp.Employee" scope="session"/>
    <jsp:useBean id="constants" class="utils.Constants"/>
    <c:set var="taskid" value="${param.taskid}"/>

    <div class="task-form">
        <c:set var="task" value="${emp.journalManager.get(taskid)}"/>
        <fmt:formatDate value="${task.date}" pattern="dd.MM.yyyy HH:mm" var="formattedDate"/>
        <c:set var="isCompleted" value="${task.status==constants.COMPLETED}" scope="session"/>
        <c:set var="isEditable" value="${task.cr_id==emp.ID}" scope="page"/>
        <c:choose>
            <c:when test="${isCompleted==true}">
                <c:if test="${param.pt_id != null}">
                    <fmt:formatDate value="${emp.journalManager.get(param.pt_id).date}" pattern="dd.MM.yyyy HH:mm" var="formattedDate1"/>
                    <label for="pt_id">Parent task</label>
                    <input id="pt_id" readonly value="${task.name}"/>
                    <input type="text" readonly value="${task.pt_id}" name="pt_id" hidden/>

                    <label for="task-date">Parent task date</label>
                    <input class="date-cell" type="text" id="task-date" readonly value="${formattedDate1}"/>
                </c:if>
                <label for="exec">Executor</label>
                <c:if test="${emp.ID==task.ex_id}">

                    <input type="text" readonly value="Me" id="exec" name="ex_id"/>
                </c:if>
                <c:forEach items="${emp.emps_map.values()}" var="e">
                    <c:if test="${e.ID==task.ex_id}">

                        <input type="text" readonly value="${e.name}" id="exec" />
                    </c:if>
                </c:forEach>
                <label for="creator">Creator</label>
                <input type="text" id="creator" readonly value="${emp.getName(task.cr_id)}"/>

                <label for="taskid">ID</label>
                <input id="taskid" type="text" value="${taskid}" readonly name="taskid"/>
                <label for="name">Name</label>
                <input name="name" id="name" type="text" value="${task.name}" readonly/>

                <label for="desc">Description</label>
                <textarea name="desc" id="desc" rows="5" readonly>${task.description}</textarea>
                <label for="tdate">Date</label>
                <input readonly name="date" type="text" class="date-cell"  id="tdate" value="${formattedDate}"/>

                <label for="contacts">Contacts</label>
                <textarea name="contacts" id="contacts" rows="5" readonly>${task.contacts}</textarea>
                <label for="priority">Priority</label>
                <c:if test="${task.priority == constants.LOW}">
                    <input id="priority" type="text" value="Low" readonly/>
                </c:if>
                <c:if test="${task.priority == constants.NORMAL}">
                    <input id="priority" type="text" value="Normal" readonly/>
                </c:if>
                <c:if test="${task.priority == constants.HIGH}">
                    <input id="priority" type="text" value="High" readonly/>
                </c:if>
                <label for="status">Status</label>
                <input id="status" name="status" type="text" value="${task.fullStatus}" readonly/>
            </c:when>
            <c:otherwise>
                <form method="post" action="savetask" id="update-task-form">
                    <c:if test="${param.pt_id != null}">
                        <fmt:formatDate value="${emp.journalManager.get(param.pt_id).date}" pattern="dd.MM.yyyy HH:mm" var="formattedDate1"/>
                        <label for="pt_id">Parent task</label>
                        <input type="text" id="pt_id" readonly value="${task.name}"/>
                        <input type="text" readonly value="${task.pt_id}" name="pt_id" hidden/>
                        <label for="task-date">Parent task date</label>
                        <input class="date-cell" type="text" id="task-date" readonly value="${formattedDate1}"/>
                    </c:if>
                    <label for="exec">Executor</label>
                    <c:choose>
                        <c:when test="${isEditable==false || task.status==constants.CANCELLED}">
                            <c:if test="${emp.ID==task.ex_id}">
                                <input type="text" readonly value="Me" id="exec"/>
                                <input type="text" readonly value="${emp.ID}" id="exec" name="ex_id" hidden/>
                            </c:if>
                            <c:if test="${emp.ID!=task.ex_id}">
                                <c:forEach items="${emp.emps_map.values()}" var="e">
                                    <c:if test="${e.ID==task.ex_id}">
                                        <input type="text" readonly value="${e.name}" id="exec"/>
                                        <input type="text" readonly value="${e.ID}" id="exec" name="ex_id" hidden/>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <select name="ex_id" id="exec">
                                <c:if test="${emp.ID==task.ex_id}">
                                    <option value="${emp.ID}" selected>Me</option>
                                </c:if>
                                <c:if test="${emp.ID !=task.ex_id}">
                                    <option value="${emp.ID}" >Me</option>
                                </c:if>
                                <c:forEach items="${emp.emps_map.values()}" var="e">
                                    <c:if test="${e.ID==task.ex_id}">
                                        <option value="${e.ID}" selected>${e.name}</option>
                                    </c:if>
                                    <c:if test="${e.ID !=task.ex_id}">
                                        <option value="${e.ID}">${e.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </c:otherwise>
                    </c:choose>

                    <label for="creator">Creator</label>
                    <input type="text" id="creator" readonly value="${emp.getName(task.cr_id)}"/>
                    <input type="text"  hidden readonly value="${task.cr_id}" name="cr_id"/>

                    <input id="taskid" type="text" value="${taskid}" readonly name="taskid" hidden/>

                    <label for="name">Name</label>
                    <c:choose>
                        <c:when test="${isEditable==false || task.status==constants.CANCELLED}">
                            <input name="name" id="name" type="text" value="${task.name}" readonly/>
                        </c:when>
                        <c:otherwise>
                            <input name="name" id="name" type="text" value="${task.name}"/>
                            <label id="name-error" class="error" hidden >Name should not be empty</label>
                        </c:otherwise>
                    </c:choose>

                    <label for="desc">Description</label>
                    <c:choose>
                        <c:when test="${isEditable==false || task.status==constants.CANCELLED}">
                            <textarea name="desc" id="desc" rows="5" readonly>${task.description}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea name="desc" id="desc" rows="5">${task.description}</textarea>
                        </c:otherwise>
                    </c:choose>

                    <label for="tdate">Date</label>
                    <c:choose>
                        <c:when test="${isEditable==false || task.status==constants.CANCELLED}">
                            <input name="date" type="text" class="date-cell"  id="tdate" value="${formattedDate}" readonly/>
                        </c:when>
                        <c:otherwise>
                            <input name="date" type="text" class="date-cell"  id="tdate" value="${formattedDate}"/>
                            <label id="date-error" class="error" hidden>Please enter correct date</label>
                        </c:otherwise>
                    </c:choose>
                    <br/>
                    <label for="contacts">Contacts</label>
                    <c:choose>
                        <c:when test="${isEditable==false || task.status==constants.CANCELLED}">
                            <textarea name="contacts" id="contacts" rows="5" readonly>${task.contacts}</textarea>
                        </c:when>
                        <c:otherwise>
                            <textarea name="contacts" id="contacts" rows="5">${task.contacts}</textarea>
                        </c:otherwise>
                    </c:choose>

                    <label for="priority">Priority</label>
                    <c:choose>
                        <c:when test="${emp.ID!=task.cr_id || task.status==constants.CANCELLED}">
                            <c:if test="${task.priority == constants.LOW}">
                                <input id="priority" type="text" value="${constants.fullLow}" readonly  name="priority"/>
                            </c:if>
                            <c:if test="${task.priority == constants.NORMAL}">
                                <input id="priority" type="text" value="${constants.fullNormal}" readonly  name="priority"/>
                            </c:if>
                            <c:if test="${task.priority == constants.HIGH}">
                                <input id="priority" type="text" value="${constants.fullHigh}" readonly name="priority" />
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <select name="priority" id="priority">
                                <c:if test="${task.priority == constants.LOW}">
                                    <option value="${constants.LOW}" selected>${constants.fullLow}</option>
                                    <option value="${constants.NORMAL}">${constants.fullNormal}</option>
                                    <option value="${constants.HIGH}">${constants.fullHigh}</option>
                                </c:if>
                                <c:if test="${task.priority == constants.NORMAL}">
                                    <option value="${constants.LOW}" >${constants.fullLow}</option>
                                    <option value="${constants.NORMAL}" selected>${constants.fullNormal}</option>
                                    <option value="${constants.HIGH}">${constants.fullHigh}</option>
                                </c:if>
                                <c:if test="${task.priority == constants.HIGH}">
                                    <option value="${constants.LOW}" >${constants.fullLow}</option>
                                    <option value="${constants.NORMAL}">${constants.fullNormal}</option>
                                    <option value="${constants.HIGH}" selected>${constants.fullHigh}</option>
                                </c:if>
                            </select>
                        </c:otherwise>
                    </c:choose>
                    <label for="status">Status</label>
                    <c:choose>
                        <c:when test="${emp.ID!=task.ex_id || task.status==constants.CANCELLED}">
                            <input id="status" name="status" type="text" value="${task.fullStatus}" readonly/>
                        </c:when>
                        <c:otherwise>
                            <select name="status" id="status">
                                <c:if test="${task.status == constants.NEW}">
                                    <option value="${constants.NEW}" selected>${constants.fullNew}</option>
                                    <option value="${constants.PERFORMING}">${constants.fullPerforming}</option>
                                </c:if>
                                <c:if test="${task.status == constants.PERFORMING}">
                                    <option value="${constants.NEW}">${constants.fullNew}</option>
                                    <option value="${constants.PERFORMING}" selected>${constants.fullPerforming}</option>
                                </c:if>
                            </select>
                        </c:otherwise>
                    </c:choose>
                    <c:if test="${task.status!=constants.CANCELLED}">
                        <input type="submit" value="Save" name="update"/>
                    </c:if>
                </form>
                <c:choose>
                    <c:when test="${task.status==constants.CANCELLED}">
                        <c:if test="${emp.ID==task.cr_id}">
                            <form action="activatetask" method="post" class="last-cell">
                                <button type="submit" name="taskid" value="${taskid}">Activate</button>
                            </form>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${emp.ID==task.ex_id}">
                            <form action="completetask" method="post" class="last-cell" id="complete-form">
                                <button type="submit" name="taskid"value="${taskid}">Complete</button>
                            </form>
                        </c:if>
                        <c:if test="${emp.ID==task.cr_id }">
                            <form action="canceltask" method="post">
                                <button type="submit" name="taskid" value="${taskid}">Cancel</button>
                            </form>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
    <div id="menu-container">
        <c:set var="b" value="${emp.ID == task.cr_id}" scope="page"/>
        <div class="actions">
            <c:if test="${(task.status==constants.NEW || task.status==constants.PERFORMING)}" >
                <a href="newtask.jsp?pt_id=${taskid}">+ New subtask</a>
            </c:if>

        </div>
        <div id="table-container">
            <c:set var="tasks" value="${emp.journalManager.getSubtasks(taskid)}"/>
            <c:choose>
                <c:when test="${!empty tasks}">
                    <table id="tasks" class="tablesorter">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Date</th>
                            <th>Status</th>
                            <th>Executor</th>
                            <th class="noSort">Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="task" items="${tasks}">
                            <tr>
                                <td>${task.ID}</td>
                                <td><a href="task.jsp?taskid=${task.ID}&pt_id=${taskid}" target="_blank">${task.name}</a> </td>
                                <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${task.date}"/></td>
                                <td>${task.fullStatus}</td>
                                <c:if test="${emp.ID != task.ex_id}">
                                    <td>
                                        <a href="emp.jsp?id=${task.ex_id}">${emp.getEmp(task.ex_id).name}</a>
                                    </td>
                                </c:if>
                                <c:if test="${emp.ID == task.ex_id}">
                                    <td>Me</td>
                                </c:if>
                                <c:if test="${b==true}">
                                    <td class="last-cell">
                                        <form action="deletetask"  class="delete-form" method="post">
                                            <button  class="delete-button" type="submit" name="id" value="${task.ID}"></button>
                                        </form>
                                        <form action="copytask" method="post">
                                            <button  class="copy-button" type="submit" name="id" value="${task.ID}"></button>
                                        </form>
                                    </td>
                                </c:if>
                                <c:if test="${b==false}">
                                    <td class="last-cell">
                                        <form action="copytask" method="post">
                                            <button  class="copy-button" type="submit" name="id" value="${task.ID}"></button>
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <c:if test="${emp.ID==task.ex_id}">
                        <label id="message" hidden>You don't have subtasks.</label>
                    </c:if>
                    <c:if test="${emp.ID!=task.ex_id}">
                        <label id="message" hidden>Employee doesn't have subtasks.</label>
                    </c:if>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</c:if>
<c:if test="${empty sessionScope.username}">
    Please <a href="start.jsp">Sign in</a>
    or <a href="registration.jsp">Sign up</a>
</c:if>

</body>
</html>
