import React from 'react'
import TeacherScheduleList from './ScheduleList'
import TeacherPage from './TeacherPage'

const Scheduler = () => {
    
    return (
        <div>
            <TeacherScheduleList/>
            {/* <TeacherPage defaultContent={<TeacherScheduleList/>}/> */}
        </div>
    )
}

export default Scheduler
