import { API } from "../configurations/configuration"
import httpClient from "../configurations/httpClient"
import { getToken } from "./localStorageService";

export const insertUser = async (data) => {
  const user = await httpClient.post(API.REGISTER, data).catch((e) => e.message);
  return user;
}

export const getMyInfo = async () => {
  return await httpClient.get(API.MY_INFO, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  });
};

export const getAllUser = async () => {
  const res = await httpClient.get(API.ALL_USER, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  }).catch(e => e);
  return res;
};

export const getAllUserProfile = async () => {
  const res = await httpClient.get(API.USER_PROFILE, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  }).catch(e => e);
  return res;
};

export const updateUserProfile = async (user) => {
  const res = await httpClient.put(API.USER_PROFILE, user, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  }).catch(e => e);
  return res;
};

export const getUserByRole = async (role) => {
  const res = await httpClient.get(`${API.ALL_USER}/${role}`, {
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  }).catch(e => e);
  return res;
};