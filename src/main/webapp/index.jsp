<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome to Zlagoda Store</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #ccffcc;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        .container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
            font-size: 28px;
            margin-bottom: 10px;
        }
        p {
            color: #666;
            font-size: 18px;
            margin-bottom: 20px;
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
            transition: background-color 0.3s;
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