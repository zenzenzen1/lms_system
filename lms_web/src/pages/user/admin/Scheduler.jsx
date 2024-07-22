import React, { useEffect, useRef, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { setSemestersAction } from '../../../redux/action/ScheduleAction';
import { setRooms, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';
import { getAllRooms, getAllSlots, getAllSubjects, saveSchedule } from '../../../services/ScheduleService';
import { getAllUserAndUserProfile } from '../../../services/UserService';
import ScheduleList from './ScheduleList';
import { Toast } from 'primereact/toast';
import { Button as PrimereactButton} from 'primereact/button';

const Scheduler = () => {
    const [show, setShow] = useState(false);
    // const [slots, setSlots] = useState([]);
    // const [subjects, setSubjects] = useState([]);
    // const [rooms, setRooms] = useState([]);
    const [schedule, setSchedule] = useState({});
    const [error, setError] = useState("");
    const [students, setStudents] = useState([]);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleSave = async () => {
        console.log(scheduleRequest);
        const _confirm = confirm("Save?");
        console.log(_confirm);
        if (_confirm) {
            // save schedule
            const res = await saveSchedule(scheduleRequest);

        }
    }

    useEffect(() => {
        (async () => {
            try {
                const [_slots, _subjects, _rooms] = await Promise.all([getAllSlots(), getAllSubjects(), getAllRooms()]);
                dispatch(setSlots(_slots.data.result));
                dispatch(setSubjects(_subjects.data.result));
                dispatch(setRooms(_rooms.data.result));

                setSchedule({
                    slots: _slots.data.result,
                    subjects: _subjects.data.result,
                    rooms: _rooms.data.result
                })
                dispatch(setSemestersAction());
                const users = await getAllUserAndUserProfile();
                const s = users.filter(u => u.roles.find(r => r.name.toLowerCase() === "student"));
                setStudents(s)
            } catch (error) {
                setError(error);
            }
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [navigate])
    const { slots, subjects, rooms, semesters } = useSelector(state => state.schedule);
    const parseDate = (date) => {
        const d = new Date(Date.parse(date));
        return d.getDate() + "/" + (d.getMonth() + 1) + "/" + d.getFullYear();
    }

    const [scheduleRequest, setScheduleRequest] = useState({
        semesterCode: semesters && semesters[0].semesterCode,
        slotId: slots && slots[0].slotId,
        subjectCode: subjects && subjects[0].subjectCode,
        roomId: rooms && rooms[0].roomId,
        studentIds: []
    });
    console.log(scheduleRequest);

    const handleChangeScheduleRequest = (e) => {

        setScheduleRequest(state => {
            if (e.target.name === "students") {
                const studentId = e.target.value;
                return {
                    ...state,
                    studentIds: scheduleRequest.studentIds.findIndex(s => s === studentId) === -1 ? [...scheduleRequest.studentIds, studentId] : scheduleRequest.studentIds.reduce((prev, curr) => { return curr === studentId ? prev : [...prev, curr] }, [])
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
            <ScheduleList />
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
                                        <label htmlFor='subjectCode'>Subject: </label>
                                    </td>
                                    <td>
                                        <select name="subject" id="subjectCode" value={scheduleRequest.subjectCode}
                                            onChange={handleChangeScheduleRequest}
                                        >
                                            {subjects && subjects.map((subject, index) => (
                                                <option key={index} value={subject.subjectCode}>{`${subject.subjectCode} - ${subject.subjectName}`}</option>
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
                                                <option key={index} value={room.id}>{room.roomNumber}</option>
                                            ))}
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label htmlFor="students">Students: </label>
                                    </td>
                                    <td>
                                        {students.map((value, index) => {
                                            return (
                                                <span key={index}>
                                                    <input type="checkbox" name="students" id={`student${index}`} value={`${value.id}`}
                                                        onChange={handleChangeScheduleRequest} checked={scheduleRequest.studentIds.find(id => {
                                                            return value.id === id;
                                                        })}
                                                    />
                                                    <label htmlFor={`student${index}`}>{value.username}</label>
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
