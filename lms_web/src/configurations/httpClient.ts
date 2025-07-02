import axios, { InternalAxiosRequestConfig } from "axios";
import { verifyToken } from "src/services/authenticationService";
import { CONFIG } from "./configuration";

const httpClient = axios.create({
    baseURL: CONFIG.API_GATEWAY,
    timeout: 30000,
    headers: {
        "Content-Type": "application/json",
    },
});

// httpClient.interceptors.request.use(async (config: InternalAxiosRequestConfig): Promise<InternalAxiosRequestConfig> => {
//     try {
//         await verifyToken();
//         return config;
//     } catch (error) {
//         console.error('Token validation failed:', error);
//         // Optional: Redirect to login or cancel request
//         window.location.href = '/user/login';
//         throw new axios.Cancel('Token is invalid or could not be refreshed.');
//     }
// },
//     (error) => Promise.reject(error)
// );

export default httpClient;
