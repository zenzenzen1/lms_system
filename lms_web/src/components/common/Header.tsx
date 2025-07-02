// @ts-nocheck

import { TfiAlignLeft } from "react-icons/tfi";
import { FaRegBell, FaSignOutAlt } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { logOut } from "../../services/authenticationService";

const Header = ({setShowSideBar} : {setShowSideBar: React.Dispatch<React.SetStateAction<boolean>>}) => {
    const navigate = useNavigate();
    return (
        <>

            <div className='h-auto bg-white py-2.5 sticky top-0 -z-0 shadow-neutral-400 '>
                <div className="flex items-center justify-between h-full px-4">
                    <div className="flex items-center">
                        <a className="sidebar-toggle js-sidebar-toggle "
                            onClick={() => { setShowSideBar((prev) => !prev) }}
                        >
                            <TfiAlignLeft size={24} className="text-black hover:text-blue-500!" />
                        </a>
                    </div>
                    <div className="flex items-center gap-4">
                        <div className="relative cursor-pointer">
                            <FaRegBell size={20} className="text-gray-600 hover:text-blue-500 " />
                            {/* <span className="absolute p-2 -top-2 -right-1.5 inline-flex items-center justify-center w-3 h-3 text-sm font-medium 
                                        text-white bg-[#3b7ddd] rounded-full hover:transition-[top] ease-out delay-100" >
                                3
                            </span> */}
                        </div>
                        {/* <div>
                            <img src="/img/flags/us.png" alt="US Flag" className="inline-block w-5 h-5 rounded-full" />
                        </div> */}
                        <div>
                            <img src="/img/avatars/avatar.jpg" className="w-10 h-10" />
                        </div>
                        <div>
                            <FaSignOutAlt className="text-gray-500 hover:text-blue-500 cursor-pointer" size={20} 
                                onClick={() => {
                                    logOut();
                                }}
                            />
                        </div>
                    </div>
                </div>
            </div>

        </>
    )
}

export default Header