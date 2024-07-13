import { API } from "../configurations/configuration";
import httpClient from "../configurations/httpClient";
import { getToken } from "./localStorageService";

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