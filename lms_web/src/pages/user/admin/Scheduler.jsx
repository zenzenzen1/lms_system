import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useNavigate } from 'react-router-dom';
import ScheduleList from './ScheduleList';
import { getAllRooms, getAllSlots, getAllSubjects } from '../../../services/ScheduleService';
import { useDispatch, useSelector } from 'react-redux';
import { setRooms, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';
import { RootType } from '../../../redux/store';

const Scheduler = () => {
    const [show, setShow] = useState(false);
    // const [slots, setSlots] = useState([]);
    // const [subjects, setSubjects] = useState([]);
    // const [rooms, setRooms] = useState([]);
    const [schedule, setSchedule] = useState({});
    const [error, setError] = useState("");
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    useEffect(() => {
        (async () => {
            try {
                const _slot = await getAllSlots();
                // setSlots(_slot.data.result)
                dispatch(setSlots(_slot.data.result));
                console.log(_slot.data.result);
                
                const _subjects = await getAllSubjects();
                // setSubjects(_subjects.data.result);
                dispatch(setSubjects(_subjects.data.result));
                
                const _rooms = await getAllRooms();
                // setRooms(_rooms.data.result);
                dispatch(setRooms(_rooms.data.result));
                setSchedule({
                    slots: _slot.data.result,
                    subjects: _subjects.data.result,
                    rooms: _rooms.data.result
                })
            } catch (error) {
                setError(error);
            }
        })();
    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    const {slots, subjects, rooms} = useSelector(state => state.schedule);
    
    console.log({ slots, subjects, rooms });

    return (
        <>
            <Button variant="primary" onClick={handleShow}>
                Create schedule
            </Button>
            <ScheduleList/>
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
                                        <label htmlFor='slot'>Slot: </label>
                                    </td>
                                    <td>
                                        <select name="slot" id="slot">
                                            {slots && slots.map((slot, index) => (
                                                <option key={index} value={slot.id}>{"Slot " + slot.slotId + " " + slot.startTime + " -> " + slot.endTime}</option>
                                            ))}
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label htmlFor='subject'>Subject: </label>
                                    </td>
                                    <td>
                                        <select name="subject" id="subject">
                                            {subjects && subjects.map((subject, index) => (
                                                <option key={index} value={subject.id}>{`${subject.subjectCode} - ${subject.subjectName}`}</option>
                                            ))}
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <label htmlFor='room'>Room: </label>
                                    </td>
                                    <td>
                                        <select name="room" id="room">
                                            {rooms && rooms.map((room, index) => (
                                                <option key={index} value={room.id}>{room.roomNumber}</option>
                                            ))}
                                        </select>
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
                    <Button variant="primary" onClick={handleClose}>
                        Save
                    </Button>
                </Modal.Footer>
            </Modal>
        </>
    )
}

export default Scheduler
