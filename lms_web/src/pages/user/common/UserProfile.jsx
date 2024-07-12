import { InputText } from 'primereact/inputtext';
import React, { useState } from 'react';
import { useSelector } from 'react-redux';
const UserProfile = () => {
    // const [user, setUser] = useState({})
    const _user = useSelector(state => state.user);
    const [user, setUser] = useState(_user);
    
    
    return (
        <div>
            <h1 className='text-xl font-bold'>User Profile</h1>
            <table>
                <tbody>

                    <tr>
                        <td>
                            <label htmlFor='username'>Username: </label>
                        </td>
                        <td>
                            <InputText id='username' autoComplete='off' value={user.username} />
                        </td>
                    </tr>
                    {/* <tr>
                    <td>
                        <label htmlFor='password'>Password: </label>
                    </td>
                    <td>
                        <InputText id='password' type='password' autoComplete='off' value={""} />
                    </td>
                </tr> */}
                    <tr>
                        <td>
                            <label htmlFor='fullName'>Full Name: </label>
                        </td>
                        <td>
                            <InputText id='fullName' autoComplete='off' value={user.fullName} keyfilter={'alpha'} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label htmlFor='email'>Email: </label>
                        </td>
                        <td>
                            <InputText id='email' type='email' autoComplete='off' keyfilter={'email'} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label htmlFor='phone'>Phone: </label>
                        </td>
                        <td>
                            <InputText id='phone' type='tel' autoComplete='off' keyfilter={'pnum'} />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label htmlFor='dob'>Date of Birth: </label>
                        </td>
                        <td>
                            <InputText id='dob' type='date' autoComplete='off' />
                        </td>
                    </tr>
                </tbody>
            </table>


        </div>
    )
}

export default UserProfile
