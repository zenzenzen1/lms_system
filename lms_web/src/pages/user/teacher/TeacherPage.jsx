import React, { useState } from 'react'
import Scheduler from './Scheduler';

const TeacherPage = () => {
    const [content, setContent] = useState(null);
    
    
    return (
        <div>
            Teacher page
            <div className='row'>
                <div className='col-md-2'>
                    Menu
                    <ul className='pl-3'>
                        <li onClick={e => {setContent(<Scheduler />)} }>Schedule</li>
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
