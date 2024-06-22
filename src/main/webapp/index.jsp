<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to Zlagoda Store</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        h1 {
            color: #333;
        }
        p {
            color: #666;
        }
        .login-form {
            margin-top: 20px;
        }
        .login-button {
            background-color: #4CAF50; /* Зелений колір */
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
        }
        .login-button:hover {
            background-color: #45a049; /* Темнозелений колір при наведенні */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Welcome to Zlagoda Store</h1>
        <p>Please login to your account:</p>
        <form action="/login" method="get" class="login-form">
            <button type="submit" class="login-button">Login</button>
        </form>
    </div>
</body>
</html>