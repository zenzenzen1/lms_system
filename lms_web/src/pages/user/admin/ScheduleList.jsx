import { DataTable } from 'primereact/datatable';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setSchedulesAction } from '../../../redux/action/ScheduleAction';
import { Column } from 'primereact/column';

const ScheduleList = () => {
    const { slots, subjects, rooms, schedules } = useSelector(state => state.schedule);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    console.log({ slots, subjects, rooms, schedules });
    useEffect(() => {
        // const e = document.getElementById('schedule-table');
        // const t = e.getElementsByTagName('table')[0];
    });
    // (async () => {
    //     const res = await getSchedules();
    //     setSchedules(res.data.content);
    // })();
    useEffect(() => {
        dispatch(setSchedulesAction());
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    // var curr = new Date(); 
    // var first = curr.getDate() - curr.getDay(); 
    // var last = first + 6; 

    // var firstday = new Date(curr.setDate(first)).toUTCString();
    // var lastday = new Date(curr.setDate(last)).toUTCString();
    // console.log({curr, firstday, lastday}, curr.getDay());

    return (
        <div>
            {/* <th scope="col">Course Name</th>
            <th scope="col">Session Date</th>
            <th scope="col">Slot Time</th>
            <th scope="col">Room</th>
            <th scope="col">Teacher</th>
            <th scope="col">Attendance taken?</th> */}
            <DataTable value={schedules} tableStyle={{ minWidth: '50rem' }}
                className='text-sm'
                size='small'
                removableSort
            >
                <Column sortable field="courseName" header="Course Name" body={(e) => { return (<>{`${e.subject.subjectCode} - ${e.subject.subjectName}`}</>) }}></Column>
                <Column sortable field="trainingDate" header="Training Date" ></Column>
                <Column field="room" header="Room" body={(e) => { return (<>{`${e.room.roomNumber}`}</>) }}></Column>
                <Column sortable field="slot" header="Slot"
                    // sortFunction={e => { console.log(e); }}
                    body={(e) => { return (<>{`${e.slot.slotId}: ${e.slot.startTime} -> ${e.slot.endTime}`}</>) }}
                ></Column>
                {/* <Column field="teacher" header="Teacher" body={(e) => { return (<>{`${e.teacher.firstName} ${e.teacher.lastName}`}</>) }}></Column> */}
                {/* <Column field="attendanceTaken" header="Attendance taken?"></Column> */}
            </DataTable>
        </div>
    )
}

export default ScheduleList
