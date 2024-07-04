<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <jsp:include page="../common/header.jsp"/>
        <div class="container mt-3">
            <div class="container-fluid p-1">
                <div class="row ">
                    <jsp:include page="../common/sidebar.jsp"/>
                    <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
                        <div class="container-fluid">
                            <div class="row">
                                <h2>Classes List</h2>
                                <br>
                                <div class="col-lg-4 col-md-4">
                                    <button type="button" class="btn" onclick="addClassHref()">Add new class</button>
                                </div>
                                <br>
                                <form action="${pageContext.request.contextPath}/classes/search" method="GET" id="searchForm">
                                    <table style="border: 0">
                                        <tr>
                                            <td><input type="text" placeholder="Class name" name="name"></td>
                                            <td>
                                                <select name="statusOption">
                                                    <option value="true">Active</option>
                                                    <option value="false">Inactive</option>
                                                </select>
                                            </td>
                                            <td><button class="btn btn-primary" onclick="submitForm()">Search</button></td>
                                        </tr>
                                    </table>
                                </form>
                                <table class="table table-striped mt-3">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Teacher</th>
                                            <th>Description</th>
                                            <th>Status</th>
                                            <th>Created Date</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${classes}" var="c">
                                            <tr>
                                                <td>${c.name}</td>
                                                <td>${c.teacherName}</td>
                                                <td>${c.description}</td>
                                                <td>${c.isActive == true ? "active" : "inactive"}</td>
                                                <td>${c.createDt}</td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/classes/details?id=${c.id}">Details</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </main>
                </div>
            </div>
            <jsp:include page="../common/footer.jsp"/>
        </div>
    </body>
    <script>
        function submitForm() {
            form.submit();
        }
        function addClassHref() {
            window.location.href = "${pageContext.request.contextPath}/classes/add";
        }
    </script>
</html>
