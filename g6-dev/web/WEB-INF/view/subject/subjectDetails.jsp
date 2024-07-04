<%-- 
    Document   : subjectDetails
    Created on : Apr 14, 2024, 9:43:53 PM
    Author     : Hayashi
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <div class="container mt-3">
            <jsp:include page="../common/header.jsp"/>

            <div class="mt-3">
                <h2>${subject.name} details</h2>

                <ul class="list-group">
                    <li class="list-group-item">Subject Code: ${subject.code}</li>
                    <li class="list-group-item">Name: ${subject.name}</li>
                    <li class="list-group-item">Is Online: ${subject.isOnline == true ? "yes" : "no"}</li>
                    <li class="list-group-item">Description: ${subject.description}</li>
                </ul>
                <a href="${pageContext.request.contextPath}/subject/delete?id=${subject.id}">Delete</a>
                <a href="${pageContext.request.contextPath}/subject/edit?id=${subject.id}">Edit</a>
            </div>

            <jsp:include page="../common/footer.jsp"/>
        </div>
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
    </body>
</html>
