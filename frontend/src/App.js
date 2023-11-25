
import './App.css';
import { Alert, Box, CircularProgress, Snackbar } from "@mui/material";
import { BrowserRouter, Routes, Route, Navigate, Router } from "react-router-dom";
import React, { lazy, Suspense, useEffect, useState } from "react";
// import AppLayout from './components/AppLayout';
// import Sidebar from './components/Sidebar';

const Login = lazy(() => import("./pages/Login"));
const Register = lazy(() => import("./pages/Register"));
const Logout = lazy(() => import("./pages/Logout"));
const Course = lazy(() => import("./pages/Course"));

function App() {

  const [loggedIn, setLoggedIn] = useState(false);

  useEffect(() => {
    let userToken = localStorage.getItem('token');
    console.log('userToken : ', userToken);
    if (userToken != null && userToken != '') {
      setLoggedIn(true);
    } else {
      setLoggedIn(false);
    }
  }, []);


  return (

    <>
      <BrowserRouter>
        <Suspense
          fallback={
            <Box className="display-center">
              <CircularProgress sx={{ margin: "auto" }} />
            </Box>
          }>
          <Routes>
            <Route path="/" exact element={loggedIn ? <Navigate to='/course' state={{ isLoggedIn: true }} /> : <Navigate to='/login' state={{ showLoginNecessary: true }} />} />
            <Route path="/login" exact element={loggedIn ? <Navigate to='/course' state={{ isLoggedIn: true }} /> : <Login setLoggedIn={setLoggedIn} />} />
            <Route path="/register" element={<Register setLoggedIn={setLoggedIn} />} />
            <Route path="/logout" element={<Logout setLoggedIn={setLoggedIn} />} />
            <Route path="/course" element={loggedIn ? <Course /> : <Navigate to='/login' state={{ showLoginNecessary: true }} />} />
          </Routes>
        </Suspense>
      </BrowserRouter>
    </>





  );
}

export default App;
