<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    <h2>Courses List</h2>
                    <form action='${pageContext.request.contextPath}/course/search' method='get' onsubmit="saveFormValues()">
                        <table style='border: 0'>
                            <tr>
                            <input type="text" name="code" id="code" placeholder="Course Code">
                            </tr>
                            <tr>
                            <select id="subject" name="subject">
                                <option value="">All</option>
                                <c:forEach items="subjectList" var="s">
                                    <option value="${s.id}">${s.name}</option>
                                </c:forEach>
                            </select>
                            </tr>
                            <tr>
                            <select id="teacher" name="teacher">
                                <option value="">All</option>
                                <c:forEach items="teacherList" var="t">
                                    <option value="${t.id}">${t.name}</option>
                                </c:forEach>
                            </select>
                            </tr>
                            <tr>
                            <select id="status" name="status">
                                <option value="">All</option>
                                <option value="true">Active</option>
                                <option value="false">Inactive</option>
                            </select>
                            </tr>
                            <tr><input type="submit" value="Search"></tr>
                        </table>
                    </form>
                    <table class="table table-striped mt-3">
                        <thead>
                            <tr>
                                <th>Course Code</th>
                                <th>Subject</th>
                                <th>Semester</th>
                                <th>Teacher</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${courseList}" var="s">
                                <tr>
                                    <td>${s.code}</td>
                                    <td>${s.subjects.name}</td>
                                    <td>${s.semester != null ? s.semester : "none"}</td>
                                    <td>${s.teacher.name}</td>
                                    <td>${s.isActive == true ? "active" : "inactive"}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/course/details?id=${s.id}">Details</a>
                                        <a href="${pageContext.request.contextPath}/course/changeStatus?id=${s.id}">Change Status</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </main>
            </div>
            <jsp:include page="../common/footer.jsp"/>
        </div>
    </body>
    <script>
        function saveFormValues() {
            localStorage.setItem("codeValue", document.getElementById("code").value);
            localStorage.setItem("subjectValue", document.getElementById("subject").value);
            localStorage.setItem("teacherValue", document.getElementById("teacher").value);
            localStorage.setItem("statusValue", document.getElementById("status").value);
        }
        
        window.onload = function() {
            var codeInput = document.getElementById("code");
            var subjectInput = document.getElementById("subject");
            var teacherInput = document.getElementById("teacher");
            var statusInput = document.getElementById("status");
            
            var storedCode = localStorage.getItem("codeValue");
            if (storedCode) {
                codeInput.value = storedCode;
            }
            var storedSubject = localStorage.getItem("subjectValue");
            if (storedSubject) {
                var subjectoptions = subjectInput.options;
                for (var i = 0; i < subjectoptions.length; i++) {
                    if (subjectoptions[i].value === storedSubject) {
                        subjectoptions[i].selected = true;
                        break;
                    }
                }
            }
            var storedTeacher = localStorage.getItem("teacherValue");
            if (storedTeacher) {
                var teacherOptions = teacherInput.options;
                for (var i = 0; i < teacherOptions.length; i++) {
                    if (teacherOptions[i].value === storedTeacher) {
                        teacherOptions[i].selected = true;
                        break;
                    }
                }
            }
            var storedStatus = localStorage.getItem("statusValue");
            if (storedStatus) {
                var statusOptions = statusInput.options;
                for (var i = 0; i < statusOptions.length; i++) {
                    if (statusOptions[i].value === storedStatus) {
                        statusOptions[i].selected = true;
                        break;
                    }
                }
            }
        }
    </script>
</html>
