import { DataTable } from 'primereact/datatable';
import React, { useEffect, useMemo, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
import { setSchedulesAction } from '../../../redux/action/ScheduleAction';
import { Column } from 'primereact/column';
import { getStudentIdByScheduleId } from '../../../services/attendanceService';

const ScheduleList = () => {
    const { slots, subjects, rooms } = useSelector(state => state.schedule);
    const [schedules, setSchedules] = useState(useSelector(state => state.schedule.schedules))
    const navigate = useNavigate();
    const dispatch = useDispatch();

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
        schedules.map(async (value, index) => {
            const res = await getStudentIdByScheduleId(value.scheduleId);
            const students = res.data.map((value) => value.student);
            console.log(students);
            setSchedules(schedule => schedule.map((value) => value.scheduleId === value.scheduleId ? { ...value, students } : { ...value }))
            // return {
            //     ...value,
            //     students: students
            // }
        })
        // setSchedules(s);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    console.log(schedules);
    const StudentsEnrolledTemplate = (e) => {
        return <>
        <Link>
            {e.students && e.students.map(value => value.fullName)}
        </Link>
            
        </>
    }

    return (
        <div>
            <DataTable value={schedules} tableStyle={{ minWidth: '50rem' }}
                className='text-sm'
                size='small'
                removableSort
            >
                <Column
                    // sortable 
                    header="Semester"
                    body={e => <>{`${e.course && e.course.semester.semesterCode}`}</>}
                // sortFunction={e => {
                //     return e.course.semester.semesterCode;
                // }}
                // field='semester'
                // sortField='semester'
                ></Column>
                <Column sortable field="courseName" header="Course Name" body={(e) => {
                    return (
                        <>
                            <Link >
                                {e.subject && `${e.subject.subjectCode} - ${e.subject.subjectName}`}
                            </Link>
                        </>)
                }}></Column>
                <Column sortable field="trainingDate" header="Training Date" ></Column>
                <Column field="room" header="Room" body={(e) => { return (<>{e.room && `${e.room.roomNumber}`}</>) }}></Column>
                <Column sortable field="slot" header="Slot"
                    // sortFunction={e => { console.log(e); }}
                    body={(e) => { return (<>{e.slot && `${e.slot.slotId}: ${e.slot.startTime} -> ${e.slot.endTime}`}</>) }}
                ></Column>
                <Column header="Students Enrolled" body={StudentsEnrolledTemplate} />
                {/* <Column field="teacher" header="Teacher" body={(e) => { return (<>{`${e.teacher.firstName} ${e.teacher.lastName}`}</>) }}></Column> */}
                {/* <Column field="attendanceTaken" header="Attendance taken?"></Column> */}
            </DataTable>
        </div>
    )
}

export default ScheduleList
