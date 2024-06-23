<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .profile-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
        }
        .profile-container h1 {
            text-align: center;
        }
        .profile-details {
            margin: 20px 0;
        }
        .profile-details dt {
            font-weight: bold;
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