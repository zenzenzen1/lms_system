import React, { useState } from 'react'
import { Link, Navigate } from 'react-router-dom'
import Login from './Login'
import { insertUser } from '../../../services/UserService'
import { Button } from 'primereact/button'

const Register = () => {
    const [user, setUser] = useState({});

    const handleSignUp = async () => {
        const response = await insertUser(user);
        console.log(response);
        
    }
    console.log(user);

    return (
        <div>
            <div className="account-form-inner">
                <div className="account-container">
                    <div className="heading-bx left">
                        <h2 className="title-head">
                            Sign Up <span>Now</span>
                        </h2>
                        <p>
                            Login Your Account{" "}
                            <Link to={"/user/login"} >Click here</Link>
                        </p>
                    </div>
                    <form className="contact-bx">
                        <div className="row placeani">
                            <div className="col-lg-12">
                                <div className="form-group">
                                    <div className="input-group">
                                        <label>Your Username</label>
                                        <input
                                            name="username"
                                            id="username"
                                            type="text"
                                            required=""
                                            className="form-control"
                                            value={user.username}
                                            onChange={(e) => { setUser(user => { return { ...user, username: e.target.value } }) }}
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
                                            value={user.password}
                                            onChange={(e) => { setUser(user => { return { ...user, password: e.target.value } }) }}
                                        />
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-12">
                                <div className="form-group">
                                    <div className="input-group">
                                        <label>Your Fullname</label>
                                        <input
                                            name="fullname"
                                            id="fullname"
                                            type="text"
                                            required=""
                                            className="form-control"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-12">
                                <div className="form-group">
                                    <div className="input-group">
                                        <label>Your Email Address</label>
                                        <input
                                            id="email"
                                            name="email"
                                            type="text"
                                            required=""
                                            className="form-control"
                                        />
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-12">
                                <div className="form-group">
                                    <div className="input-group">
                                        <label>Your Mobile Number</label>
                                        <input
                                            name="phone"
                                            id="phone"
                                            type="text"
                                            className="form-control"
                                        />
                                    </div>
                                </div>
                            </div>
                           
                            <div className="col-lg-12 m-b30">
                                <Button active
                                    name="submit"
                                    type="button"
                                    value="Submit"
                                    className="btn button-md"
                                    onClick={handleSignUp}
                                >
                                    Sign Up
                                </Button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

        </div>
    )
}

export default Register
