<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Employee Sales Query</title>
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
        }
        form {
            margin-top: 20px;
        }
        label {
            display: block;
            margin: 10px 0 5px;
            color: #666;
        }
        input[type="text"] {
            padding: 10px;
            width: 100%;
            box-sizing: border-box;
            margin-bottom: 10px;
        }
        button {
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
        button:hover {
            background-color: #45a049; /* Darker green on hover */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Employee Sales Query</h1>
        <p>This request retrieves sellers who sold products to all customers to whom a specific seller, named [name], sold.</p>
        <form action="/show-same-clients" method="get">
            <label for="name">Enter name:</label>
            <input type="text" id="name" name="name" required>
            <label for="surname">Enter surname:</label>
            <input type="text" id="surname" name="surname" required>
            <button type="submit">Submit</button>
        </form>
    </div>
</body>
</html>