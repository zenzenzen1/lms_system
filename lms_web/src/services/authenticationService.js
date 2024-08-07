import { getToken, removeToken, setToken } from "./localStorageService";
import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";

export const googleLogin = async (googleAccount) => {
  const response = await httpClient.post(API.GOOGLE_LOGIN, {
    email: googleAccount.email,
    fullName: googleAccount.displayName,
  }).catch((error) => {
    console.log(error);
    return error;
  });
  return response;
}

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
  clearInterval(localStorage.getItem('checkTokenInterval'));
  localStorage.clear();
  // navigate('/user/login');
  window.location.href = '/user/login';
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
  if (!getToken()) return false;
  const response = await introspectToken();
  return response.data?.result?.valid;
};

export const verifyToken = async () => {
  console.log("Authenticating token...");
  isValidToken()
    .then((valid) => {
      if (!valid) {
        logOut();
      }
    })
    .catch((error) => {
      console.log(error);
      logOut();
    })
    ;
};
