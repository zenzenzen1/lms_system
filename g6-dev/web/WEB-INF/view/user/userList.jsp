<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User List</title>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"/>

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
            <h2>Users List</h2>
            <c:if test="${not empty message}">
                <div class="alert alert-${alert}">
                    <strong><span class="content">${message}</span></strong>
                </div>
                <script>
                    $(document).ready(function() {
                        // show the alert
                        setTimeout(function() {
                            $(".alert").alert('close');
                        }, 5000);
                    });
                </script>
            </c:if>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-10 text-left">
                        <form class="form-inline" method="GET" action="${pageContext.request.contextPath}/users/search">
                            <label class="sr-only" for="inputUsername">Username</label>
                            <input type="text" class="form-control mb-2 mr-sm-2" id="inputUsername"
                                   name="inputUsername" placeholder="Username">

                            <label class="sr-only" for="inputFullName">Full Name</label>
                            <input type="text" class="form-control mb-2 mr-sm-2" id="inputFullName"  name="inputFullName"
                                   placeholder="Full Name">

                            <label class="sr-only" for="inputEmail">Email</label>
                            <div class="input-group mb-2 mr-sm-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">@</div>
                                </div>
                                <input type="text" class="form-control" id="inputEmail" name="inputEmail"
                                       placeholder="Email">
                            </div>
                            <label class="sr-only" for="inputPhone">Phone</label>
                            <div class="input-group mb-2 mr-sm-2">
                                <div class="input-group-prepend">
                                    <div class="input-group-text">#</div>
                                </div>
                                <input type="text" class="form-control" id="inputPhone"  name="inputPhone"
                                       placeholder="Phone">
                            </div>
                            <label class="my-1 mr-2" for="inputRole">Role</label>
                            <select class="custom-select my-1 mr-sm-2" id="inputRole"  name="inputRole">
                                <option value="-1">Choose...</option>
                                <option value="1">ADMIN</option>
                                <option value="37">TEACHER</option>
                                <option value="22">STUDENT</option>
                            </select>

                            <label class="my-1 mr-2" for="inputStatus">Status</label>
                            <select class="custom-select my-1 mr-sm-2" id="inputStatus" name="inputStatus">
                                <option value="-1">Choose...</option>
                                <option value="0">Active</option>
                                <option value="1">Inactive</option>

                            </select>

                            <button type="submit" class="btn btn-primary mb-2">Submit</button>
                        </form>
                    </div>

                    <div class="col-md-2 text-right">
                        <button type="button" id="addUser" class="mb-3 btn btn-primary">Add User</button>
                    </div>
                </div>
            </div>
            <div class="table-responsive-md">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th>Username</th>
                        <th>Full Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>Action</th>

                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.name}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td>${user.lmsRolesByRoleId.settingValue}</td>
                            <td>
                                    ${user.isActive == true ?
                                            "<span class='badge badge-success'>Active</span>" :
                                            "<span class='badge badge-danger'>Inactive</span>"}
                            </td>
                            <td>
                                <a role="button" aria-pressed="true" class="btn btn-primary btn-sm"
                                   href="${pageContext.request.contextPath}/users/details?id=${user.id}">
                                    <i class="fa fa-info-circle"></i>
                                </a>
                                <a role="button" aria-pressed="true" class="btn btn-primary btn-sm"
                                   type="submit">
                                            <span style="color: white;">
                                                <i class="fa fa-pencil-square-o white"></i>
                                            </span>
                                </a>
                                <a role="button" aria-pressed="true" class="btn btn-primary btn-sm"
                                   href="${pageContext.request.contextPath}/users/delete?id=${user.id}">
                                            <span style="color: white;">
                                                <i class="fa fa-trash-o white"></i>
                                            </span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <jsp:include page="../common/paging.jsp"/>
            </div>
        </main>
    </div>
    <jsp:include page="../common/footer.jsp"/>
</div>
</body>
</html>
