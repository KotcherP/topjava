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
    <th colspan=2>Action</th>
    <c:forEach var="mealToEntry" items="${mealsTo}">
        <tr style="background-color:${mealToEntry.excess ? 'indianRed' : 'paleGreen'}">
            <td>${dateTimeFormatter.format(mealToEntry.dateTime)}</td>
            <td>${mealToEntry.description}</td>
            <td>${mealToEntry.calories}</td>
            <td><a href="?action=edit&id=${mealToEntry.id}">edit</a></td>
            <td><a href="?action=delete&id=${mealToEntry.id}">delete</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="?action=add">Add meal</a></p>
</body>
</html>
