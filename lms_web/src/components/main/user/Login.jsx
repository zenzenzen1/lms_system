import { useNavigate } from 'react-router-dom';
import React, { Fragment, useState } from 'react';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { isValidToken, login, logOut, verifyToken } from '../../../services/authenticationService';
import { useDispatch, useSelector } from 'react-redux'
import { setUser } from '../../../redux/slice/UserSlice';
import { getMyInfo } from '../../../services/UserService';
import { removeToken } from '../../../services/localStorageService';
import { checkTokenInterval } from '../../../configurations/configuration';
import OAuth from '../../../components/OAuth';
import { urlRolePrefix } from '../../../configurations/common/navigate';
import { ToastContainer } from 'react-toastify';
import { Form, Formik } from 'formik';
import * as Yup from 'yup';
import { TextInputField } from '../../common/form/FormComponent';
import { inputTextClassName } from 'src/configurations/common/className';

const Login = () => {
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);

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
            verifyToken();
        }, checkTokenInterval);
        localStorage.setItem('checkTokenInterval', interval);
        const userResponse = await getMyInfo();
        const _user = userResponse.data.result;
        const user = { ..._user, roles: _user.roles.map(role => role.name), permissions: _user.roles.map(p => [...p.permissions]) };
        console.log(user);

        dispatch(setUser(user))

        if (user.roles.includes("ADMIN")) {
            navigate(urlRolePrefix.admin);
        }
        else if (user.roles.includes("STUDENT")) {
            navigate(urlRolePrefix.student);
        }
        else if (user.roles.includes("TEACHER")) {
            navigate(urlRolePrefix.teacher);
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

    return (<>
        <div>
            <span className='text-red-400'>{error}</span>
            <ToastContainer
                draggable
            />
            <div className="w-full  flex justify-center h-full items-center">
                <div className="bg-white max-md:w-xl md:p-10 md:w-2xl h-fit lg:p-6 lg:w-xl max-md:p-10  max-sm:p-5 shadow-lg mb-11">
                    <div className="text-center">
                        <h3>Đăng Nhập</h3>
                    </div>
                    <Formik
                        validationSchema={Yup.object({
                            username: Yup.string()
                                .required("Username is required"),
                            password: Yup.string()
                                .min(3, "Password must be at least 3 characters")
                                .required("Password is required")
                        })}
                        initialValues={{
                            username: '',
                            password: ''
                        }}
                        onSubmit={async (values) => {
                            setLoading(true);
                            try {
                                
                                const res = await login(values.username, values.password);
                                if (res.data.code != 1000) {
                                    setError(res.data.message);
                                    return;
                                }
                                const interval = setInterval(async () => {
                                    verifyToken();
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
                            } catch (error) {
                                console.log(error);
                            }
                            setLoading(false);
                        }}
                    >
                        <Form>
                            <Fragment>
                                <div className="mb-3">
                                    {/* <label className="form-label">Email</label>
                        <input required className="form-control form-control-lg" onChange={formik.handleChange}  name="email" placeholder="Nhập email của bạn" /> */}
                                    <TextInputField type="text" className={`${inputTextClassName}`} fieldName={"username"} label="Username" />
                                </div>
                                <div className="mb-3">
                                    <TextInputField type="password" className={`${inputTextClassName}`} fieldName={"password"} label="Password" />
                                    {/* <small>
                                        <a href="pages-reset-password.html">Quên mật khẩu?</a>
                                    </small> */}
                                </div>
                                <div className="text-center mt-3">
                                    {/* <a href="index.html" className="btn btn-lg btn-primary">Sign in</a> */}
                                    <button disabled={loading} type="submit" className="btn btn-lg btn-primary"
                                    >Login</button>
                                </div>
                            </Fragment>
                        </Form>

                    </Formik>
                </div>

            </div>

        </div>
    </>)
}

export default Login
