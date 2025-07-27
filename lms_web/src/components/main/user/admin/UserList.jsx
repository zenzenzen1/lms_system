import React, { useEffect, useState } from 'react'
import { getAllUser, getAllUserAndUserProfile, getAllUserProfile } from '../../../../services/UserService';
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
            const users = await getAllUserAndUserProfile();
            setUsers(users);
        })()

        return () => {
            
        }
    }, [navigate])
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
