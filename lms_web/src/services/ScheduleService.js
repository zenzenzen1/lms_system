import { API } from "../configurations/configuration";
import httpClient from "../configurations/httpClient";
import { getToken } from "./localStorageService";

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