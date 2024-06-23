<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cashier Main Menu (Zlagoda)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffdab9;
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
        .button-container, .blue-button-container {
            margin-top: 30px;
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            justify-content: center;
        }
        .menu-button {
            background-color: #4CAF50;
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
            background-color: #45a049;
        }
        .blue-button {
            background-color: #007BFF;
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
            background-color: #0056b3;
        }
        form {
            flex-grow: 1;
            display: flex;
        }
        .input-container {
            margin-top: 20px;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            box-sizing: border-box;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <div class="container">
        <p class="subtitle">Zlagoda</p>
        <h1>All products</h1>
        <div class="button-container">
            <form action="/all-products-information" method="post">
                <button type="submit" class="menu-button">Information about all products</button>
            </form>
            <form action="/search-by-name" method="post">
                <button type="submit" class="menu-button">Search by name</button>
            </form>
            <form action="/promotional-products" method="post">
                <button type="submit" class="menu-button">Information about all promotional products</button>
            </form>
            <form action="/non-promotional-products" method="post">
                <button type="submit" class="menu-button">Information about all non-promotional products</button>
            </form>
            <form action="/sale-price" method="post">
                <button type="submit" class="menu-button">The sale price of the product by UPC</button>
            </form>
        </div>
        <div class="input-container">
            <input type="text" id="product-name" name="product-name" placeholder="Enter product name">
        </div>
        <table>
            <tbody>
                <%= request.getAttribute("htmlTable") %>
            </tbody>
        </table>
        <div class="button-container">
            <form action="/another-action" method="post">
                <button type="submit" class="blue-button">Another Action</button>
            </form>
        </div>
    </div>
</body>
</html>