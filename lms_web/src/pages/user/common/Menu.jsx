import React from 'react';
import { useNavigate } from 'react-router-dom';

// eslint-disable-next-line react/prop-types
const Menu = ({ className, handleShowNotification, NotificationContent }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        const confirm = window.confirm('Are you sure you want to logout?');
        if (confirm) {
            clearInterval(localStorage.getItem('checkTokenInterval'));
            localStorage.clear();
            navigate('/user/login');
        }
    }
    // const token = localStorage.getItem('token')
    // if (!token) {
    //     window.location.href = '/login'
    // }
    return (
        <>

            <li className='w-fit cursor-pointer' onClick={handleLogout}>{"<-- Logout"}</li>
            <li className='w-fit cursor-pointer' onClick={handleShowNotification}>{NotificationContent}
            </li>
        </>
    )
}

export default Menu
