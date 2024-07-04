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
                    <h2>Add New Course</h2>
                    <form action="${pageContext.request.contextPath}/course/add" method="post">
                        <div class="form-group">
                            <label for="subject">Subject:</label>
                            <select class='form-control' id='subject' name='subject'>
                                <c:forEach items="${subjectList}" var="s">
                                    <option value='${s.id}'>${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class='form-group'>
                            <label for='teacher'>Teacher:</label>
                            <select class='form-control' id='teacher' name='teacher'>
                                <c:forEach items="${teacherList}" var="t">
                                    <option value='${t.id}'>${t.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class='form-group'>
                            <label for='code'>Code:</label>
                            <input class='form-control' type='text' name='code' id='code'>
                        </div>
                        <div class='form-group'>
                            <label for='semester'>Semester:</label>
                            <select class='form-control' name='semester' id='semester'>

                            </select>
                        </div>
                        <div class='form-group'>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" id="active" value='active'>
                                <label class="form-check-label" for="active">
                                    Active
                                </label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="status" id="inactive" value='inactive' checked>
                                <label class="form-check-label" for="inactive">
                                    Inactive
                                </label>
                            </div>
                        </div>
                        <button type='button' class='btn btn-primary' onclick='submitForm()'>Add</button>
                        <button type='button' class='btn btn-secondary' onclick='backPage()'>Cancel</button>
                    </form>
                </main>
            </div>
            <jsp:include page="../common/footer.jsp"/>
        </div>
    </body>
    <script>
        function submitForm() {
            if (confirm("Are you sure you want to add this course?")) {
                form.submit();
            }
        }
        function backPage() {
            window.history.back();
        }
    </script>
</html>
