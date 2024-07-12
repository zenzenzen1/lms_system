import { createSlice } from "@reduxjs/toolkit";

type userType = {
    id: string,
    username: string,
    fullName: string,
    email: string,
    phoneNumber: string,
    dob: string,
    userId: string,
    role: string[]
}

const initialState: userType = {
    id: "",
    fullName: "",
    role: [],
    email: "",
    phoneNumber: "",
    dob: "",
    userId: "",
    username: ""
}

export const userSlice = createSlice({
    name: "user",
    initialState: initialState,
    reducers: {
        setUser: (state, action: {payload: userType}) => {
            // state.id = action.payload.id;
            // state.userId = action.payload.userId;
            // state.fullName = action.payload.fullName;
            // state.role = action.payload.role;
            // state.email = action.payload.email;
            // state.phoneNumber = action.payload.phoneNumber;
            // state.dob = action.payload.dob;
            
            return action.payload;   
        }
    }
})

export const setUser = userSlice.actions.setUser;