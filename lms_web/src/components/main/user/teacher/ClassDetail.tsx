import { some } from 'lodash';
import { InputText } from 'primereact/inputtext';
import { useEffect, useState } from 'react';
import { Button, Table } from 'react-bootstrap';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { toast, ToastContainer } from 'react-toastify';
import { AttendanceType } from 'src/types/type';
import { getStudentIdByScheduleId, saveAttendances } from '../../../../services/attendanceService';
import { verifyToken } from '../../../../services/authenticationService';

//
const ClassDetail = () => {
    verifyToken();
    const location = useLocation();
    const navigate = useNavigate();
    const [loading, setLoading] = useState({ fetchData: false, save: false });
    const scheduleId = location.state.scheduleId;
    if (!scheduleId) {
        navigate(-1);
    }

    const [attendance, setAttendance] = useState<AttendanceType[]>([]);
    const [attendanceRequests, setAttendanceRequests] = useState<AttendanceType[]>([]);

    useEffect(() => {
        setLoading((loading) => ({ ...loading, fetchData: true }));
        (async () => {
            const res = await getStudentIdByScheduleId(scheduleId);
            console.log(res);
            setAttendance(res.result);
            setAttendanceRequests(res.result.map(a => { return { ...a, attendanceStatus: a.attendanceStatus || false } }));
            setLoading((loading) => ({ ...loading, fetchData: false }));
        })();
    }, [scheduleId]);

    const handleChangeAttendanceStatus = (attendanceId: number) => {
        setAttendanceRequests((attendances) => {
            return attendances.map((value, index) => {
                return value.attendanceId === attendanceId ? {
                    ...value, attendanceStatus: !value.attendanceStatus,
                    // isChanged: (attendanceRequests[index].attendanceStatus !== attendance[index].attendanceStatus || attendanceRequests[index].attendanceNote !== attendance[index].attendanceNote)
                } : value
            })
        })
    }

    const handleSaveAttendances = async () => {
        setLoading({ ...loading, save: true });
        let isRequestChanged = some(attendanceRequests, (value, index) => value.attendanceStatus !== attendance[index].attendanceStatus || value.attendanceNote !== attendance[index].attendanceNote);

        // const _attendanceRequests = attendanceRequests.reduce((prev, curr) => {
        //     return [...prev, { attendanceId: curr.attendanceId, attendanceNote: curr.attendanceNote, attendanceStatus: curr.attendanceStatus }]
        // }, []);

        if (isRequestChanged) {
            const res = await saveAttendances(attendanceRequests);
            setAttendance(res.data);
            setAttendanceRequests(res.data);

            toast.success("Save success", { autoClose: 4000 });
        }
        else {
            toast.warn("Nothing changed", { autoClose: 4000 });

        }

        // const res = await saveAttendances(attendancRequests);
        // // console.log(res);
        // setAttendance(res.data);


        setLoading({ ...loading, save: false });
    }

    return (
        // <TeacherPage defaultContent={<ClassDetail />} setAttendance={setAttendance}/>
        // <ClassDetail />
        (loading.fetchData ? <div>Loading data....</div> :
            <>
                <ToastContainer draggable />
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
                                <th>Index</th>
                                <th>Image</th>
                                <th>Student Name</th>
                                <th>Attendance</th>
                                <th>Attendance Note</th>
                                {/* <th className='w-fit pl-0 pr-0 text-center'>Modified?</th> */}
                            </tr>
                        </thead>
                        <tbody>

                            {attendanceRequests.map((value, index) => {
                                return (
                                    <tr key={index} >
                                        <td>{index + 1}</td>
                                        <td>?</td>
                                        <td className=''>
                                            {value.student.fullName}
                                        </td>
                                        <td>
                                            <div className='flex gap-3'>
                                                <div>
                                                    <input id={"attend" + value.student.id} onChange={() => handleChangeAttendanceStatus(value.attendanceId)} type='radio'
                                                        checked={value.attendanceStatus}

                                                    />
                                                    <label htmlFor={"attend" + value.student.id}>Attend</label>
                                                </div>
                                                <div>
                                                    <input className='ml-2' type='radio' id={`absent${value.student.id}`}
                                                        checked={!value.attendanceStatus}
                                                        onChange={() => handleChangeAttendanceStatus(value.attendanceId)}

                                                    />
                                                    <label htmlFor={`absent${value.student.id}`}>Absent</label>
                                                </div>
                                            </div>
                                            {value.attendanceStatus}
                                        </td>
                                        <td>
                                            <InputText value={value.attendanceNote ?? ""} className='w-full'
                                                onChange={(e) => {
                                                    // setAttendance(attendanc e => attendance.map(a => a.attendanceId === value.attendanceId ? { ...a, attendanceNote: e.target.value } : a))
                                                    setAttendanceRequests(attendanceRequest => attendanceRequest.map(a => a.attendanceId === value.attendanceId ? { ...a, attendanceNote: e.target.value } : a))
                                                }}
                                            />
                                        </td>
                                        {/* <td className='pl-0 pr-0 text-center' style={{width: "1px"}}>
                                            {value.isChanged ? <span className='text-green-600'>v</span> : <span className='text-red-600'>x</span>}
                                        </td> */}
                                    </tr>
                                )
                            })}
                        </tbody>
                    </Table>
                    <Button disabled={loading.save} onClick={handleSaveAttendances}>Save</Button>
                </div>
            </>
        )
    )
}

export default ClassDetail
