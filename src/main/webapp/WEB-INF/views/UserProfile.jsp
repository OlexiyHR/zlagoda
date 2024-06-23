<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #baa8ff;
            color: #333;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .profile-container {
            max-width: 600px; /* Зменшуємо максимальну ширину контейнера */
            width: 90%;
            margin: 20px;
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .profile-container h1 {
            text-align: center;
            color: #2c3e50;
            font-size: 2em; /* Зменшуємо розмір заголовку */
            margin-bottom: 15px;
        }
        .profile-details {
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
            margin-top: 15px;
        }
        .profile-details p {
            font-size: 1em; /* Зменшуємо розмір шрифту профільних деталей */
            color: #34495e;
            background-color: #ecf0f1;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 10px;
            flex: 0 0 48%;
            box-sizing: border-box;
        }
        .profile-details p:nth-child(odd) {
            background-color: #dff9fb;
        }
        .profile-details p:nth-child(even) {
            background-color: #f9f7e8;
        }
        .button-container {
            text-align: center;
            margin-top: 20px;
        }
        .menu-button {
             background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 1em; /* Зменшуємо розмір кнопки */
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .menu-button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <h1>User Profile</h1>
        <div class="profile-details">
            <c:forEach var="profileItem" items="${UserProfile.split(', ')}">
                <p>${profileItem}</p>
            </c:forEach>
        </div>

        <div class="button-container">
            <form action="/manager" method="get">
                <button type="submit" class="menu-button">Another Action</button>
            </form>
        </div>
    </div>
</body>
</html>