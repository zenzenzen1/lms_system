import { DataTable } from 'primereact/datatable';
import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';

const ScheduleList = () => {
    const {slots, subjects, rooms} = useSelector(state => state.schedule);
    useEffect(() => {
        const e = document.getElementById('schedule-table');
        const t = e.getElementsByTagName('table')[0];
        console.log({e, t}); 
    })
    
    return (
        <div>
            <DataTable id='schedule-table'>
                
            </DataTable>
        </div>
    )
}

export default ScheduleList
