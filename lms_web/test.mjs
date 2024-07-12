import { login } from "./src/services/authenticationService.js";

const res = await login("student1", "student1");

console.log(res);

