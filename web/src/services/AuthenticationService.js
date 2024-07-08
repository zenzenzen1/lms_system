import { API } from "../configurations/configuration"
import httpClient from "../configurations/httpClient"

export const login = async (username, password) => {
    const res = httpClient.post(API.LOGIN, {
        username,
        password
    });
    return res;
}