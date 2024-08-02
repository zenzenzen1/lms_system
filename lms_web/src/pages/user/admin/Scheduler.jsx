import React, { useEffect, useMemo, useRef, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setSchedulesAction, setSemestersAction } from '../../../redux/action/ScheduleAction';
import { setRooms, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';
import { getAllRooms, getAllSlots, getAllSubjects, getSchedules, saveSchedule } from '../../../services/ScheduleService';
import { getAllUserAndUserProfile } from '../../../services/UserService';
import ScheduleList from './ScheduleList';
import { Toast } from 'primereact/toast';
import { Button as PrimereactButton } from 'primereact/button';
import { getCoursesBySemester } from '../../../services/courseService';
import { getStudentIdByScheduleId } from '../../../services/attendanceService';

const Scheduler = () => {
    const [show, setShow] = useState(false);
    // const [slots, setSlots] = useState([]);
    // const [subjects, setSubjects] = useState([]);
    // const [rooms, setRooms] = useState([]);
    const [schedule, setSchedule] = useState([]);
    const [schedulesDisplayed, setScheduleDisplayed] = useState([]);
    const [error, setError] = useState("");
    const [students, setStudents] = useState([]);
    const [courses, setCourses] = useState([]);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSave = async () => {
        console.log(scheduleRequest);
        const _confirm = confirm("Save?");
        if (_confirm) {
            // save schedule
            saveSchedule(scheduleRequest).then(res => {
                console.log(res);
                // dispatch(setSchedulesAction())
                return (res.data)
            })
                .then(schedules => {
                    // schedules.map(async (value, index) => {
                    //     const res = await getStudentIdByScheduleId(value.scheduleId);
                    //     const students = res.data.map((value) => value.student);
                    //     console.log({ value, students });
                    //     setSchedule(schedules => [...schedules, { ...value, students }])
                    //     return {
                    //         ...value,
                    //         students: students
                    //     }
                    // })
                    setSchedule(schedules);
                    alert("success!");
                })
                .catch(e => {
                    alert("error " + e.message);
                })
        }
    }

    // schedules.map(async (value, index) => {
    //     const res = await getStudentIdByScheduleId(value.scheduleId);
    //     const students = res.data.map((value) => value.student);
    //     console.log(students);
    //     setScheduleList(schedule => schedule.map((value) => value.scheduleId === value.scheduleId ? { ...value, students } : { ...value }))

    const { slots, subjects, rooms, semesters } = useSelector(state => state.schedule);
    useEffect(() => {
        (async () => {
            const schedules = await getSchedules().then(res => {
                return (res.data.content)
            })
                .then(schedules => {
                    return (
                        // schedules.map(async (value, index) => {
                        //     const res = await getStudentIdByScheduleId(value.scheduleId);
                        //     const students = await res.data.map((value) => value.student)
                        //     return {
                        //         ...value, students
                        //     }
                        // })
                        schedules.reduce((prev, curr, index, array) => {
                            // value.then((v) => console.log(v))
                            const i = prev.findIndex(t => t.course.semester.semesterCode === curr.course.semester.semesterCode && t.course.courseId == curr.course.courseId && t.room.roomId === curr.room.roomId && t.slot.slotId === curr.slot.slotId);
                            console.log(i);
                            if (+i === -1) {
                                return [...prev, {
                                    ...curr,
                                    trainingDate: {
                                        startDate: curr.trainingDate,
                                        endDate: curr.trainingDate
                                    }
                                }]
                            }else{
                                const _prev = [...prev];
                                _prev[i] = {
                                    ..._prev[i],
                                    trainingDate: {
                                        startDate: (new Date(prev[i].trainingDate.startDate) > new Date(curr.trainingDate)) ? curr.trainingDate : prev[i].trainingDate.startDate,
                                        endDate: (new Date(prev[i].trainingDate.endDate) < new Date(curr.trainingDate)) ? curr.trainingDate : prev[i].trainingDate.endDate
                                    }
                                }
                                return [..._prev];
                            }
                        }, [])
                    );
                })
            // schedules.map(async value => {
            //     value.then(v => { console.log(v); })
            // })
            console.log(schedules);
            setScheduleDisplayed(schedules);


            const users = await getAllUserAndUserProfile();
            const s = users.filter(u => u.roles.find(r => r.name.toLowerCase() === "student"));
            setStudents(s)

            //         } catch (error) {
            //             setError(error);
            //         }
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [navigate, schedule])


    const parseDate = (date) => {
        const d = new Date(Date.parse(date));
        return d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getFullYear();
    }

    const [scheduleRequest, setScheduleRequest] = useState({
        semesterCode: semesters && semesters[0].semesterCode,
        slotId: slots && slots[0].slotId,
        // subjectCode: subjects && subjects[0].subjectCode,
        courseId: courses[0] && courses[0].courseId,
        roomId: rooms && rooms[0].roomId,
        studentIds: []
    });

    useMemo(async () => {
        console.log(scheduleRequest.semesterCode);
        const res = await getCoursesBySemester(scheduleRequest.semesterCode);
        setCourses(res.data);
        setScheduleRequest(request => {
            return {
                ...request,
                courseId: res.data[0].courseId
            }
        })
    }, [scheduleRequest.semesterCode])

    const handleChangeScheduleRequest = (e) => {
        setScheduleRequest(state => {
            if (e.target.name === "students") {
                const studentId = e.target.value;
                return {
                    ...state,
                    // studentIds: [...state.studentIds ?? studentId]
                    studentIds: scheduleRequest.studentIds.findIndex(s => s === studentId) === -1
                        ? [...state.studentIds, studentId]
                        : scheduleRequest.studentIds.reduce((prev, curr) => { return curr === studentId ? [...prev] : [...prev, curr] }, [])
                }
            }
            return {
                ...state,
                [e.target.id]: e.target.value
            }
        })
    }
    const toast = useRef(null);

    const showSuccess = () => {
        toast.current.show({ severity: 'success', summary: 'Success', detail: 'Message Content', life: 3000 });
    }
    return (
        <>
            <Toast ref={toast} />

            <Button variant="primary" onClick={handleShow}>
                Create schedule
            </Button>
            <ScheduleList schedules={schedulesDisplayed} />
            <Modal show={show} onHide={handleClose} className='' size='xl'>
                <Modal.Header closeButton>
                    <Modal.Title>Create schedule</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div>
                        <table>
                            <tbody>
                                <tr>
                                    <td>
                                        <label htmlFor="semesterCode">Semester: </label>
                                    </td>
                                    <td>
                                        <select id='semesterCode' value={scheduleRequest.semesterCode}
                                            onChange={handleChangeScheduleRequest}>
                                            {semesters && semesters.map((value, index) => {
                                                return (
                                                    <option key={index} value={value.semesterCode}>
                                                        {`${value.semesterCode}`} {`${parseDate(value.startDate)} -> ${parseDate(value.endDate)}`}
                                                    </option>)
                                            })}
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label htmlFor='slotId'>Slot: </label>
                                    </td>
                                    <td>
                                        <select name="slot" id="slotId" value={scheduleRequest.slotId}
                                            onChange={handleChangeScheduleRequest}
                                        >
                                            {slots && slots.map((slot, index) => (
                                                <option key={index} value={slot.slotId}>{"Slot " + slot.slotId + " " + slot.startTime + " -> " + slot.endTime}</option>
                                            ))}
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label htmlFor="courseId">Course</label>
                                    </td>
                                    <td>
                                        <select name='' id='courseId' value={scheduleRequest.courseId}
                                            onChange={handleChangeScheduleRequest}
                                        >
                                            {courses && courses.map((course, index) => (
                                                <option key={index} value={course.courseId}>
                                                    {`${course.subject.subjectCode} - ${course.subject.subjectName}`} by {` ${course.teacher.fullName}`}
                                                </option>
                                            ))}
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label htmlFor='roomId'>Room: </label>
                                    </td>
                                    <td>
                                        <select name="room" id="roomId" value={scheduleRequest.roomId}
                                            onChange={handleChangeScheduleRequest}
                                        >
                                            {rooms && rooms.map((room, index) => (
                                                <option key={index} value={room.roomId}>
                                                    {room.roomNumber}
                                                </option>
                                            ))}
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label htmlFor="students">Students: </label>
                                    </td>
                                    <td>
                                        {students.map((student, index) => {
                                            // console.log(student);
                                            return (
                                                <span key={index}>
                                                    <input type="checkbox" name="students" id={`student${index}`} value={`${student.id}`}
                                                        onChange={handleChangeScheduleRequest} checked={(scheduleRequest.studentIds.find(id => {
                                                            return student.id === id;
                                                        })) ? true : false}
                                                    />
                                                    <label htmlFor={`student${index}`}>{student.username}</label>
                                                </span>
                                            )
                                        })}
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleSave}>
                        Save
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default Scheduler
