import { createSlice } from "@reduxjs/toolkit";

type scheduleType = {
    rooms: object[],
    subjects: object[],
    slots: object[],
    schedules: object[]

}

const initialState: scheduleType = {
    rooms: [],
    subjects: [],
    slots: [],
    schedules: []
};

export const scheduleSlice = createSlice({
    name: "schedule",
    initialState: initialState,
    reducers: {
        setSchedules: (state, action) => {
            return {
                ...state,
                schedules: action.payload
            }
        },
        setRooms: (state, action) => {
            return {
                ...state,
                rooms: action.payload
            }
        },
        setSubjects: (state, action) => {
            return {
                ...state,
                subjects: action.payload
            }
        },
        setSlots: (state, action) => {
            return {
                ...state,
                slots: action.payload
            }
        }
    }
})

export const { setRooms, setSubjects, setSlots, setSchedules } = scheduleSlice.actions;