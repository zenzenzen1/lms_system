<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page trimDirectiveWhitespaces="true" %>
<nav aria-label="Page navigation">
    <ul class="pagination">
        <c:forEach var="i" begin="1" end="${totalPages}">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <c:url value="${servletPath}" var="pageUrl">
                    <c:param name="page" value="${i}" />
                    <c:param name="size" value="${size}" />

                    <c:if test="${not empty type}">
                        <c:param name="type" value="${type}" />
                    </c:if>
                    <c:if test="${not empty state}">
                        <c:param name="state" value="${state}" />
                    </c:if>
                    <c:if test="${not empty query}">
                        <c:param name="query" value="${query}" />
                    </c:if>
                </c:url>
                <a class="page-link" href="${pageUrl}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</nav>