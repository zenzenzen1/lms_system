<%-- 
    Document   : addClass
    Created on : Apr 22, 2024, 10:01:57 AM
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
            <jsp:include page="../common/sidebar.jsp"/>
            <div class="container mt-3">
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
                    <div class="container-fluid">
                        <div class="row">
                            <h2>Add new class</h2>

                            <form method="post" action="${pageContext.request.contextPath}/classes/add?">
                                <div class="form-group">
                                    <label for="className">Class Name:</label>
                                    <input type="text" class="form-control" id="className">
                                </div>
                                <div class="form-group">
                                    <label for="classDescription">Class Name:</label>
                                    <input type="text"class="form-control" id="classDescription">
                                </div>
                                <div class="form-group">
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="isActive" id="activeClass">
                                        <label class="form-check-label" for="activeClass">
                                            Active
                                        </label>
                                    </div>
                                    <div class="form-check">
                                        <input class="form-check-input" type="radio" name="isActive" id="inactiveClass" checked>
                                        <label class="form-check-label" for="inactiveClass">
                                            Inactive
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="classTeacher">Teacher:</label>
                                    <div class="dropdown">
                                        <button class="btn btn-secondary dropdown-toggle" type="button" id="teacherList" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                            Choose teacher
                                        </button>
                                        <div class="dropdown-menu" aria-labelledby="teacherList">
                                            <button class="dropdown-item" type="button" value=""></button>
                                            <button class="dropdown-item" type="button" value=""></button>
                                            <button class="dropdown-item" type="button" value=""></button>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </main>
            </div>
            <jsp:include page="../common/footer.jsp" />
        </div>
    </body>
</html>
