import React, { useState } from 'react'
import { useSelector } from 'react-redux'
import { getKey } from '../../../services/localStorageService';
import { Link, useNavigate } from 'react-router-dom';
import UserList from './UserList';
import { isValidToken, verifyToken } from '../../../services/authenticationService';
import UserHeader from '../../../components/user/UserHeader';
import Scheduler from './Scheduler';

const AdminPage = () => {
    const [menu, setMenu] = useState({
        user: false,
        schedule: false
    });
    const navigate = useNavigate();
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
