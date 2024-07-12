import React from 'react'
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom'

const UserHeader = () => {
    const currentUser = useSelector(state => state.user);
    console.log(currentUser);
    
    const handleLogout = () => {
        
    }
    return (
        // eslint-disable-next-line react/prop-types
        <div>
            <header className='bg-slate-200 shadow-md '>

                <div className='flex justify-between place-items-center p-3 max-w-6xl mx-auto h-9'>
                    <h1 className='font-bold text-sm sm:text-xl flex flex-wrap '>
                        <span className='text-slate-500'>aaa</span>
                        <span className='text-slate-700'>bbb</span>
                    </h1>
                    {/* <form className='bg-slate-100 p-2 rounded-lg flex items-center '>
                        <input placeholder='search...' className='bg-transparent focus:outline-none w-24 sm:w-64'
                            onClick={(e) => {
                                setSearch(e.target.value);
                            }} />
                    </form> */}
                    <ul className='flex gap-4'>
                        <Link to={"/"}>
                            <li className='hidden sm:inline hover:underline'>Home</li>
                        </Link>
                        <Link to={"/about"}>
                            <li className='hidden sm:inline hover:underline'>About</li>
                        </Link>
                        {currentUser ?
                            <>
                                <Link to={"/user/profile"}>
                                    <li>Profile</li>
                                </Link>
                                <li className="hidden sm:inline" onClick={handleLogout}>Logout</li>
                            </>
                            :
                            <Link to={"/sign-in"}>
                                <li>Sign in</li>
                            </Link>

                        }

                    </ul>
                </div>
            </header>
        </div>
    )
}

export default UserHeader
