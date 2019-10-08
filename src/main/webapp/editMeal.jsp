<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<form method="post">
    <c:if test="${!empty mealEdit.id}">
        <input type="hidden" name="id" value="${mealEdit.id}">
    </c:if>

    <label for="dateTime">Date time</label>
    <input type="datetime-local" name="dateTime" id="dateTime" value="${mealEdit.dateTime}">

    <label for="description">Description</label>
    <input type="text" name="description" id="description" value="${mealEdit.description}">

    <label for="calories">Calories</label>
    <input type="number" name="calories" id="calories" value="${mealEdit.calories}">

    <input type="submit" value="${empty mealEdit.id ? "Add" : "Edit" }">

</form>
</body>
</html>
