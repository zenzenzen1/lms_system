import React, { useEffect, useState } from 'react';
import Schedule from './Schedule';
import AttendanceStatus from './AttendanceStatus';
import { useDispatch, useSelector } from 'react-redux';
import { setSemestersAction } from '../../../redux/action/ScheduleAction';
import { getAllRooms, getAllSlots, getAllSubjects } from '../../../services/ScheduleService';
import { setRooms, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';

const StudentPage = () => {
    const dispatch = useDispatch();
    const [content, setContent] = useState(null);
    const [semesters, setSemesters] = useState(useSelector(state => state.schedule.semesters));
    if (semesters.length === 0) {
        dispatch(setSemestersAction());

    }
    useEffect(() => {
        (async () => {
            try {
                const _slot = await getAllSlots();
                dispatch(setSlots(_slot.data.result));

                const _subjects = await getAllSubjects();
                dispatch(setSubjects(_subjects.data.result));

                const _rooms = await getAllRooms();
                dispatch(setRooms(_rooms.data.result));

            } catch (error) {
                // setError(error);
                console.log(error);
            }
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    console.log(semesters);
    return (
        <div className=''>
            Student page
            <div className='row'>
                <div className='col-md-2'>
                    Menu
                    <ul className='pl-3'>
                        <li onClick={e => setContent(<Schedule />)}>Schedule</li>
                        <li onClick={e => setContent(<AttendanceStatus />)}>Attendance Status</li>
                    </ul>
                </div>
                <div className='col-md-10'>
                    {content}
                </div>
            </div>
        </div>
    )
}

export default StudentPage
