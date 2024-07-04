<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<nav id="sidebarNav" class="col-sm-2 col-lg-2 d-md-block bg-light sidebar collapse">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active btn btn-light" href="${pageContext.request.contextPath}/home" data-toggle="tooltip" data-placement="right" title="Home">
                    <span data-feather="home"></span>
                    <span class="menu-text">Home</span><span class="sr-only">(current)</span>
                </a>
            </li>
            <c:forEach items="${authorities}" var="item">
                <li class="nav-item">
                    <a class="nav-link btn btn-light" href="${pageContext.request.contextPath}${item.url}/all" data-toggle="tooltip" data-placement="right" title="${item.urlName}">
                        <span data-feather="file"></span>
                        <span class="menu-text">${item.urlName}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
</nav>