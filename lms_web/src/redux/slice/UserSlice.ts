import { createSlice } from "@reduxjs/toolkit";
import { UserResponseType } from "src/types/type";


export const userSlice = createSlice({
    name: "user",
    initialState: {} as UserResponseType,
    reducers: {
        setUser: (state, action: {payload: UserResponseType}) => {
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