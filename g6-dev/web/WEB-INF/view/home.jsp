<%--
  Created by IntelliJ IDEA.
  User: macbook
  Date: 09/04/2024
  Time: 01:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Home</title>
    </head>
    <body>
        <header>
            <jsp:include page="common/header.jsp"/>
            <div class="container-fluid">
                <div class="row">
                    <jsp:include page="common/sidebar.jsp"/>
                </div>
            </div>
        </header>

        <jsp:include page="common/footer.jsp"/>
    </body>
</html>