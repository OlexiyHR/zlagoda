<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cashier Main Menu Manager (Zlagoda)</title>
    <style>
         body {
            font-family: Arial, sans-serif;
             background-color: #ffcccc;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        .container {
            max-width: 600px;
            margin: 50px auto; /* Вирівнює контейнер по центру горизонтально */
            padding: 20px;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #333;
            font-size: 24px;
        }
        .subtitle {
            color: #666;
            font-size: 14px;
            margin-top: -10px;
        }
        .button-container, .blue-button-container {
            margin-top: 30px;
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
        }
        .menu-button {
            background-color: #4CAF50; /* Green */
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            flex-grow: 1;
            flex-basis: calc(50% - 20px);
            box-sizing: border-box;
        }
        .menu-button:hover {
            background-color: #45a049; /* Darker green on hover */
        }
        .blue-button {
            background-color: #007BFF; /* Blue */
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            font-size: 16px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
            flex-grow: 1;
            flex-basis: calc(50% - 20px);
            box-sizing: border-box;
        }
        .blue-button:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }
        form {
            flex-grow: 1;
            display: flex;
        }
    </style>
</head>
<body>
    <div class="container">
        <p class="subtitle">Zlagoda</p>
        <h1>Main Menu Cashier</h1>
        <div class="button-container">
            <form action="./all-products" method="get">
                <button type="submit" class="menu-button">All products</button>
            </form>
            <form action="/all-products-in-store" method="get">
                <button type="submit" class="menu-button">All products in store</button>
            </form>
            <form action="/manage-regular-clients" method="post">
                <button type="submit" class="menu-button">Manage regular clients</button>
            </form>
            <form action="/manage-check" method="post">
                <button type="submit" class="menu-button">Manage check</button>
            </form>
        </div>
        <div class="blue-button-container">
            <form action="/user-profile" method="get">
                <button type="submit" class="blue-button">User profile</button>
            </form>
            <form action="/cashier" method="post">
                <button type="submit" class="blue-button">Log out</button>
            </form>
        </div>
    </div>
</body>
</html>