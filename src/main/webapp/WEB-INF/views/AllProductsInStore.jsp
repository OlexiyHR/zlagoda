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
        <h1>All products in store</h1>
        <div class="button-container">
            <form action="/sorted-products" method="post">
                <button type="submit" class="menu-button">Sorted products</button>
            </form>
            <form action="/available-units-of-product" method="post">
                <button type="submit" class="menu-button">The number of available units of the product by UPC</button>
            </form>
        </div>
                <table>
                    <tbody>
                        <%= request.getAttribute("htmlTable") %>
                    </tbody>
                </table>
                <div class="button-container">
                    <form action="/cashier" method="get">
                        <button type="submit" class="blue-button">Another Action</button>
                    </form>
                </div>
    </div>
</body>
</html>