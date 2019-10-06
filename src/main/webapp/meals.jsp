<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border=1>
    <th>Date time</th>
    <th>Description</th>
    <th>Calories</th>
    <th>Excess</th>
    <c:forEach var="mealToEntry" items="${mealsTo}">
        <tr style="background-color:${mealToEntry.excess ? 'paleGreen' : 'indianRed'}">
            <td>
                <fmt:parseDate value="${mealToEntry.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="dateTimeFmt"/>
                <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${dateTimeFmt}"/>
            </td>
            <td><c:out value="${mealToEntry.description}"/></td>
            <td><c:out value="${mealToEntry.calories}"/></td>
            <td><c:out value="${mealToEntry.excess}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
