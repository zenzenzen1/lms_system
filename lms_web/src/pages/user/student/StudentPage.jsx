import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setSemestersAction } from '../../../redux/action/ScheduleAction';
import { setRooms, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';
import { verifyToken } from '../../../services/authenticationService';
import { getAllRooms, getAllSlots, getAllSubjects } from '../../../services/ScheduleService';
import Menu from '../common/Menu';
import AttendanceStatus from './AttendanceStatus';
import Schedule from './Schedule';

const StudentPage = () => {
    const dispatch = useDispatch();
    const [content, setContent] = useState(null);
    (async() => {
        verifyToken();
    })();
    useEffect(() => {
        (async () => {
            try {
                const _slot = await getAllSlots();
                dispatch(setSlots(_slot.data.result));

                const _subjects = await getAllSubjects();
                dispatch(setSubjects(_subjects.data.result));

                const _rooms = await getAllRooms();
                dispatch(setRooms(_rooms.data.result));

                dispatch(setSemestersAction());
            } catch (error) {
                // setError(error);
                console.log(error);
            }
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    return (
        <div className=''>
            Student page
            <div className='row'>
                <div className='col-md-2'>
                    Menu
                    <ul className='pl-3 mb-0 flex flex-col gap-1'>
                        <Menu className={"pl-3 mb-0"} />
                        <li onClick={e => setContent(<Schedule />)}>Schedule</li>
                        <li onClick={e => setContent(<AttendanceStatus />)}>Attendance Status</li>
                    </ul>
                </div>
                <div className='col-md-10'>
                    {content ?? <Schedule />}
                </div>
            </div>
        </div>
    )
}

export default StudentPage
