export const KEY_TOKEN = "accessToken";

export const setToken = (token: string) => {
  localStorage.setItem(KEY_TOKEN, token);
};

export const getToken = () => {
  return localStorage.getItem(KEY_TOKEN);
};

export const removeToken = () => {
  return localStorage.removeItem(KEY_TOKEN);
};

export const getKey = (key: string) => {
  return localStorage.getItem(key);
};