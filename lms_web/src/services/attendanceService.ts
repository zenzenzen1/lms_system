import { ApiResponseType, AttendanceType } from "src/types/type";
import { API } from "../configurations/configuration"
import httpClient from "src/configurations/httpClient";
import { getToken } from "./localStorageService";

export const getAttendanceByCourseIdStudentId = async (courseId: number, studentId: string): Promise<ApiResponseType<AttendanceType[]>> => {
    return httpClient.get(API.GET_ATTENDANCES_BY_COURSEID_STUDENTID, {
        params: {
            courseId,
            studentId
        },
        headers: {
            Authorization: `Bearer ${getToken()}`,
        }
    })
        .then(response => {
            return response.data;
        })
        .catch(e => e.message);
}

export const getAttendancesByCourseId = async (courseId, studentId) => {
    const res = httpClient.get(API.GET_ATTENDANCES_BY_COURSEID_STUDENTID_SLOTID, {
        params: {
            courseId: courseId,
            studentId,
            slotId: 0
        },
        headers: {
            Authorization: `Bearer ${getToken()}`,
        }
    }).catch(e => e)

    return res;
}



export const getStudentIdByScheduleId = async (scheduleId: number): Promise<ApiResponseType<AttendanceType[]>> => {
    return httpClient.get(API.STUDENTS_BY_SCHEDULEID + `/${scheduleId}`, {
        headers: {
            Authorization: `Bearer ${getToken()}`,
        }
    })
        .then(response => {
            return response.data;
        })
        .catch(e => e.message);
    

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