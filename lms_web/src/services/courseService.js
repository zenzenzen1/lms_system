import { API } from "../configurations/configuration"
import httpClient from "../configurations/httpClient"
import { getToken } from "./localStorageService"

export const getCoursesByStudentIdSemesterCode = async(studentId, semesterCode) => {
    const res = httpClient.get(API.GET_COURSES_BY_STUDENTID_SEMESTERCODE, {
        params: {
            studentId: studentId,
            semesterCode: semesterCode,
            slotId: 0
        },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(e => e)
    return res;
}

export const getCoursesBySemester = async(semesterCode) => {
    const res = httpClient.get(API.GET_COURSES_BY_SEMESTER + `/${semesterCode}`, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(e => e)
    return res;
}