import { AxiosResponse } from "axios"
import { API } from "../configurations/configuration"
import { getToken } from "./localStorageService"
import { ApiResponseType, CourseType } from "src/types/type"
import httpClient from "src/configurations/httpClient"

export const getCoursesByStudentIdSemesterCode = async (studentId: string, semesterCode: string): Promise<ApiResponseType<CourseType[]>> => {
    return httpClient.get(API.GET_COURSES_BY_STUDENTID_SEMESTERCODE, {
        params: {
            studentId: studentId,
            semesterCode: semesterCode,
            slotId: 0
        },
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).
    then(response => {
        return response.data;
    })
    .catch(e => e)
}

export const getCoursesBySemester = async (semesterCode: String): Promise<AxiosResponse<CourseType[]>> => {
    const res = httpClient.get(API.GET_COURSES_BY_SEMESTER + `/${semesterCode}`, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(e => e)
    return res;
}