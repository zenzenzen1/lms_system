// @ts-nocheck


import React, { useEffect, useMemo, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getKey } from '../../../../services/localStorageService';
import { Link, Outlet, useNavigate } from 'react-router-dom';
import UserList from './UserList';
import { isValidToken, verifyToken } from '../../../../services/authenticationService';
import UserHeader from '../../../user/UserHeader';
import Scheduler from './Scheduler';
import { getAllRooms, getAllSlots, getAllSubjects } from '../../../../services/ScheduleService';
import { setRooms, setSlots, setSubjects } from '../../../../redux/slice/ScheduleSlice';
import { setSemestersAction } from '../../../../redux/action/ScheduleAction';
import { getAllUserAndUserProfile } from '../../../../services/UserService';
import {adminSidebarTabs} from "../../../../configurations/common/sidebar";
import SideBar from '../../../common/SideBar';
import Header from '../../../common/Header';

//
const AdminPage = ({ defaultContent }) => {
    const [menu, setMenu] = useState({
        user: false,
        schedule: false
    });
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [content, setContent] = useState(defaultContent);

    const user = useSelector(state => state.user);


    const handleUserClick = async () => {
        const isValidUser = await isValidToken();
        if (!isValidUser) {
            console.log(isValidUser);
            navigate("/user/login");
        }

        setContent(<UserList />)
    }

    verifyToken();

    // useEffect(() => {
    //     (async () => {
    //         try {
    //             const [_slots, _subjects, _rooms] = await Promise.all([getAllSlots(), getAllSubjects(), getAllRooms()]);
    //             dispatch(setSlots(_slots.data.result));
    //             dispatch(setSubjects(_subjects.data.result));
    //             dispatch(setRooms(_rooms.data.result));

    //             dispatch(setSemestersAction());

    //         } catch (error) {
    //             console.log(error);
    //         }
    //     })();
    // }, [navigate])

    const [showSideBar, setShowSideBar] = useState(true);
    return (
        <>
            {/* <div>
                admin page. He Nhoo {user.username}
                <div className='flex'>
                    <div className='w-1/6'>
                        <span className='text-blue-500'>Menu</span>
                        <ul className='pl-3 flex flex-col gap-1'>
                            <Menu />
                            <li className='cursor-pointer w-fit' onClick={handleUserClick}>
                                Show all users 
                            </li>
                            <li className='cursor-pointer w-fit' onClick={() => setContent(<Scheduler key={Math.round(Math.random() * 1000)}/>)}>Schedule</li>
                        </ul>
                    </div>
                    <div className='w-5/6'>
                        {content || defaultContent || <Scheduler />}    
                    </div>

                </div>
            </div > */}
            <div className="flex-row flex">
                <SideBar show={showSideBar} sidebarTabs={adminSidebarTabs}/>
                <div className="flex-1">
                    <Header setShowSideBar={setShowSideBar} />
                    <Outlet />
                </div>
            </div>

        </>
    )
}

export default AdminPage
