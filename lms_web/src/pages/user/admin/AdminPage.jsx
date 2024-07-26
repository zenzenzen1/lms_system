import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { getKey } from '../../../services/localStorageService';
import { Link, useNavigate } from 'react-router-dom';
import UserList from './UserList';
import { isValidToken, verifyToken } from '../../../services/authenticationService';
import UserHeader from '../../../components/user/UserHeader';
import Scheduler from './Scheduler';
import { getAllRooms, getAllSlots, getAllSubjects } from '../../../services/ScheduleService';
import { setRooms, setSlots, setSubjects } from '../../../redux/slice/ScheduleSlice';
import { setSemestersAction } from '../../../redux/action/ScheduleAction';
import { getAllUserAndUserProfile } from '../../../services/UserService';

const AdminPage = () => {
    const [menu, setMenu] = useState({
        user: false,
        schedule: false
    });
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [content, setContent] = useState("this is content");

    const _user = JSON.parse(getKey("persist:root")).user;
    const user = JSON.parse(_user);
    
    
    const handleUserClick = async () => {
        const isValidUser = await isValidToken();
        if (!isValidUser) {
            console.log(isValidUser);
            navigate("/user/login");
        }

        setContent(<UserList />)
    }
    
    useEffect(() => {
        (async () => {
            try {
                const [_slots, _subjects, _rooms] = await Promise.all([getAllSlots(), getAllSubjects(), getAllRooms()]);
                dispatch(setSlots(_slots.data.result));
                dispatch(setSubjects(_subjects.data.result));
                dispatch(setRooms(_rooms.data.result));

                dispatch(setSemestersAction());

            } catch (error) {
                console.log(error);
            }
        })();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [navigate])
    
    console.log("admin page");
    return (
        <>
            {/* <div className='position-sticky text-blue-600'>
                <UserHeader />
            </div> */}
            <div>
                admin page. He Nhoo {user.username}
                <div className='flex'>
                    <div className='w-1/6'>
                        <span className='text-blue-500'>Menu</span>
                        <ul className='pl-1'>
                            <li className='cursor-pointer' onClick={handleUserClick}>
                                Show all users
                            </li>
                            <li className='cursor-pointer' onClick={() => setContent(<Scheduler/>)}>Schedule</li>
                        </ul>
                    </div>
                    <div className='w-5/6'>
                        {content}
                    </div>

                </div>
            </div >
        </>
    )
}

export default AdminPage
