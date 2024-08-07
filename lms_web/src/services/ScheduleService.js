import { API } from "../configurations/configuration";
import httpClient from "../configurations/httpClient";
import { getToken } from "./localStorageService";

// export const getScheduleByCourseIdSlotIdRoomNumberSemesterCode = async(courseId, slotId, roomId, semesterCode) => {
//     const res = httpClient.get(API.GET_SCHEDULES_BY_COURSEID_SLOTID_ROOMID_SEMESTERCODE, {
//         params: {
//             courseId,
//             slotId,
//             roomId,
//             semesterCode
//         },
//         headers: {
//             Authorization: `Bearer ${getToken()}`,
//         }
//     }).catch(e => e)

//     return res;
// };

export const getScheduleByCourseIdSlotIdRoomNumberSemesterCode = async () => {
    const res = httpClient.get(API.GET_SCHEDULES_BY_COURSEID_SLOTID_ROOMID_SEMESTERCODE, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(e => e)

    return res;
};

export const importFromExcel = async (file) => {
    const formData = new FormData();
    formData.append('files', file);
    const res = await httpClient.post(API.IMPORT_FROM_EXCEL_SCHEDULES, formData, {
        headers: {
            'content-type': 'application/vnd.ms-excel',
            Authorization: `Bearer ${getToken()}`
        }
    });

    return res;
}

export const exportToExcelSchedules = async () => {
    const res = await httpClient.get(API.EXPORT_TO_EXCEL_SCHEDULES, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(e => e);
    return res;
}

export const getSemesters = async () => {
    const res = httpClient.get(API.ALL_SEMESTERS, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(e => e);
    return res;
}

export const saveSchedule = async (scheduleRequest) => {
    const res = await httpClient.post(API.SCHEDULE, scheduleRequest, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    })
    return res;
}

export const getAttendancesByTeacherId = async (teacherId, startDate, endDate) => {
    const res = await httpClient.get(API.ATTENDANCES_BY_TEACHERID + `/${teacherId}`, {
        params: {
            startDate: startDate,
            endDate: endDate
        },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch((e) => e);

    return res;
}

export const getSchedulesByStudentId = async (studentId, startDate, endDate) => {
    const res = await httpClient.get(API.SCHEDULE_BY_STUDENTID + `/${studentId}`, {
        params: {
            startDate: startDate,
            endDate: endDate
        },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch((e) => e);

    return res;
}

export const getSchedules = async () => {
    const res = await httpClient.get(API.ALL_SCHEDULE, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch((e) => e);
    return res;
};

export const getAllRooms = async () => {
    const res = await httpClient.get(API.ROOM, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch((e) => e);
    return res;
}

export const getAllSlots = async () => {
    const res = await httpClient.get(API.SLOTS, {
        headers: {
            Authorization: `Bearer ${getToken()}`,
        }
    }).catch(error => error);
    return res;
};

export const getAllSubjects = async () => {
    const res = await httpClient.get(API.SUBJECTS, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(error => error);

    return res;
}