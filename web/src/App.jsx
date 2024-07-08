import { BrowserRouter, Routes } from 'react-router-dom'
import { Route } from 'react-router-dom'
import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Login from './pages/user/Login'
import Register from './pages/user/Register'

function App() {
  const [count, setCount] = useState(0)

  return (
    <>
      <BrowserRouter>
        <Routes>
          {/* <Route path='/' element={<Home />} /> */}
          <Route path='/user/login' element={<Login />} />
          <Route path='/user/register' element={<Register />} />
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
