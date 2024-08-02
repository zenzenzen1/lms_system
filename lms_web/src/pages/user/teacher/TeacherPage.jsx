import React, { useState } from 'react'
import Scheduler from './Scheduler';
import { useNavigate } from 'react-router-dom';
import Menu from '../common/Menu';

// eslint-disable-next-line react/prop-types
const TeacherPage = ({ defaultContent = null }) => {
    const [content, setContent] = useState(defaultContent ?? <Scheduler />);
    const navigate = useNavigate();
    

    return (
        <div>
            
            Teacher page
            <div className='row'>
                <div className='col-md-2'>
                    Menu
                    <ul className='pl-3'>
                        <Menu/>
                        <li className='cursor-pointer w-fit' onClick={e => {
                            setContent(<Scheduler />)
                            // navigate('/user/teacher/schedule')
                        }}>Schedule</li>
                    </ul>
                </div>
                <div className='col-md-10'>
                    {content}
                </div>
            </div>
        </div>
    )
}

export default TeacherPage
