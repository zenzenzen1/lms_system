import React, { useMemo, useState } from 'react'
import Modal from 'react-bootstrap/Modal';
import { useLocation } from 'react-router-dom';
import AdminPage from './AdminPage';
import moment from 'moment';
import { getStudentsByScheduleId } from '../../../../services/UserService';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import Button from 'react-bootstrap/esm/Button';

// eslint-disable-next-line react/prop-types
const ScheduleDetail = ({ schedule = {} }) => {
    // const location = useLocation();
    // const schedule = location.state.schedule;
    console.log(schedule);
    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => {
        setShow(true);

    }
    const [students, setStudents] = useState([]);
    useMemo(() => {
        getStudentsByScheduleId(schedule.scheduleId).then(res => {
            console.log(res.data.result);
            setStudents(res.data.result.map((s, index) => {return {...s, index: index + 1}}));
        })
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    const handleSave = () => {
        
    }
    return (
        <>
            <div className='mt-5'>
                <div className='row'>
                <Modal show={show} onHide={handleClose} className='' size='xl'>
                <Modal.Header closeButton>
                    <Modal.Title>Create schedule</Modal.Title>
                </Modal.Header>
                <Modal.Body className='relative'>
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
                    {/* <div className='col-md-12'> */}
                        <table>
                            <tbody className=''>
                                <tr>
                                    <th>{"Semester"}</th>
                                    <td>{schedule.course.semester.semesterCode}</td>
                                </tr>
                                <tr>
                                    <th>Course</th>
                                    <td>{`${schedule.course.subject.subjectCode}`}</td>
                                </tr>
                                <tr>
                                    <th>Instructor</th>
                                    <td>{schedule.course.teacher.fullName}</td>
                                </tr>
                                <tr>
                                    <th>Room</th>
                                    <td>{schedule.room.roomNumber}</td>
                                </tr>
                                <tr>
                                    <th>Slot</th>
                                    <td>{schedule.slot.slotId}</td>
                                </tr>
                                <tr>
                                    <th>Training Date</th>
                                    <td>{`${moment(schedule.trainingDate.startDate).format("D/M/yyyy")} -> 
                                        ${moment(schedule.trainingDate.endDate).format("D/M/yyyy")}`}</td>
                                </tr>
                                <tr>
                                    <th className='min-w-fit'>
                                        <div className='absolute top-64'>Students</div>
                                    </th>
                                    <td className='' style={{minWidth: "700px"}}>
                                        {students && Array.isArray(students)
                                            ? <div>
                                                <div className='mr-4 mb-1 text-right top-0'>
                                                    <div className='absolute z-1 right-4 top-48'>
                                                        <Button size='sm' variant='secondary' onClick={() => {
                                                            setShow(true);
                                                        }}>
                                                            Add more student
                                                        </Button>
                                                    </div>
                                                </div>
                                                <div className=''>
                                                    <DataTable
                                                        // style={{ fontSize: "14px" }}
                                                        className='p-datatable-striped text-sm' size='small'
                                                        value={students || []}
                                                    >
                                                        <Column field='index' header="Index"></Column>
                                                        <Column field='id' header="StudentId"></Column>
                                                        <Column field='fullName' header="Full Name"></Column>
                                                        <Column field='email' header="Email"></Column>
                                                        <Column field='phoneNumber' header="Phone"></Column>
                                                        <Column field='dob' header="dob"></Column>
                                                    </DataTable>
                                                    <div className=' right-1 text-right mr-7 mt-1'>
                                                        total: {`${students.length} ${students.length > 1 ? "students" : "student"}`}
                                                    </div>
                                                </div>
                                            </div>
                                            : <>loading....</>
                                        }
                                    </td>
                                </tr>
                                {/* <tr className=''>
                                    <th>Created At</th>
                                    <td>{moment(schedule.createdAt).format("D/M/yyyy")}</td>
                                </tr> */}
                            </tbody>
                        </table>
                    {/* </div> */}

                </div>
            </div>
            {/* <AdminPage defaultContent={<div>
                asdf
            </div>} /> */}
        </>
    )
}

export default ScheduleDetail
