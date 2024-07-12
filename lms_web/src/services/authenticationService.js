import { getToken, removeToken, setToken } from "./localStorageService";
import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";

export const login = async (username, password) => {
  const response = await httpClient.post(API.LOGIN, {
    username: username,
    password: password,
  }).catch((error) => {
    console.log(error);
    return error.response;
  });
  console.log(response);
  setToken(response.data?.result?.token);

  return response;
};

export const logOut = () => {
  removeToken();
};

export const isAuthenticated = () => {
  return getToken();
};

export const introspectToken = async () => {
  return await httpClient.post(API.INTROSPECT, {
    token: getToken(),
  }).catch(error => error);
};

export const isValidToken = async () => {
  const response = await introspectToken();
  return response.data?.result?.valid;
};

export const verifyToken = async () => {
  const _isValidToken = await isValidToken();
  if (!_isValidToken) {
    removeToken();
  }
};