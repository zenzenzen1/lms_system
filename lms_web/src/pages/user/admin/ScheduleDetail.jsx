import React, { useState } from 'react'
import { useLocation } from 'react-router-dom';
import AdminPage from './AdminPage';

// eslint-disable-next-line react/prop-types
const ScheduleDetail = ({schedule = {}}) => {
    // const location = useLocation();
    // const schedule = location.state.schedule;
    console.log(schedule);
    const [studentList, setStudentList] = useState([]);
    
    
    return (
        <>
            <div className='mt-3'>
                <div className='row'>
                    <div className='col-md-4'>
                        <table>
                            <tbody>
                                <tr>
                                    <th>{"Semester:"}</th>
                                    <td>{schedule.course.semester.semesterCode}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            {/* <AdminPage defaultContent={<div>
                asdf
            </div>} /> */}
        </>
    )
}

export default ScheduleDetail
