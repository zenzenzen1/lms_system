$(document).ready(function () {

    const $sidebarNav = $('#sidebarNav');
    const $menuText = $(".menu-text");
    const $tooltipToggle = $('[data-toggle="tooltip"]');
    const $settingName = $('#settingName');
    const $settingValue = $('#settingValue');
    const $settingType = $('#settingType');
    const $roleId = $('#roleId');
    const $permissionsList = $('#permissionsList');
    const $settingId = $('#settingId');
    const $isActive = $('#isActive');
    const $createdBy = $('#createdBy');
    const $editedBy = $('#editedBy');
    const $parentId = $('#parentId');
    const $roleListForm = $('#roleListForm');
    const $permissionListForm = $('#permissionListForm');
    const $roleList = $('#roleList');
    const $settingTypes = $('#settingTypes');
    const $permissionsListSelect = $('#permissionsList');
    const $DetailsModel = $('#DetailsModel');
    const $confirmButton = $('#confirmButton');
    const $confirmationModal = $('#confirmationModal');
    const $addSettingModal = $('#AddSettingModal');

    const contextPath = '';

    $('#sidebarToggle').click(function () {
        $sidebarNav.toggleClass("collapsed");
        if ($sidebarNav.hasClass("collapsed")) {
            $menuText.hide();
            $tooltipToggle.tooltip();
        } else {
            $menuText.show();
            $tooltipToggle.tooltip('dispose');
        }
    });

    $('#saveChangesButton').click(function (e) {
        e.preventDefault();

        $('input,select').removeClass('is-invalid');
        let isValid = true;

        if ($settingName.val() === '') {
            $settingName.addClass('is-invalid');
            isValid = false;
        }
        if ($settingValue.val() === '') {
            $settingValue.addClass('is-invalid');
            isValid = false;
        }
        if ($settingType.val() === '') {
            $settingType.addClass('is-invalid');
            isValid = false;
        }

        if ($settingType.val() === 'ROLE') {

            var roleList = $roleId[0];
            var selectedRole = roleList.options[roleList.selectedIndex].value;

            var permissionsList = $permissionsList[0];
            var selectedPermissions = Array.from(permissionsList.selectedOptions).map(option => option.value);

            if (selectedRole === '') {
                alert('Please select a role');
                return false;
            }
            if (selectedPermissions.length === 0) {
                alert('Please select at least one permission');
                return false;
            }
        }

        if (!isValid) {
            alert('All fields must be filled out');
            return false;
        }

        const formData = {
            'settingName': $settingName.val(),
            'settingValue': $settingValue.val(),
            'type': $settingType.val(),
            'isActive': $isActive.is(':checked'),
            'createdBy': $createdBy.val(),
            'editedBy': $editedBy.val(),
            'parentId': $parentId.val()
        };
        if (formData.type === 'ROLE') {
            formData.permissions = selectedPermissions
        }
        const settingId = $settingId.val();
        console.log("Setting ID: ", settingId);
        if (settingId) {
            $.ajax({
                url: `${contextPath}/settings/${settingId}`,
                type: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (response) {
                    window.location.href = `${contextPath}/settings/all`;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    const errorText = encodeURIComponent(errorThrown);
                    window.location.href = 'errorDialog.jsp?error=' + errorText;
                }
            });
        } else {
            $.ajax({
                url: `${contextPath}/settings`,
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (response) {
                    toastr.success('Your operation was successful.', 'Success');
                    window.location.href = `${contextPath}/settings/all`;
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    const errorText = encodeURIComponent(errorThrown);
                    window.location.href = 'errorDialog.jsp?error=' + errorText;
                }
            });
        }
    });

    $("#addSetting").click(function (e) {
        e.preventDefault();
        $.ajax({
            url: `${contextPath}/settings/add`,
            type: 'GET',
            success: function (response) {
                var setting = response.setting;
                var settingTypes = response.settingTypes;
                var roleList = setting.roles;
                var permissionsList = setting.permissions;

                $settingName.val(setting.settingName);
                $settingValue.val(setting.settingValue);

                $settingType.empty();
                $.each(settingTypes, function (index, type) {
                    $settingType.append(new Option(type, type, false, false));
                });

                if (setting.type === 'PERMISSION') {
                    $roleListForm.show();
                    $permissionListForm.hide();

                    $roleList.empty();
                    $.each(roleList, function (index, role) {
                        $roleList.append(new Option(role.settingValue, role, false, false));
                    });
                } else if (setting.type === 'ROLE') {
                    $roleListForm.hide();
                    $permissionListForm.show();

                    $permissionsListSelect.empty();
                    $.each(permissionsList, function (key, value) {
                        let settingDetails = JSON.parse(key);
                        $permissionsListSelect.append(new Option(settingDetails.settingValue, settingDetails, value, value));
                    });
                }

                $settingType.on('changed.bs.select', function () {
                    $settingType.selectpicker('refresh');
                });

                $isActive.prop('checked', setting.isActive);
                $DetailsModel.modal('show');
            },
            error: function (error) {
            }
        });
    });

    $('#editSetting').click(function (e) {
        e.preventDefault();

        const settingId = $(this).attr('data-id');
        const type = $('#type').val();

        $.ajax({
            url: `${contextPath}/settings/${settingId}?type=${type}`,
            type: 'GET',
            success: function (response) {
                const setting = response.setting;
                const settingTypes = response.settingTypes;
                var roleList = setting.roles;
                var permissionsList = setting.permissions;

                $settingId.val(setting.id);
                $settingName.val(setting.settingName);
                $settingValue.val(setting.settingValue);

                // clear previous options
                $settingTypes.empty();
                $.each(settingTypes, function (index, type) {
                    $settingTypes.append(new Option(type, type, false, setting.type === type));
                });

                if (setting.type === 'PERMISSION') {
                    $roleListForm.show();
                    $permissionListForm.hide();

                    $roleList.empty();
                    $.each(roleList, function (index, role) {
                        $roleList.append(new Option(role.settingValue, role, false, false));
                    });
                } else if (setting.type === 'ROLE') {
                    $roleListForm.hide();
                    $permissionListForm.show();

                    $permissionsListSelect.empty();
                    $.each(permissionsList, function (key, value) {
                        let settingDetails = JSON.parse(key);
                        $permissionsListSelect.append(new Option(settingDetails.settingValue, settingDetails, value, value));
                    });
                }

                $settingTypes.on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
                    $settingTypes.selectpicker('refresh');
                });

                $isActive.prop('checked', setting.isActive);
                $addSettingModal.modal('show');
            },
            error: function (error) {
                const errorText = encodeURIComponent(error);
                window.location.href = 'errorDialog.jsp?error=' + errorText;
            }
        });
    });

    const selectSetting = function (element) {
        // Fetch the setting id from the clicked button's data-id attribute
        var settingId = $(element).attr('data-id');
        var deleteUrl = `${contextPath}/settings/` + settingId;

        // Show the confirmation dialog
        $confirmationModal.modal('show');

        // Set a one-time 'confirm' event handler
        $confirmButton.one('click', function () {
            $confirmationModal.modal('hide');
            $.ajax({
                url: deleteUrl,
                type: 'DELETE',
                success: function () {
                    toastr.success('Your operation was successful.', 'Success');
                    const buttonSelector = '*[id="deleteSetting"][data-id="' + settingId + '"]';
                    const $buttonSelector = $(buttonSelector);
                    const currentStatus = $buttonSelector.text().trim();
                    const newStatus = (currentStatus == 'Activate') ? 'Deactivate' : 'Activate';
                    $buttonSelector.text(newStatus);
                },
                error: function (request, error) {
                    const errorText = encodeURIComponent(error);
                    window.location.href = 'errorDialog.jsp?error=' + errorText;
                }
            });
        });

        $confirmationModal.one('hidden.bs.modal', function () {
            $confirmButton.off('click');
        });
    };

    $('#deleteSetting').on('click', function (event) {
        event.preventDefault();
        selectSetting(this);
    });
});