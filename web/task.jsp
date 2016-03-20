<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
<jsp:useBean id="emp" class="emp.Employee" scope="session"/>
<jsp:useBean id="constants" class="utils.Constants"/>
<c:set var="taskid" value="${param.taskid}"/>
<div class="task-form">
    <c:set var="task" value="${emp.journalManager.get(taskid)}"/>
    <fmt:formatDate value="${task.date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate"/>
    <c:set var="isCompleted" value="${task.status==constants.COMPLETED || task.status==constants.CANCELLED}" scope="session"/>
    <c:set var="isEditable" value="${task.cr_id==emp.ID}" scope="page"/>
    <form method="post" action="savetask" id="update-task-form">
        <table>
            <c:if test="${param.pt_id != null}">
                <fmt:formatDate value="${emp.journalManager.get(param.pt_id).date}" type="both" dateStyle="medium" timeStyle="short" var="formattedDate1"/>
                <tr>
                    <th>Parent task ID</th>
                    <td><input type="text" readonly value="${task.pt_id}" name="pt_id" id="pt_id"/></td>
                </tr>
                <tr>
                    <th>Parent task date</th>
                    <td><input class="date-cell" type="text" id="task-date" readonly value="${formattedDate1}"/></td>
                </tr>
            </c:if>
            <tr>
                <th>Executor</th>
                <td>
                    <c:if test="${isCompleted==true || emp.ID!=task.cr_id}">
                            <c:if test="${emp.ID==task.ex_id}">
                                <input type="text" readonly value="Me" />
                            </c:if>
                            <c:forEach items="${emp.emps}" var="e">
                                <c:if test="${e.ID==task.ex_id}">
                                    <input type="text" readonly value="${e.name}" />
                                </c:if>
                            </c:forEach>
                    </c:if>
                    <c:if test="${isCompleted!=true && emp.ID==task.cr_id}">
                        <select name="ex_id">
                            <c:if test="${emp.ID==task.ex_id}">
                                <option value="${emp.ID}" selected>Me</option>
                            </c:if>
                            <c:if test="${emp.ID !=task.ex_id}">
                                <option value="${emp.ID}" >Me</option>
                            </c:if>
                            <c:forEach items="${emp.emps}" var="e">

                                <c:if test="${e.ID==task.ex_id}">
                                    <option value="${e.ID}" selected>${e.name}</option>
                                </c:if>
                                <c:if test="${e.ID !=task.ex_id}">
                                    <option value="${e.ID}">${e.name}</option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </c:if>
                </td>
            </tr>
            <tr>
                <th>Creator</th>
                <td><input type="text" readonly value="${emp.getName(task.cr_id)}"/></td>
            </tr>
            <tr>
                <th>ID</th>
                <td><input type="text" value="${taskid}" readonly name="taskid"/></td>
            </tr>
            <tr>
                <th>Name</th>
            <c:if test="${isCompleted!=true &&  emp.ID==task.cr_id}">
                <td><input name="name" id="name" type="text" value="${task.name}"/></td>

             </c:if>
            <c:if test="${isCompleted==true || emp.ID!=task.cr_id}">
             <td><input name="name" type="text" value="${task.name}" readonly/></td>
            </c:if>
            </tr>
            <c:if test="${isCompleted!=true &&  emp.ID==task.cr_id}">
               <tr>
                   <td></td>
                   <td id="name-error" class="error" hidden >Name should not be empty</td>
               </tr>
            </c:if>
            <tr>
                <th>Description</th>
                <c:if test="${isCompleted!=true}">
                    <td><textarea name="desc" rows="7">${task.description}</textarea></td>
                </c:if>
                <c:if test="${isCompleted==true}">
                    <td><textarea name="desc" rows="7" readonly>${task.description}</textarea></td>
                </c:if>

            </tr>
            <tr>
                <th>Date</th>
                <c:if test="${isCompleted!=true}">
                    <td><input name="date" type="text" class="date-cell"  id="tdate" value="${formattedDate}"/></td>
                </c:if>
                <c:if test="${isCompleted==true}">
                    <td><input name="date" type="text" class="date-cell"  value="${formattedDate}" readonly/></td>
                </c:if>
            </tr>
            <c:if test="${isCompleted!=true}">
               <tr>
                   <td></td>
                   <td id="date-error" class="error" hidden>Please enter correct date</td>
               </tr>
            </c:if>
            <tr>
                <th>Contacts</th>
                <c:if test="${isCompleted!=true}">
                    <td><textarea name="contacts" rows="7">${task.contacts}</textarea></td>
                </c:if>
                <c:if test="${isCompleted==true}">
                    <td><textarea name="contacts" rows="7" readonly>${task.contacts}</textarea></td>
                </c:if>

            </tr>
            <tr>
                <th>Priority</th>
                <c:if test="${isCompleted==true}">
                    <td>
                        <c:if test="${task.priority == constants.LOW}">
                            <input type="text" value="Low" readonly/>
                        </c:if>
                        <c:if test="${task.priority == constants.MEDIUM}">
                            <input type="text" value="Medium" readonly/>
                        </c:if>
                        <c:if test="${task.priority == constants.HIGH}">
                            <input type="text" value="High" readonly/>
                        </c:if>
                    </td>
                </c:if>
                <c:if test="${isCompleted!=true}">
                    <td><select name="priority">

                        <c:if test="${task.priority == constants.LOW}">
                            <option value="${constants.LOW}" selected>Low</option>
                            <option value="${constants.MEDIUM}">Medium</option>
                            <option value="${constants.HIGH}">High</option>
                        </c:if>
                        <c:if test="${task.priority == constants.MEDIUM}">
                            <option value="${constants.LOW}" >Low</option>
                            <option value="${constants.MEDIUM}" selected>Medium</option>
                            <option value="${constants.HIGH}">High</option>
                        </c:if>
                        <c:if test="${task.priority == constants.HIGH}">
                            <option value="${constants.LOW}" >Low</option>
                            <option value="${constants.MEDIUM}">Medium</option>
                            <option value="${constants.HIGH}" selected>High</option>
                        </c:if>
                    </select>
                    </td>
                </c:if>
            </tr>
            <tr>
                <th>Status</th>

                <c:if test="${emp.ID!=task.ex_id && isCompleted!=true}">
                    <td><input name="status" type="text" value="${task.fullStatus}" readonly/></td>
                </c:if>
                <c:if test="${isCompleted==true}">
                    <td><input name="status" type="text" value="${task.fullStatus}" readonly/></td>
                </c:if>
                <c:if test="${isCompleted!=true && emp.ID==task.ex_id}">
                    <td><select name="status">
                        <c:if test="${task.status == constants.NEW}">
                            <option value="${constants.NEW}" selected>New</option>
                            <option value="${constants.PERFORMING}">In progress</option>
                        </c:if>
                        <c:if test="${task.status == constants.PERFORMING}">
                            <option value="${constants.NEW}">New</option>
                            <option value="${constants.PERFORMING}" selected>In progress</option>
                        </c:if>
                    </select>
                    </td>
               </c:if>
            </tr>
        </table>
        <c:if test="${isCompleted!=true}">
            <input type="submit" value="Save" name="update"/>
        </c:if>
    </form>
    <c:if test="${isCompleted!=true}">
        <c:if test="${emp.ID==task.ex_id}">
            <form action="completetask" method="post">
                <input type="text" name="taskid" hidden readonly value="${taskid}"/>
                <input type="submit" value="Complete" name="update"/>
            </form>
        </c:if>
        <c:if test="${emp.ID==task.cr_id}">
            <form action="savetask" method="post">
                <input type="text" name="taskid" hidden readonly value="${taskid}"/>
                <input type="submit" value="Cancel" name="update"/>
            </form>
        </c:if>
    </c:if>
</div>
<div id="menu-container">
        <div class="actions">
            <c:if test="${isCompleted!=true}" >
                <a href="newtask.jsp?pt_id=${taskid}">+ New subtask</a>
            </c:if>
            <br/>
            <label id="message" hidden>You don't have subtasks.</label>
        </div>

        <div id="table-container">

            <c:set var="b" value="${emp.ID == task.cr_id}" scope="page"/>
            <c:set var="tasks" value="${emp.journalManager.getSubtasks(taskid)}"/>
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
                       <td>
                           <fmt:formatDate type="both" dateStyle="medium" timeStyle="short" value="${task.date}"/>
                       </td>
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
                               <form action="deletetask"  id="delete-form" method="post">
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
        </div>
</div>
<div>

</div>
</body>
</html>
