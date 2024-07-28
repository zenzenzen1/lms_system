import { useNavigate } from 'react-router-dom';
// import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { isValidToken, login } from '../../services/authenticationService';
import { useDispatch, useSelector } from 'react-redux'
import { setUser } from '../../redux/slice/UserSlice';
import { getMyInfo } from '../../services/UserService';
import { removeToken } from '../../services/localStorageService';
import { checkTokenInterval } from '../../configurations/configuration';

const Login = () => {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("");

    const navigate = useNavigate();
    const dispatch = useDispatch();

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
        if (res.data.code != 1000) {
            setError(res.data.message);
            return;
        }
        const interval = setInterval(async () => {
            const _isValidToken = await isValidToken();
            if (!_isValidToken) {
                removeToken();
                if (window.location.pathname !== '/user/login')
                    window.location.href = '/user/login';
            }
        }, checkTokenInterval);
        localStorage.setItem('checkTokenInterval', interval);
        const userResponse = await getMyInfo();
        const _user = userResponse.data.result;
        const user = { ..._user, roles: _user.roles.map(role => role.name), permissions: _user.roles.map(p => [...p.permissions]) };
        console.log(user);

        dispatch(setUser(user))

        if (user.roles.includes("ADMIN")) {
            navigate("/user/admin");
        }
        else if (user.roles.includes("STUDENT")) {
            navigate("/user/student");
        }
        else if (user.roles.includes("TEACHER")) {
            navigate("/user/teacher");
        }

    }
    // useEffect(() => {
    //     const interval = setInterval(async () => {
    //       const _isValidToken = await isValidToken();
    //       if (!_isValidToken) {
    //         removeToken();
    //         if (window.location.pathname !== '/user/login')
    //           window.location.href = '/user/login';
    //       }
    //     }, 2000);
    //     localStorage.setItem('checkTokenInterval', interval);
    //     return () => {
    //       console.log("App component is unmounted");
    //       clearInterval(interval);
    //     }
    //     // eslint-disable-next-line react-hooks/exhaustive-deps
    //   });

    return (
        <>
            <div>

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
                                        className=""
                                        value={username}
                                        onChange={(e) => { setUsername(e.target.value) }}
                                    />
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-12">
                            <div className="form-group">
                                <div className="">
                                    <label>Your Password</label>
                                    <InputText
                                        name="password"
                                        id="password"
                                        type="password"
                                        className=""
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
                            <Button
                                size='small'
                                name="submit"
                                className="p-2 text-sm rounded"
                                onClick={handleLogin}
                            >
                                {<i className="pi pi-apple"></i>}
                                {<i className='pi pi-spin pi-spinner'></i>}
                                <span>Login</span>
                            </Button>
                        </div>
                    </div>
                </form>

            </div>
        </>
    )
}

export default Login
