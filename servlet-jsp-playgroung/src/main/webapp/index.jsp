<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Practice Page</title>
        <link rel="stylesheet" href="<%= application.getContextPath() %>/css/style.css" />
    </head>

    <body class="center-content">
        <div class="card">
        <%@ include file="menu.jsp" %>

            <h1 class="heading">Hello, World!</h1>
            <p class="paragraph">
                This is a simple webpage created with HTML and styled with a separate CSS file. It's fully responsive, so it looks great on any device.
            </p>
            <button class="button">Click Me</button>
        </div>

        <div>


    </body>

</html>
