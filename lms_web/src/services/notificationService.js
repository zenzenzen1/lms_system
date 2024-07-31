import { API } from "../configurations/configuration";
import httpClient from "../configurations/httpClient";
import { getToken } from "./localStorageService";

export const getNotificationsByUserId = async (userId) => {
    const res = await httpClient.get(API.GET_NOTIFICATIONS_BY_USER_ID + `/${userId}`, {
        headers: {
            Authorization: `Bearer ${getToken()}`
        }
    }).catch((e) => e);
    return res;
}