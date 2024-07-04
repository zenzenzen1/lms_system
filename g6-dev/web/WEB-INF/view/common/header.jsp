<!DOCTYPE html>
<html>
<head>
    <title>Header</title>
    <script>
        const contextPath = "<%=request.getContextPath()%>";
    </script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/css/common.css">
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css"
            rel="stylesheet"
    />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/vendors/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/resource/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/js/jquery.scroller.js"></script>
    <script src="${pageContext.request.contextPath}/resource/vendors/bootstrap/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/vendors/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resource/vendors/js.cookie/js.cookie.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resource/vendors/toastr/toastr.css">
    <script src="${pageContext.request.contextPath}/resource/vendors/toastr/toastr.js"></script>
</head>
<header>
    <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
        <button class="navbar-toggler col-sm-2 col-md-2 mr-0" type="button" id="sidebarToggle">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="${pageContext.request.contextPath}/home">Learning
            Management System</a>
        <ul class="navbar-nav px-3">
            <li class="nav-item text-nowrap">
                <a id="login-link" href="${pageContext.request.contextPath}/login">Login</a>
                <a style="display:none;" id="logout-link" href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
        </ul>
    </nav>
</header>
</html>