import { getSchedules } from "../../services/ScheduleService";
import { setSchedules } from "../slice/ScheduleSlice";

export const setSchedulesAction = () => async (dispatch) => {
    const res = await getSchedules();
    dispatch(setSchedules(res.data.content));
}