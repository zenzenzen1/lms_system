import { API } from "../configurations/configuration"
import httpClient from "../configurations/httpClient"
import { getToken } from "./localStorageService"

export const getCoursesBySemester = async(semesterCode) => {
    const res = httpClient.get(API.GET_COURSES_BY_SEMESTER + `/${semesterCode}`, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch(e => e);
    return res;
}