import React, { useEffect, useState } from 'react'
import { getStudentIdByScheduleId, saveAttendances } from '../../../services/attendanceService';
import { Button, Table } from 'react-bootstrap';
import { InputText } from 'primereact/inputtext';

// eslint-disable-next-line react/prop-types
const ClassDetail = ({ scheduleId = 0 }) => {
    const [attendance, setAttendance] = useState([]);
    useEffect(() => {
        console.log("class detail");
        (async () => {
            const res = await getStudentIdByScheduleId(scheduleId);
            console.log(res.data);
            setAttendance(res.data);
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
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
    return (
        <div>
            class detail
            <Table>
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

export default ClassDetail
