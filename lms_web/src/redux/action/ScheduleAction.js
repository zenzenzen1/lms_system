import { getSchedules, getSemesters } from "../../services/ScheduleService";
import { setSchedules, setSemesters } from "../slice/ScheduleSlice";

export const setSchedulesAction = () => async (dispatch) => {
    const res = await getSchedules();
    dispatch(setSchedules(res.data.content));
}

export const setSemestersAction = () => async (dispatch) => {
    const res = await getSemesters();
    dispatch(setSemesters(res.data.content));
}