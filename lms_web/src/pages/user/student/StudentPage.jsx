import React, { useState } from 'react';
import Schedule from './Schedule';

const StudentPage = () => {
    const [content, setContent] = useState(null);

    return (
        <div className=''>
            Student page
            <div className='row'>
                <div className='col-md-2'>
                    Menu
                    <ul className='pl-3'>
                        <li onClick={e => setContent(<Schedule />)}>Schedule</li>
                    </ul>
                </div>
                <div className='col-md-10'>
                    {content}
                </div>
            </div>
        </div>
    )
}

export default StudentPage
