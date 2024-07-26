import React, { useEffect, useState } from 'react'
import { getStudentIdByScheduleId, saveAttendances } from '../../../services/attendanceService';
import { Button, Table } from 'react-bootstrap';
import { InputText } from 'primereact/inputtext';
import TeacherPage from './TeacherPage';
import { Link, useLocation, useNavigate } from 'react-router-dom';

// eslint-disable-next-line react/prop-types
const ClassDetail = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const scheduleId = location.state.scheduleId;

    const [attendance, setAttendance] = useState([]);
    useEffect(() => {
        (async () => {
            const res = await getStudentIdByScheduleId(scheduleId);
            console.log(res.data);
            setAttendance(res.data);
        })();
    }, [scheduleId]);

    const handleChangeAttendanceStatus = (attendanceId) => {
        setAttendance(attendance => {
            return attendance.map((value) => {
                return value.attendanceId === attendanceId ? { ...value, attendanceStatus: !value.attendanceStatus } : value
            })
        })
    }
    const handleSaveAttendances = async () => {
        const attendancRequests = attendance.map(a => {
            return { attendanceId: a.attendanceId, attendanceNote: a.attendanceNote, attendanceStatus: a.attendanceStatus }
        });
        const res = await saveAttendances(attendancRequests);
        // console.log(res);
        setAttendance(res.data);
    }


    const ClassDetail = () => {
        return (attendance.length === 0 ? <div>Loading data....</div> :
            <div className='pl-3 mt-3'>
                {"<--"}<Link to={"/user/teacher"} >
                    {"Back"}
                </Link>
                <br />
                <h4>
                    class detail
                </h4>
                <Table >
                    <thead>
                        <tr>
                            <th>Image</th>
                            <th>Student Name</th>
                            <th>Attendance</th>
                            <th>Attendance Note</th>
                        </tr>
                    </thead>
                    <tbody>

                        {attendance.map((value, index) => {
                            return (
                                <tr key={index}>
                                    <td>?</td>
                                    <td className=''>
                                        {value.student.fullName}
                                    </td>
                                    <td>
                                        <input name={value.student.id} id={"attend" + value.student.id} onChange={() => handleChangeAttendanceStatus(value.attendanceId)} type='radio'
                                            checked={value.attendanceStatus}
                                            value={true}
                                        />
                                        <label htmlFor={"attend" + value.student.id}>Attend</label>
                                        <input name={value.student.id} className='ml-2' type='radio' id={`absent${value.student.id}`}
                                            checked={!value.attendanceStatus}
                                            onChange={() => handleChangeAttendanceStatus(value.attendanceId)}
                                            value={false}
                                        />
                                        <label htmlFor={`absent${value.student.id}`}>Absent</label>
                                        {value.attendanceStatus}
                                    </td>
                                    <td>
                                        <InputText value={value.attendanceNote ?? ""} className='w-full'
                                            onChange={(e) => {
                                                setAttendance(attendance => attendance.map(a => a.attendanceId === value.attendanceId ? { ...a, attendanceNote: e.target.value } : a))
                                            }}
                                        />
                                    </td>
                                </tr>
                            )
                        })}
                    </tbody>
                </Table>
                <Button onClick={handleSaveAttendances}>Save</Button>
            </div>
        )
    }

    return (
        // <TeacherPage defaultContent={<ClassDetail />} setAttendance={setAttendance}/>
        <ClassDetail />
    )
}

export default ClassDetail
