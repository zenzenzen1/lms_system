$(document).ready(function () {
    $('#saveChangesButton').click(function (e) {
        e.preventDefault();

        $('input,select').removeClass('is-invalid');
        let isValid = true;
        if ($('#topicName').val() === '') {
            $('#topicName').addClass('is-invalid'); // Change the '#day' to '#topicName' to validate the 'topicName' field
            isValid = false;
        }
        if ($('#duration').val() === '') {
            $('#duration').addClass('is-invalid'); // Change the '#description' to '#duration' to validate the 'duration' field
            isValid = false;
        }
        if ($('#subjects').val() === '') {
            toastr.fail('Please choose Subjects', 'Error');
            isValid = false;
        }

        // Form validation
        if (!isValid) {
            toastr.fail('All fields must be filled out', 'Error');
            return false;
        }

        // Prepare form data
        const formData = {
            'topicName': $('#topicName').val(),
            'duration': $('#duration').val(),
            'isActive': $('#isActive').is(':checked'),
            'subjectId': $('#subjects').val()
        };

        const sessionId = $('#sessionId').val();

        if (sessionId) {
            $.ajax({
                url: `${contextPath}/sessions/${sessionId}`,
                type: 'PUT',
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (response) {
                    toastr.success('Your operation was successful.', 'Success');
                    window.location.href = `${contextPath}/sessions/all`;
                },
                error: function (errorThrown) {
                    const errorText = encodeURIComponent(errorThrown);
                    window.location.href = 'errorDialog.jsp?error=' + errorText;
                }
            });
        } else {
            $.ajax({
                url: `${contextPath}/sessions`,
                type: 'POST',
                contentType: "application/json",
                data: JSON.stringify(formData),
                success: function (response) {
                    window.location.href = `${contextPath}/sessions/all`;
                },
                error: function (errorThrown) {
                    const errorText = encodeURIComponent(errorThrown);
                    window.location.href = 'errorDialog.jsp?error=' + errorText;
                }
            });
        }
    });

    $('#editSession').click(function (e) {
        e.preventDefault();

        // Fetch the session id from the clicked button's data-id attribute
        const sessionId = $(this).attr('data-id');
        $.ajax({
            url: `${contextPath}/sessions/${sessionId}`,
            type: 'GET',
            success: function (response) {
                var subjectList = response.subjectsList;
                const subjects = response.subjects;
                $('#sessionId').val(response.id);
                $('#duration').val(response.duration);
                $('#topicName').val(response.topicName);
                $('#isActive').prop('checked', response.isActive);

                $('#subjects').empty();
                $.each(subjectList, function (index, subject) {
                    $('#subjects').append(new Option(subject.name, subject.id, false, subjects.id === subject.id));
                });
                $('#DetailsModel').modal('show');
            },
            error: function (error) {
            }
        });
    });

    $('#addSession').click(function (e) {
        e.preventDefault();

        // Fetch the session id from the clicked button's data-id attribute
        const sessionId = $(this).attr('data-id');

        $.ajax({
            url: `${contextPath}/sessions/add`,
            type: 'GET',
            success: function (response) {
                var subjectList = response.subjectsList;

                $('#sessionId').val(response.id);
                $('#duration').val(response.duration);
                $('#isActive').prop('checked', response.isActive);

                $('#subjects').empty();
                $.each(subjectList, function (index, subject) {
                    $('#subjects').append(new Option(subject.name, subject.id, false, false));
                });

                $('#DetailsModel').modal('show');
            },
            error: function (error) {
            }
        });
    });

    $('#deleteSession').click(function (e) {
        e.preventDefault(); // Prevent the default form submission

        var itemId = $(this).data('id');
        // Show the confirmation dialog
        $('#confirmationModal').modal('show');

        // Set a one-time 'confirm' event handler
        $('#confirmButton').one('click', function () {
            $('#confirmationModal').modal('hide');
            $.ajax({
                url: `${contextPath}/sessions/` + itemId,
                type: 'DELETE',
                success: function () {
                    toastr.success('Your operation was successful.', 'Success');
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    const errorText = encodeURIComponent(errorThrown);
                    window.location.href = 'errorDialog.jsp?error=' + errorText;
                }
            });
        });

        $('#confirmationModal').one('hidden.bs.modal', function () {
            $('#confirmButton').off('click');
        });
    });
});