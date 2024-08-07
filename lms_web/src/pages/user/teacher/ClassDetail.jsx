import React, { useEffect, useState } from 'react'
import { getStudentIdByScheduleId, saveAttendances } from '../../../services/attendanceService';
import { Button, Table } from 'react-bootstrap';
import { InputText } from 'primereact/inputtext';
import TeacherPage from './TeacherPage';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { verifyToken } from '../../../services/authenticationService';

// eslint-disable-next-line react/prop-types
const ClassDetail = () => {
    verifyToken();
    const location = useLocation();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const scheduleId = location.state.scheduleId;

    const [attendance, setAttendance] = useState([]);
    const [attendanceRequest, setAttendanceRequest] = useState([]);

    useEffect(() => {
        (async () => {
            const res = await getStudentIdByScheduleId(scheduleId);
            setAttendance(res.data);
            setAttendanceRequest(res.data.map(a => { return { ...a, attendanceStatus: a.attendanceStatus || false } }));
        })();
    }, [scheduleId]);

    const handleChangeAttendanceStatus = (attendanceId) => {
        setAttendanceRequest((attendances) => {
            return attendances.map((value, index) => {
                return value.attendanceId === attendanceId ? { ...value, attendanceStatus: !value.attendanceStatus, isChanged: (attendanceRequest[index].attendanceStatus !== attendance[index].attendanceStatus || attendanceRequest[index].attendanceNote !== attendance[index].attendanceNote) } : value
            })
        })
    }
    console.log({attendance, attendanceRequest});
    
    const handleSaveAttendances = async () => {
        // const attendancRequests = attendance.map(a => {

        //     return { attendanceId: a.attendanceId, attendanceNote: a.attendanceNote, attendanceStatus: a.attendanceStatus }
        // });
        setLoading(true);
        let b = false;
        for (const index in attendanceRequest) {
            if (attendanceRequest[index].attendanceStatus !== attendance[index].attendanceStatus || attendanceRequest[index].attendanceNote !== attendance[index].attendanceNote) {
                b = true;
                break;
            }
        }
        // attendanceRequest.forEach((element, index) => {
        //     const a = attendance[index];
        //     if(element.attendanceStatus !== a.attendanceStatus || element.attendanceNote !== a.attendanceNote){
        //         b = true;
        //         break;
        //     }
        // });

        const attendanceRequests = attendanceRequest.reduce((prev, curr, index, array) => {
            return [...prev, { attendanceId: curr.attendanceId, attendanceNote: curr.attendanceNote, attendanceStatus: curr.attendanceStatus }]
        }, []);

        if (b) {
            const res = await saveAttendances(attendanceRequests);
            setAttendance(res.data);
            setAttendanceRequest(res.data);
            
            alert("Save success");
        }
        else {
            alert("Nothing change");
        }

        // const res = await saveAttendances(attendancRequests);
        // // console.log(res);
        // setAttendance(res.data);


        setLoading(false);
    }

    const [text, setText] = useState("Hello");
    const onChange = (e) => {
        setText(e.target.value)
    }
    const ClassDetail = () => {
        return ;
    }

    return (
        // <TeacherPage defaultContent={<ClassDetail />} setAttendance={setAttendance}/>
        // <ClassDetail />
        (attendanceRequest && attendanceRequest.length === 0 ? <div>Loading data....</div> :
            <>
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

                            {attendanceRequest.map((value, index) => {
                                console.log(value.attendanceStatus);
                                return (
                                    <tr key={index} className='text-green-600'>
                                        <td>{index + 1}</td>
                                        <td>?</td>
                                        <td className=''>
                                            {value.student.fullName}
                                        </td>
                                        <td>
                                            <input id={"attend" + value.student.id} onChange={() => handleChangeAttendanceStatus(value.attendanceId)} type='radio'
                                                checked={value.attendanceStatus}
                                                value={true}
                                            />
                                            <label htmlFor={"attend" + value.student.id}>Attend</label>
                                            <input className='ml-2' type='radio' id={`absent${value.student.id}`}
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
                                                    // setAttendance(attendanc e => attendance.map(a => a.attendanceId === value.attendanceId ? { ...a, attendanceNote: e.target.value } : a))
                                                    setAttendanceRequest(attendanceRequest => attendanceRequest.map(a => a.attendanceId === value.attendanceId ? { ...a, attendanceNote: e.target.value } : a))
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
                    <Button disabled={loading} onClick={handleSaveAttendances}>Save</Button>
                </div>
            </>
        )
    )
}

export default ClassDetail
