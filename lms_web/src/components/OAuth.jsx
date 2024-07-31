import { GoogleAuthProvider, getAuth, signInAnonymously, signInWithPopup } from 'firebase/auth'
import React from 'react'
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import { app } from '../../firebase';
import { Button } from 'react-bootstrap';
import { Button as PrimeButton } from 'primereact/button';
import { googleLogin } from '../services/authenticationService';

const OAuth = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleGoogleAuth = async () => {
        try {
            const provider = new GoogleAuthProvider();
            const auth = getAuth(app);

            const result = await signInWithPopup(auth, provider);
            console.log(result);
            
            const res = await googleLogin(result.user);
            console.log(res);

            // const data = await res.json();
            // console.log(data);
            // navigate("/");
        } catch (error) {
            console.log("google auth error", error);
        }
    }
    return (
        <>
            <PrimeButton
                size='small'
                onClick={handleGoogleAuth}
                type='button' className='bg-red-700 text-white hover:opacity-90 text-sm rounded p-2'>
                continue with google
            </PrimeButton>
        </>
    )
}

export default OAuth
