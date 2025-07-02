// @ts-nocheck

// import 'bootstrap/dist/css/bootstrap.min.css';
import { useEffect } from 'react';
import { Provider, useSelector } from 'react-redux';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { PersistGate } from 'redux-persist/integration/react';
import './App.css';
import DialogDemo from './components/DialogDemo';
import DynamicTable from './components/DynamicTable';
import TableDemo from './components/TableDemo';
import TemplateDemo from './components/UploadDemo';
import AlbumTable from './components/table/AlbumTable';
import UserTable from './components/table/UserTable';
import { store, persistor, RootStateType } from './redux/store';
import ClassDetail from './components/main/user/teacher/ClassDetail';
import StudentPage from './components/main/user/student/StudentPage';
import AdminPage from './components/main/user/admin/AdminPage';
import Login from './components/main/user/Login';
import ScheduleDetail from './components/main/user/admin/ScheduleDetail';
import { SideBarTabs, SideBarType } from './types/type';
import { adminSidebarTabs, sidebarComponents, studentSidebarTabs, TeacherSidebarTabs } from './configurations/common/sidebar';
import { urlRolePrefix } from './configurations/common/navigate';
import TeacherPage from './components/main/user/teacher/TeacherPage';
import { verifyToken } from './services/authenticationService';


const generateSidebarRoutes = (tabs: SideBarType[], roleTab: string): JSX.Element[] => {
    return tabs.flatMap((tab) => {
        const Component = sidebarComponents[roleTab][tab.componentName as SideBarTabs];

        const routes: JSX.Element[] = [];

        if (Component && tab.linkUrl) {
            routes.push(
                <Route
                    key={tab.linkUrl}
                    path={tab.linkUrl}
                    element={<Component />}
                />
            );
        }

        if (tab.children?.length) {
            routes.push(...generateSidebarRoutes(tab.children));
        }

        return routes;
    });
};

function App() {
    // useEffect(() => {
    //   const interval = setInterval(async () => {
    //     const _isValidToken = await isValidToken();
    //     if (!_isValidToken) {
    //       removeToken();
    //       if (window.location.pathname !== '/user/login')
    //         window.location.href = '/user/login';
    //     }
    //   }, 2000);
    //   localStorage.setItem('checkTokenInterval', interval);
    //   return () => {
    //     console.log("App component is unmounted");
    //     clearInterval(interval);
    //   }
    //   // eslint-disable-next-line react-hooks/exhaustive-deps
    // });

    const user = useSelector((state: RootStateType) => state.user);
    console.log("User from Redux Store:", user, user && user.roles && user.roles.includes("STUDENT"));

    return (
        <>


            <BrowserRouter>
                <Routes>
                    <Route path="/dynamic-table" element={<DynamicTable />} />
                    <Route path="/dialog-demo" element={<DialogDemo />} />
                    <Route path='/table-demo' element={<TableDemo />} />
                    <Route path='/upload-demo' element={<TemplateDemo />} />
                    <Route path="/user-table" element={<UserTable />} />
                    <Route path='/album-table' element={<AlbumTable />} />
                    <Route path='/user/login' element={<Login />} />
                    {/* <Route path='/user/profile' element={<UserProfile />} /> */}
                    {user && user.roles && user.roles.includes("ADMIN") && (
                        <Route path={urlRolePrefix.admin} element={<AdminPage />} >
                            {generateSidebarRoutes(adminSidebarTabs, "adminSidebarComponents")}
                        </Route>)}
                    {user && user.roles && user.roles.includes("STUDENT") && (
                        <Route path="/user/student" element={<StudentPage />} >
                            {generateSidebarRoutes(studentSidebarTabs, "studentSidebarComponents")}
                        </Route>
                    )}
                    {user && user.roles && user.roles.includes("TEACHER") && (
                        <Route path={urlRolePrefix.teacher} element={<TeacherPage />} >
                            {generateSidebarRoutes(TeacherSidebarTabs, "teacherSidebarComponents")}
                        </Route>
                    )}
                    <Route path="/user/admin/schedule-detail/*" element={<ScheduleDetail />} />
                    <Route path="/user/teacher/schedule" element={<Scheduler />} />
                    <Route path='/user/teacher/class-detail/*' element={<ClassDetail />} />
                    <Route path='*' element={(() => {
                        if(window.location.pathname !== '/user/login') {
                            verifyToken();
                        }
                        return (<>

                            <h1>404 Not Found</h1>
                            <p>The page you are looking for does not exist.</p>
                        </>)
                    })()} />

                </Routes>

            </BrowserRouter>
        </>
    )
}

export default App
