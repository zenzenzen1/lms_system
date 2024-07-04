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
                    <h2>Subjects List</h2>
                    <table class="table table-striped mt-3">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Created Date</th>
                                <th>Description</th>
                                <th>Is Online</th>
                                <th>Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${subjects}" var="s">
                                <tr>
                                    <td>${s.name}</td>
                                    <td>${s.createDt}</td>
                                    <td>${s.description}</td>
                                    <td>${s.isOnline == true ? "yes" : "no"}</td>
                                    <td>${s.isActive == true ? "yes" : "no"}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/subject/details?id=${s.id}">Details</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <jsp:include page="../common/paging.jsp"/>
                </main>
            </div>
            <jsp:include page="../common/footer.jsp"/>
        </div>
    </body>
</html>
