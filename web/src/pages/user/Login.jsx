import React from 'react'
import { login } from '../../services/AuthenticationService'

const Login = () => {
    const [username, setUsername] = React.useState("")
    const [password, setPassword] = React.useState("")
    
    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const res = await login(username, password);
            console.log(res);
        } catch (error) {
            console.log(error);
        }
    }
    
    return (
        <div>
            <form className="contact-bx">
                <div className="row placeani">
                    <div className="col-lg-12">
                        <div className="form-group">
                            <div className="input-group">
                                <label>Your User Name</label>
                                <input
                                    name="username"
                                    id="username"
                                    type="text"
                                    required=""
                                    className="form-control"
                                    value={username}
                                    onChange={(e) => {setUsername(e.target.value)}}
                                />
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-12">
                        <div className="form-group">
                            <div className="input-group">
                                <label>Your Password</label>
                                <input
                                    name="password"
                                    id="password"
                                    type="password"
                                    className="form-control"
                                    required=""
                                    value={password}
                                    onChange={(e) => {setPassword(e.target.value)}}
                                />
                            </div>
                        </div>
                    </div>
                    <div className="col-lg-12">
                        <div className="form-group form-forget">
                            <div className="custom-control custom-checkbox">
                                <input
                                    type="checkbox"
                                    className="custom-control-input"
                                    id="customControlAutosizing"
                                />
                            </div>
                            <a href="forget-password" className="ml-auto">
                                Forgot Password?
                            </a>
                        </div>
                    </div>
                    <div className="col-lg-12 m-b30">
                        <button
                            id="lms-login-btn"
                            name="submit"
                            value="Submit"
                            className="btn button-md"
                            onClick={handleLogin}
                        >
                            Login
                        </button>
                    </div>
                </div>
            </form>

        </div>
    )
}

export default Login
