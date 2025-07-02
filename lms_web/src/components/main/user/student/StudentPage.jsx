import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { setSemestersAction } from '../../../../redux/action/ScheduleAction';
import { setRooms, setSlots, setSubjects } from '../../../../redux/slice/ScheduleSlice';
import { verifyToken } from '../../../../services/authenticationService';
import { getAllRooms, getAllSlots, getAllSubjects } from '../../../../services/ScheduleService';
import AttendanceStatus from './AttendanceStatus';
import Schedule from './Schedule';
import { getNotificationsByUserId } from '../../../../services/notificationService';
import SideBar from 'src/components/common/SideBar';
import Header from 'src/components/common/Header';
import { Outlet } from 'react-router-dom';
import { studentSidebarTabs } from 'src/configurations/common/sidebar';

const StudentPage = () => {
    verifyToken();
    const dispatch = useDispatch();
    const [content, setContent] = useState(null);
    const [notifications, setNotifications] = useState([]);
    const [showNotification, setShowNotification] = useState(false);
    useEffect(() => {
        (async () => {
            try {
                const _slot = await getAllSlots();
                dispatch(setSlots(_slot.data.result));

                const _subjects = await getAllSubjects();
                dispatch(setSubjects(_subjects.data.result));

                const _rooms = await getAllRooms();
                dispatch(setRooms(_rooms.data.result));

                dispatch(setSemestersAction());
            } catch (error) {
                // setError(error);
                console.log(error);
            }
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);
    
    // const user = useSelector(state => state.user);
    // useEffect(() => {
    //     const getNotificationsInterval = setInterval(() => {
    //         getNotificationsByUserId(user.userProfile.id).then(res => {
    //             console.log(res.data);
    //             setNotifications(res.data);
    //         });
    //     }, 20000);
    //     return () => {
    //         clearInterval(getNotificationsInterval);
    //     }
    // }, [user]);
    const handleShowNotification = () => {
        setShowNotification(!showNotification);
    }
    const [showSideBar, setShowSideBar] = useState(true);
    return (<>
        <div className="flex-row flex">
            <SideBar show={showSideBar} sidebarTabs={studentSidebarTabs} />
            <div className="flex-1">
                <Header setShowSideBar={setShowSideBar} />
                <Outlet />
            </div>
        </div>
        {/*  <div className=''>
             Student page
             <div className='row'>
                 <div className='col-md-2'>
                     Menu
                     <ul className='pl-3 mb-0 flex flex-col gap-1'>
                         <Menu className={"pl-3 mb-0"} handleShowNotification={handleShowNotification} NotificationContent={<>
                             Notification {notifications && <span className='text-red-500'>{"{" + notifications.length + "}"}</span>}
                             <ul className=' list-decimal text-sm'>
                             {showNotification && notifications && notifications.length > 0 && notifications.map((value, index) => {
                                 return <li className='p-0' key={index}>
                                     {value.message}
                                 </li>
                             })
                             }
                             </ul>
                         </>} />
                         <li onClick={e => setContent(<Schedule />)}>Schedule</li>
                         <li onClick={e => setContent(<AttendanceStatus />)}>
                             Attendance Status
                            
                         </li>
                     </ul>
                 </div>
                 <div className='col-md-10'>
                     {content ?? <Schedule />}
                 </div>
             </div>
         </div> */}
    </>)
}

export default StudentPage
