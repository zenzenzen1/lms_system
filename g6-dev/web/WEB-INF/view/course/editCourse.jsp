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
                    <h2>Edit Course</h2>
                    <form action="${pageContext.request.contextPath}/course/edit" method="post">
                        <div class="form-group">
                            <label for="subject">Subject:</label>
                            <select class='form-control' id='subject' name='subject'>
                                <c:forEach items="${subjectList}" var="s">
                                    <c:choose>
                                        <c:when test="${s.id eq course.subjectId}">
                                            <option value='${s.id}' selected>${s.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value='${s.id}'>${s.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class='form-group'>
                            <label for='teacher'>Teacher:</label>
                            <select class='form-control' id='teacher' name='teacher'>
                                <c:forEach items="${teacherList}" var="t">
                                    <c:choose>
                                        <c:when test="${t.id eq course.teacherId}">
                                            <option value='${t.id}' selected>${t.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value='${t.id}'>${t.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                        <div class='form-group'>
                            <label for='code'>Code:</label>
                            <input class='form-control' type='text' name='code' id='code' value='${course.code}'>
                        </div>
                        <div class='form-group'>
                            <label for='semester'>Semester:</label>
                            <select class='form-control' name='semester' id='semester'>

                            </select>
                        </div>
                        <div class='form-group'>
                            <c:choose>
                                <c:when test="${course.isActive eq true}">
                                    <input class="form-check-input" type="radio" name="status" id="active" value='active' checked>
                                    <input class="form-check-input" type="radio" name="status" id="inactive" value='inactive'>
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input" type="radio" name="status" id="active" value='active'>
                                    <input class="form-check-input" type="radio" name="status" id="inactive" value='inactive' checked>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <button type='button' class='btn btn-primary' onclick='submitForm()'>Edit</button>
                        <button type='button' class='btn btn-secondary' onclick='backPage()'>Cancel</button>
                    </form>
                </main>
            </div>
            <jsp:include page="../common/footer.jsp"/>
        </div>
    </body>
    <script>
        function submitForm() {
            if (confirm("Are you sure you want to edit this course?")) {
                form.submit();
            }
        }
        function backPage() {
            window.history.back();
        }
    </script>
</html>
