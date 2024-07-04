

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f2f2f2;
                margin: 0;
                padding: 0;
            }

            .container {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                height: 100vh;
                width: 100%;
                background-color: #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            h1 {
                font-size: 2.5rem;
                color: #444;
                margin-bottom: 3rem;
            }

            .form-group {
                display: flex;
                flex-direction: column;
                align-items: center;
                width: 20rem;
                margin-bottom: 2rem;
            }

            label {
                font-size: 1.2rem;
                color: #444;
                margin-bottom: 0.5rem;
                text-align: left;
                width: 100%;
            }

            input[type="text"],
            input[type="password"] {
                width: 100%;
                padding: 0.5rem;
                font-size: 1rem;
                border: 1px solid #ccc;
                border-radius: 4px;
                margin-bottom: 1.5rem;
            }

            input[type="submit"] {
                width: 100%;
                padding: 0.5rem;
                font-size: 1rem;
                background-color: #4CAF50;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
            }

            p {
                font-size: 0.9rem;
                color: #444;
                margin-top: 1rem;
                text-align: center;
            }

            a {
                color: #4CAF50;
                text-decoration: none;
            }

            a:hover {
                text-decoration: underline;
            }

            .teacher-login,
            .student-login {
                display: flex;
                flex-direction: column;
                align-items: center;
            }
        </style>
    </head>
    <body>
        <div class="container">
            <h1>Login</h1>
            <div class="teacher-login">
                <div class="form-group">
                    <form>
                        <label for="teacher-username">Username</label>
                        <input type="text" id="teacher-username" placeholder="Enter username">

                        <label for="teacher-password">Password</label>
                        <input type="password" id="teacher-password" placeholder="Enter password">

                        <input type="submit" value="Login as Teacher">
                    </form>
                </div>
            </div>
            <div class="student-login">
                <div class="form-group">
                    <form>
                        <label for="student-username">Username</label>
                        <input type="text" id="student-username" placeholder="Enter username">

                        <label for="student-password">Password</label>
                        <input type="password" id="student-password" placeholder="Enter password">

                        <input type="submit" value="Login as Student">
                    </form>
                </div>
