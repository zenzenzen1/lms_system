import { useNavigate } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { login } from '../../services/authenticationService';
const Login = () => {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("");
    
    const navigate = useNavigate();
    
    const handleLogin = async (e) => {
        e.preventDefault();
        // try {
        //     const res = await login(username, password);
        //     console.log(res);
        // } catch (error) {
        //     console.log(error);
        //     setError(JSON.stringify(error));
        // }
        const res = await login(username, password);
        if (!res.data.code != 1000) {
            setError(res.data.message);
            return;
        }
        console.log(res);
    }

    return (
        <>
            
            <span className='text-red-400'>{error}</span>
            <form className="contact-bx">
                <div className="row placeani">
                    <div className="col-lg-12">
                        <div className="form-group">
                            <div className="">
                                <label>Your User Name</label>
                                <InputText
                                    name="username"
                                    id="username"
                                    type="text"
                                    required=""
                                    className="form-control"
                                    value={username}
                                    onChange={(e) => { setUsername(e.target.value) }}
                                />
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-12">
                        <div className="form-group">
                            <div className="input-group">
                                <label>Your Password</label>
                                <InputText
                                    name="password"
                                    id="password"
                                    type="password"
                                    className="form-control"
                                    required=""
                                    value={password}
                                    onChange={(e) => { setPassword(e.target.value) }}
                                />
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-12">
                        <div className="form-group form-forget">
                            <div className='flex'>
                                <div className="custom-control custom-checkbox">
                                    <input
                                        type="checkbox"
                                        className="custom-control-input"
                                        id="customControlAutosizing"
                                    />
                                </div>
                                <p className='p-0 mb-0 ml-3'>Remember me</p>
                            </div>
                            <a href="forget-password" className="ml-auto">
                                Forgot Password?
                            </a>
                        </div>
                    </div>
                    <div className="col-lg-12 m-b30">
                        <Button variant="secondary"
                        size='small'
                            id="lms-login-btn"
                            name="submit"
                            value="Submit"
                            className="btn"
                            onClick={handleLogin}
                        >
                            Login
                        </Button>
                    </div>
                </div>
            </form>

        </>
    )
}

export default Login
