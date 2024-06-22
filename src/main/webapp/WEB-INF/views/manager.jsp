<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manager Main Menu Manager (Zlagoda)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
            text-align: center;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
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
        .button-container {
            margin-top: 30px;
            display: flex;
            justify-content: center;
        }
        .menu-button {
            background-color: #4CAF50; /* Green */
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px;
            cursor: pointer;
            border: none;
            border-radius: 4px;
        }
        .menu-button:hover {
            background-color: #45a049; /* Darker green on hover */
        }
    </style>
</head>
<body>
    <div class="container">
        <p class="subtitle">Zlagoda</p>
        <h1>Main Menu Manager</h1>
        <div class="button-container">
            <form action="/show-number-buyers" method="post">
                <button type="submit" class="menu-button">Show number of buyers of each product</button>
            </form>
            <form action="/show-same-clients" method="post">
                <button type="submit" class="menu-button">Show sellers who sold products to all customers of another</button>
            </form>
        </div>
    </div>
</body>
</html>