<%-- 
    Document   : addCourse
    Created on : Apr 25, 2024, 8:46:03 PM
    Author     : Hayashi
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <header>
            <%
                String contextPath = request.getContextPath() + "/resource/";
            %>
            <jsp:include page="../common/header.jsp"/>
        </header>
        <div class="container-fluid p-1">
            <div class="row ">
                <jsp:include page="../common/sidebar.jsp"/>
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
                    <h2>${course.code} details</h2>

                    <ul class="list-group">
                        <li class="list-group-item">Course Code: ${course.code}</li>
                        <li class="list-group-item">Subject: ${course.subjects.name}</li>
                        <li class="list-group-item">Semester: ${course.semester == null ? "none" : course.semester}</li>
                        <li class="list-group-item">Teacher: ${course.teacher.name}</li>
                        <li class="list-group-item">Status: ${course.isActive == true ? "active" : "inactive"}</li>
                    </ul>
                    <button type='button' class='btn' onclick='changeStatusHref()'>Delete</button>
                    <button type='button' class='btn' onclick="editHref()">Edit</button>
                </main>
            </div>
            <jsp:include page="../common/footer.jsp"/>
        </div>
    </body>
    <script>
        function changeStatusHref() {
            window.location.href = ${pageContext.request.contextPath}/course/changeStatus?id=${course.id};
        }
        function editHref() {
            window.location.href = ${pageContext.request.contextPath}/course/edit?id=${course.id};
        }
    </script>
</html>

