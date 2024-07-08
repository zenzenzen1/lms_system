import { API } from "../configurations/configuration"
import httpClient from "../configurations/httpClient"

export const insertUser = async (data) => {
    const user = await httpClient.post(API.REGISTER, data);
    return user;
}