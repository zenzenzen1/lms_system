import { API } from "../configurations/configuration"
import httpClient from "../configurations/httpClient"
import { getToken } from "./localStorageService";

export const getAttendancesByCourseId = async (courseId, studentId) => {
    const res = httpClient.get(API.GET_ATTENDANCES_BY_COURSEID_STUDENTID, {
        params: {
            courseId: courseId,
            studentId
        },
        headers: {
            Authorization: `Bearer ${getToken()}`,
        }
    }).catch(e => e)

    return res;
}

export const getStudentIdByScheduleId = async (scheduleId) => {
    const res = httpClient.get(API.STUDENTS_BY_SCHEDULEID + `/${scheduleId}`, {
        headers: {
            Authorization: `Bearer ${getToken()}`,
        }
    }).catch(e => e.message);
    
    return res;
}

export const saveAttendances = async (attendances) => {
    const res = httpClient.put(API.SAVE_ALL_ATTENDANCES, {
        attendanceRequests: attendances
    }, {
        headers: {
            Authorization: `Bearer ${getToken()}`,
        }
    }).catch(e => e);
    
    return res;
}