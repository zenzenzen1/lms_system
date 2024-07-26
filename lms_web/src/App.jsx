import 'bootstrap/dist/css/bootstrap.min.css';
import { useEffect } from 'react';
import { Provider } from 'react-redux';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { PersistGate } from 'redux-persist/integration/react';
import './App.css';
import DialogDemo from './components/DialogDemo';
import DynamicTable from './components/DynamicTable';
import TableDemo from './components/TableDemo';
import TemplateDemo from './components/UploadDemo';
import AlbumTable from './components/table/AlbumTable';
import UserTable from './components/table/UserTable';
import Login from './pages/user/Login';
import AdminPage from './pages/user/admin/AdminPage';
import UserProfile from './pages/user/common/UserProfile';
import StudentPage from './pages/user/student/StudentPage';
import TeacherPage from './pages/user/teacher/TeacherPage';
import { persistor, store } from './redux/store';
import { isValidToken } from './services/authenticationService';
import ClassDetail from './pages/user/teacher/ClassDetail';
import { removeToken } from './services/localStorageService';
import Scheduler from './pages/user/teacher/Scheduler';
function App() {
  useEffect(() => {
    const interval = setInterval(async () => {
      const _isValidToken = await isValidToken();
      if (!_isValidToken) {
        removeToken();
        if (window.location.pathname !== '/user/login')
          window.location.href = '/user/login';
      }
    }, 20000);
    return () => {
      console.log("App component is unmounted");
      clearInterval(interval);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  });
  return (
    <>
      <Provider store={store}>
        <PersistGate loading={null} persistor={persistor}>

          <BrowserRouter>
            <Routes>
              <Route path="/dynamic-table" element={<DynamicTable />} />
              <Route path="/dialog-demo" element={<DialogDemo />} />
              <Route path='/table-demo' element={<TableDemo />} />
              <Route path='/upload-demo' element={<TemplateDemo />} />
              <Route path="/user-table" element={<UserTable />} />
              <Route path='/album-table' element={<AlbumTable />} />
              <Route path='/user/login' element={<Login />} />
              <Route path='/user/profile' element={<UserProfile />} />
              <Route path="/user/admin" element={<AdminPage />} />
              <Route path="/user/student" element={<StudentPage />} />
              <Route path="/user/teacher" element={<TeacherPage />} />
              {/* <Route path="/user/teacher/schedule" element={<Scheduler />} /> */}
              <Route path='/user/teacher/class-detail/*' element={<ClassDetail />} />
            </Routes>
          </BrowserRouter>
        </PersistGate>
      </Provider>
    </>
  )
}

export default App
