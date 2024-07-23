import { createSlice } from "@reduxjs/toolkit";

type scheduleType = {
    rooms: object[],
    subjects: object[],
    slots: object[],
    schedules: object[],
    semesters: object[]

}

const initialState: scheduleType = {
    rooms: [],
    subjects: [],
    slots: [],
    schedules: [],
    semesters: []
};

export const scheduleSlice = createSlice({
    name: "schedule",
    initialState: initialState,
    reducers: {
        setSemesters: (state, action) => {
            return {
                ...state, 
                semesters: action.payload
            }
        },
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

export const { setRooms, setSubjects, setSlots, setSchedules, setSemesters } = scheduleSlice.actions;