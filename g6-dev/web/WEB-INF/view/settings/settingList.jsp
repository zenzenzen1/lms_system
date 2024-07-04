<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Setting List</title>

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
                        <form method="GET" action="${pageContext.request.contextPath}/settings/search" id="sortForm">
                            <input type="hidden" id="sort" name="sort" value=""/>
                            <input type="hidden" id="direction" name="direction" value=""/>
                            <div class="form-row align-items-lg-start flex-column">
                                <div class="row align-items-center mb-3">
                                    <div class="col-lg-8 col-md-8">
                                        <input type="text" name="query" placeholder="Search..."
                                               class="form-control search-input">
                                    </div>
                                    <div class="col-lg-4 col-md-4">
                                        <button type="submit" class="btn btn-primary">Search</button>
                                    </div>
                                </div>
                                <div class="row align-items-baseline mb-3">
                                    <div class="col-md-6">
                                        <select class="form-control form-select-sm input-sm mb-2"
                                                aria-label=".form-select-sm setting" name="type" id="type">
                                            <c:forEach var="item" items="${settingsList}">
                                                <option value="${item}" ${item == type? 'selected' : ''}>${item}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-md-6">
                                        <select class="form-control form-select-sm input-sm mb-2"
                                                aria-label=".form-select-sm state" name="state" id="state">
                                            <c:forEach var="item" items="${states}">
                                                <option value="${item}" ${item == state? 'selected' : ''}>${item}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                    <div class="col-md-2 text-right">
                        <button type="button" id="addSetting" class="mb-3 btn btn-primary">Add Setting</button>
                    </div>
                </div>
            </div>

            <h2>Settings</h2>

            <div class="table-responsive-md">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">Setting Type
                            <a href="#"
                               class="sort" data-sort="type" data-direction="asc" onclick="submitForm(this);">
                                <span class="fa-stack fa-xs">
                                    <i class="fas fa-sort-up fa-stack-1x up"></i>
                                    <i class="fas fa-sort-down fa-stack-1x down"></i>
                                </span>
                            </a>
                        </th>
                        <th scope="col">Setting Name
                            <a href="#"
                               class="sort" data-sort="setting_name" data-direction="asc" onclick="submitForm(this);">
                                <span class="fa-stack fa-xs">
                                    <i class="fas fa-sort-up fa-stack-1x up"></i>
                                    <i class="fas fa-sort-down fa-stack-1x down"></i>
                                </span>
                            </a>
                        </th>
                        <th scope="col">
                            Setting Value
                            <a href="#"
                               class="sort" data-sort="setting_value" data-direction="asc" onclick="submitForm(this);">
                                <span class="fa-stack fa-xs">
                                    <i class="fas fa-sort-up fa-stack-1x up"></i>
                                    <i class="fas fa-sort-down fa-stack-1x down"></i>
                                </span>
                            </a>
                        </th>
                        <th scope="col">
                            Order
                            <a href="#" class="sort" data-sort="display_order" data-direction="asc" onclick="submitForm(this);">
                                <span class="fa-stack fa-xs">
                                    <i class="fas fa-sort-up fa-stack-1x up"></i>
                                    <i class="fas fa-sort-down fa-stack-1x down"></i>
                                </span>
                            </a>
                        </th>
                        <th scope="col">Action</th>
                    </tr>
                    </thead>
                    <c:forEach var="item" items="${settings}">
                        <tr>
                            <td>${item.type}</td>
                            <td>${item.settingName}</td>
                            <td>${item.settingValue}</td>
                            <td>${item.displayOrder}</td>
                            <td class="text-nowrap">
                                <button id="editSetting" class="btn btn-primary" type="submit" data-id="${item.id}">
                                    Edit
                                </button>
                                <button id="deleteSetting" class="btn btn-primary" type="submit"
                                        data-id="${item.id}">${item.isActive ? 'Deactivate' : 'Activate'}</button>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <jsp:include page="../common/paging.jsp"/>
            </div>

            <div class="modal fade" id="DetailsModel" tabindex="-1" role="dialog"
                 aria-labelledby="Add/Edit Setting Modal"
                 aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="h5">Setting Details</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <form>
                                <input type="text" id="settingId" hidden/>

                                <div class="form-group">
                                    <label for="settingName">Setting Name</label>
                                    <input type="text" class="form-control" id="settingName"
                                           value="${setting.settingName}"/>
                                </div>

                                <div class="form-group">
                                    <label for="settingValue">Setting Value</label>
                                    <input type="text" class="form-control" id="settingValue"
                                           value="${setting.settingValue}"/>
                                </div>

                                <div class="form-group">
                                    <label for="settingType">Setting Type</label>
                                    <select class="form-control" id="settingType">
                                        <c:forEach var="item" items="${settingsList}">
                                            <option value="${item}" ${type == item ? 'selected' : ''}>${item}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label for="isActive">Is Active?</label>
                                    <input type="checkbox" id="isActive" checked="${setting.isActive}"/>
                                </div>

                                <div class="form-group" id="roleListForm">
                                    <label for="roleList">Role List</label>
                                    <select class="form-control" id="roleList">
                                    </select>
                                </div>

                                <div class="form-group" id="permissionListForm">
                                    <label for="permissionsList">Permissions List</label>
                                    <select class="form-control" id="permissionsList" multiple>
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
        </main>

    </div>
</div>
<jsp:include page="../common/footer.jsp"/>
<jsp:include page="../common/confirmDialog.jsp"/>
<script src="${pageContext.request.contextPath}/resource/js/settings/settings.js"></script>
</body>
</html>