import React, { useEffect, useState } from 'react'
import { getAllUser, getAllUserAndUserProfile, getAllUserProfile } from '../../../services/UserService';
import { useNavigate } from 'react-router-dom';

import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

const RoleTemplate = (e) => {
    return (
        <>
            {e.roles.map(role => role.name.toLowerCase() + " ")}
        </>
    )
}

const UserList = () => {
    const [users, setUsers] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        (async () => {
            // const _users = await getAllUser();
            // // setUsers(_users.data.result);
            // const users = _users.data.result;

            // const _userProfiles = await getAllUserProfile();

            // const userProfiles = _userProfiles.data.result;
            // console.log({ users, userProfiles });

            // setUsers(users.map(user => {
            //     const userProfile = userProfiles.find(p => p.userId === user.id)
            //     return userProfile ? {
            //         ...user,
            //         ...userProfile
            //     } : {
            //         ...user
            //     }
            // }))
            const users = await getAllUserAndUserProfile();
            setUsers(users);
        })()

        return () => {
            
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])
    console.log(users);
    return (
        <>
            <div>
                <DataTable className='text-sm'
                    size='small' value={users}
                    sortMode='multiple' removableSort
                    stripedRows
                >
                    <Column sortable field="username" header="Username" ></Column>
                    <Column header="Roles" body={RoleTemplate}></Column>
                    <Column sortable field="email" header="Email"></Column>
                    <Column sortable field="fullName" header="Name"></Column>
                    <Column sortable field="active" header="Active"></Column>
                    <Column sortable field='phoneNumber' header='Phone Number'></Column>
                    <Column sortable field='dob' header='date of birth'></Column>
                </DataTable>
            </div>
        </>
    )
}

export default UserList
