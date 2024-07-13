import './App.css'
import { BrowserRouter, Route, Routes } from "react-router-dom";
import DynamicTable from './components/DynamicTable';
import DialogDemo from './components/DialogDemo';
import TableDemo from './components/TableDemo';
import TemplateDemo from './components/UploadDemo';
import UserTable from './components/table/UserTable';
import AlbumTable from './components/table/AlbumTable';
import Login from './pages/user/Login';
import { Provider } from 'react-redux';
import { persistor, store } from './redux/store';
import AdminPage from './pages/user/admin/AdminPage';
import { PersistGate } from 'redux-persist/integration/react';
import UserProfile from './pages/user/common/UserProfile';
import StudentPage from './pages/user/student/StudentPage';
import TeacherPage from './pages/user/teacher/TeacherPage';
import 'bootstrap/dist/css/bootstrap.min.css';
function App() {

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
              <Route path='/user/profile' element={<UserProfile/>} />
              <Route path="/user/admin" element={<AdminPage />} />
              <Route path="/user/student" element={<StudentPage/>} />
              <Route path="/useruser/teacher" element={<TeacherPage/>} />
            </Routes>
          </BrowserRouter>
        </PersistGate>
      </Provider>
    </>
  )
}

export default App
