// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
// import dotenv from "dotenv";
// dotenv.config();
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
const firebaseConfig = {
    apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
    authDomain: "mern-project-a8fcd.firebaseapp.com",
    projectId: "mern-project-a8fcd",
    storageBucket: "mern-project-a8fcd.appspot.com",
    messagingSenderId: "1039558895334",
    appId: "1:1039558895334:web:582ebf5357a9453cf6bd27"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
