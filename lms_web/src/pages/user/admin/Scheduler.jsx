import React, { useEffect, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { getAllSlots } from '../../../services/SlotService';
import { useNavigate } from 'react-router-dom';
import { getAllSubjects } from '../../../services/SubjectService';
import { getAllRooms } from '../../../services/RoomService';
import ScheduleList from './ScheduleList';

const Scheduler = () => {
    const [show, setShow] = useState(false);
    const [slots, setSlots] = useState([]);
    const [subjects, setSubjects] = useState([]);
    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    useEffect(() => {
        (async () => {
            const _slot = await getAllSlots();
            console.log(_slot);
            setSlots(_slot.data.result)

            const _subjects = await getAllSubjects();
            setSubjects(_subjects.data.result);
            
            const _rooms = await getAllRooms();
            setRooms(_rooms.data.result);
        })();
    }, [navigate])

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
                                            {slots.map((slot, index) => (
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
                                            {subjects.map((subject, index) => (
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
                                            {rooms.map((room, index) => (
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
