$(document).ready(function () {

    // Save or Edit
    $('#saveChangesButton').click(function (e) {
        // Prevent the form submission
        e.preventDefault();

        // Prepare the form data
        const formData = {
            'id': $('#id').val(),
            'trainingDate': $('#trainingDate').val(),
            'attendanceTaken': $('#attendanceTaken').is(':checked'),
            'teacherId': $('#teacherList').value,
            'roomId': $('#roomList').value,
            'slotId': $('#slotList').value,
            'sessionsId': $('#sessionsList').value,
            'courseId': $('#courseList').value
        };

        const isUpdate = ($('#id').val() !== '');
        const urlPath = isUpdate ? `${contextPath}/scheduler/${$('#id').val()}` : `${contextPath}/scheduler`;
        const methodType = isUpdate ? 'PUT' : 'POST';

        // AJAX request
        $.ajax({
            url: urlPath,
            type: methodType,
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                window.location.href = `${contextPath}/scheduler/all`;
            },
            error: function (response) {
            }
        });
    });

    $('#editScheduler').click(function (e) {
        e.preventDefault();

        // Fetch the session id from the clicked button's data-id attribute
        const sessionId = $(this).attr('data-id');

        $.ajax({
            url: `${contextPath}/scheduler/${sessionId}`,
            type: 'GET',
            success: function (response) {
                var slotsData = response.slotList;
                var roomsData = response.roomList;
                var sessionsData = response.sessionsList;
                var teacherData = response.teacherList
                var courseData = response.courseList;

                var slots = response.slots;
                var rooms = response.room;
                var teachers = response.teacher;
                var sessions = response.sessions;
                var courses = response.course;

                $('#schedulerId').val(response.id);
                var date = new Date(response.trainingDate);
                var dateString = date.toISOString();
                var localDateTime = dateString.substring(0, dateString.length - 1);
                $('#trainingDate').val(localDateTime);
                $('#attendanceTaken').prop('checked', response.attendanceTaken);
                $('#sessionsList').empty();
                $.each(sessionsData, function (index, session) {
                    $('#sessionsList').append(new Option(session.topicName, session.id, false, session.id === sessions.id));
                });
                $('#teacherList').empty();
                $.each(teacherData, function (index, teacher) {
                    $('#teacherList').append(new Option(teacher.username, teacher.id, false, teachers.id === teacher.id));
                });

                $('#roomList').empty();
                $.each(roomsData, function (index, room) {
                    $('#roomList').append(new Option(room.settingName + room.settingValue, room.id, false, room.id === rooms.id));
                });

                $('#slotList').empty();
                $.each(slotsData, function (index, slot) {
                    $('#slotList').append(new Option(slot.settingName + slot.settingValue, slot.id, false, slot.id === slots.id));
                });

                $('#courseList').empty();
                $.each(courseData, function (index, course) {
                    $('#courseList').append(new Option(course.code, course.id, false, course.id === courses.id));
                });

                $('#DetailsModel').modal('show');
            },
            error: function (error) {
            }
        });
    });

    $('#addScheduler').click(function (e) {
        $.ajax({
            url: `${contextPath}/scheduler/add`,
            type: 'GET',
            success: function (response) {
                var slotsData = response.slotList;
                var roomsData = response.roomList;
                var sessionsData = response.sessionsList;
                var teacherData = response.teacherList
                var courseData = response.courseList;

                $('#sessionsList').empty();
                $.each(sessionsData, function (index, session) {
                    $('#sessionsList').append(new Option(session.topicName, session.id, false, false));
                });
                $('#teacherList').empty();
                $.each(teacherData, function (index, teacher) {
                    $('#teacherList').append(new Option(teacher.username, teacher.id, false, false));
                });

                $('#roomList').empty();
                $.each(roomsData, function (index, room) {
                    $('#roomList').append(new Option(room.settingName + room.settingValue, room.id, false, false));
                });

                $('#slotList').empty();
                $.each(slotsData, function (index, slot) {
                    $('#slotList').append(new Option(slot.settingName + slot.settingValue, slot.id, false, false));
                });

                $('#courseList').empty();
                $.each(courseData, function (index, course) {
                    $('#courseList').append(new Option(course.code, course.id, false, false));
                });

                $('#DetailsModel').modal('show');
            },
            error: function (error) {
            }
        });
    });
});