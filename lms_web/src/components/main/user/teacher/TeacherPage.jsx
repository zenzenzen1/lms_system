import React, { useState } from 'react'
import { Outlet, useNavigate } from 'react-router-dom';
import SideBar from 'src/components/common/SideBar';
import { TeacherSidebarTabs } from 'src/configurations/common/sidebar';
import Header from 'src/components/common/Header';
import { verifyToken } from '../../../../services/authenticationService';

const TeacherPage = ({ defaultContent = null }) => {
    const navigate = useNavigate();
    const [showSideBar, setShowSideBar] = useState(true);
    
    verifyToken();
    return (<>
        <div className="flex-row flex">
            <SideBar show={showSideBar} sidebarTabs={TeacherSidebarTabs} />
            <div className="flex-1">
                <Header setShowSideBar={setShowSideBar} />
                <Outlet />
            </div>
        </div>
        {/* <div>
            
            Teacher page
            <div className='row'>
                <div className='col-md-2'>
                    Menu
                    <ul className='pl-3'>
                        <Menu />
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
        </div> */}
    </>)
}

export default TeacherPage
