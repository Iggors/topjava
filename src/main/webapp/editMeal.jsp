<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>

<html lang="ru">
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h3>${param.action == 'add' ? 'Add meal' : 'Edit meal'}</h3>
<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
<form method="post" action="meals">
    <input type="hidden" name="id" value="${meal.id}">
    <table>
        <tr align="left">
            <th>DateTime:</th>
            <th><input type="datetime-local" value=${meal.dateTime} name="dateTime"></th>
        </tr>
        <tr align="left">
            <th>Description:</th>
            <th><input type="text" value="${meal.description}" name="description"></th>
        </tr>
        <tr align="left">
            <th>Calories:</th>
            <th><input type="number" value="${meal.calories}" name="calories"></th>
        </tr>
    </table>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>