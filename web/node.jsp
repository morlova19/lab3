<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<body>
<c:set var="node" value="${node}" scope="request"/>
<ul>
    <c:forEach var="node" items="${node.emps_map.values()}">
        <c:set var="node" value="${node}" scope="request"/>
        <c:if test="${sessionScope.emp.ID == node.ID}">
            <c:if test="${!empty node.emps_map}">
                <li class="current" value="${node.ID}"><input type="button" value="-" class="hide-child"/><a href="emp.jsp?id=${node.ID}">Me</a>   <jsp:include page="node.jsp"/></li>
            </c:if>
            <c:if test="${empty node.emps_map}">
                <li class="current" ><a href="emp.jsp?id=${node.ID}">Me</a><jsp:include page="node.jsp"/></li>
            </c:if>
        </c:if>
        <c:if test="${sessionScope.emp.ID != node.ID}">
            <c:if test="${!empty node.emps_map}">
                <li ><input type="button" value="-" class="hide-child"/> <a href="emp.jsp?id=${node.ID}">${node.name}</a><jsp:include page="node.jsp"/></li>
            </c:if>
            <c:if test="${empty node.emps_map}">
                <li><a href="emp.jsp?id=${node.ID}">${node.name}</a> <jsp:include page="node.jsp"/></li>
            </c:if>
        </c:if>
    </c:forEach>
</ul>


</body>
</html>
