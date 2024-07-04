<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Session List</title>

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
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-10 text-left">
                                <form method="GET" action="${pageContext.request.contextPath}/sessions/search">
                                    <div class="form-row align-items-lg-start flex-column">
                                        <div class="row align-items-center mb-3">
                                            <div class="col-lg-3 col-md-4">
                                                <select class="form-control" name="state">
                                                    <c:forEach var="item" items="${states}">
                                                        <option value="${item}" ${item == state? 'selected' : ''}>${item}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Search</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-2 text-right">
                                <button type="button" id="addSession" class="mb-3 btn btn-primary">Add Session</button>
                            </div>
                        </div>
                    </div>

                    <h2>Scheduler</h2>

                    <div class="table-responsive-md">
                        <table class="table table-hover" id="scrollableTable">
                            <thead>
                                <tr>
                                    <th scope="col">
                                        Topic Name
                                        <a href="#"
                                           class="sort" data-sort="topicName" data-direction="asc" onclick="submitForm(this);">
                                            <span class="fa-stack fa-xs">
                                                <i class="fas fa-sort-up fa-stack-1x up"></i>
                                                <i class="fas fa-sort-down fa-stack-1x down"></i>
                                            </span>
                                        </a>
                                    </th>
                                    <th scope="col">
                                        Duration
                                        <a href="#"
                                           class="sort" data-sort="duration" data-direction="asc" onclick="submitForm(this);">
                                            <span class="fa-stack fa-xs">
                                                <i class="fas fa-sort-up fa-stack-1x up"></i>
                                                <i class="fas fa-sort-down fa-stack-1x down"></i>
                                            </span>
                                        </a>
                                    </th>
                                    <th scope="col">Subject Name</th>
                                    <th scope="col">Subject Description</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <c:forEach var="item" items="${sessions}">
                                <tr>
                                    <td>${item.topicName}</td>
                                    <td>${item.duration}</td>
                                    <td>${item.subjects != null ? item.subjects.name : ''}</td>
                                    <td>${item.subjects != null ? item.subjects.description : ''}</td>


                                    <td class="text-nowrap">
                                        <button id="editSession" class="btn btn-primary" type="submit" data-id="${item.id}">
                                            Edit
                                        </button>
                                        <button id="deleteSession" class="btn btn-primary" type="submit"
                                                data-id="${item.id}">${item.isActive ? 'Deactivate' : 'Activate'}</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <jsp:include page="../common/paging.jsp"/>
                    </div>

                    <div class="modal fade" id="DetailsModel" tabindex="-1" role="dialog"
                         aria-labelledby="Add/Edit Session Modal"
                         aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="h5">Scheduler Details</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form>
                                        <input type="text" id="sessionId" hidden/>

                                        <div class="form-group">
                                            <label for="topicName">Topic Name</label>
                                            <input type="text" class="form-control" id="topicName"
                                                   value="${session.topicName}"/>
                                        </div>

                                        <div class="form-group">
                                            <label for="duration">Duration</label>
                                            <input type="number" class="form-control" id="duration"
                                                   value="${session.duration}"/>
                                        </div>

                                        <div class="form-group">
                                            <label for="isActive">Is Active?</label>
                                            <input type="checkbox" id="isActive" checked="${session.isActive}"/>
                                        </div>

                                        <div class="form-group">
                                            <label for="subjects">Subjects</label>
                                            <select class="form-control" id="subjects" name="subjects">
                                            </select>
                                        </div>

                                    </form>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    <button type="button" id="saveChangesButton" class="btn btn-primary">Save Changes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%@include file="../common/confirmDialog.jsp"%>
                </main>
            </div>
        </div>
        <jsp:include page="../common/footer.jsp"/>
        <script src="${pageContext.request.contextPath}/resource/js/sessions/sessions.js"></script>
    </body>
</html>