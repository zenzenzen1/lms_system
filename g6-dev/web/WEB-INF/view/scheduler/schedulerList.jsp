<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Scheduler List</title>

</head>
<body>
<header>
    <jsp:include page="../common/header.jsp"/>
</header>

<div class="container-fluid p-1">
    <div class="row ">
        <jsp:include page="../common/sidebar.jsp"/>
        <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-md-4">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-10 text-left">
                        <form method="GET" action="${pageContext.request.contextPath}/scheduler/search">
                            <div class="form-row align-items-lg-start flex-column">
                                <div class="row align-items-center mb-3">
                                    <div class="col-lg-3 col-md-4">
                                        <input type="date" class="form-control" id="fromDate" name="fromDate"
                                               placeholder="From Date">
                                    </div>
                                    <div class="col-lg-3 col-md-4">
                                        <input type="date" class="form-control" id="toDate" name="toDate"
                                               placeholder="To Date">
                                    </div>
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
                        <button type="button" id="addScheduler" class="mb-3 btn btn-primary">Add Scheduler</button>
                    </div>
                </div>
            </div>

            <h2>Schedulers</h2>
            <div class="table-responsive-md">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Course Name</th>
                        <th scope="col">Session Date</th>
                        <th scope="col">Slot Time</th>
                        <th scope="col">Room</th>
                        <th scope="col">Teacher</th>
                        <th scope="col">Attendance taken?</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${schedulers}">
                        <tr>
                            <td>${item.course.name}</td>
                            <td>${item.sessions.date}</td>
                            <td>${item.slot.time} (from ${item.slot.startTime} to ${item.slot.endTime})</td>
                            <td>${item.room.name}</td>
                            <td>${item.teacher.fullName}</td>
                            <td>${item.attendance_taken ? 'Yes' : 'No'}</td>
                            <td class="text-nowrap">
                                <button class="btn btn-primary" type="submit" data-id="${item.id}">
                                    Edit
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <jsp:include page="../common/paging.jsp"/>
            </div>
            <div class="modal fade" id="DetailsModel" tabindex="-1" role="dialog"
                 aria-labelledby="Details Scheduler Modal"
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
                                <input type="text" id="schedulerId" hidden/>

                                <div class="form-group">
                                    <label for="trainingDate">Training Date</label>
                                    <input type="date" class="form-control" id="trainingDate"/>
                                </div>

                                <div class="form-group">
                                    <label for="attendanceTaken">Attendance Taken</label>
                                    <input type="checkbox" class="form-control" id="attendanceTaken"/>
                                </div>

                                <div class="form-group">
                                    <label for="teacherList">Teacher List</label>
                                    <select multiple class="form-control" id="teacherList">
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="roomList">Room List</label>
                                    <select multiple class="form-control" id="roomList">
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="slotList">Slot List</label>
                                    <select multiple class="form-control" id="slotList">
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="sessionsList">Sessions List</label>
                                    <select multiple class="form-control" id="sessionsList">
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="courseList">Course List</label>
                                    <select multiple class="form-control" id="courseList">
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
            <%@include file="../common/confirmDialog.jsp" %>
        </main>
    </div>
</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/confirmDialog.jsp"/>
<script src="${pageContext.request.contextPath}/resource/js/scheduler/scheduler.js"></script>
</body>
</html>