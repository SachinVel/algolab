import React, { useState } from 'react';
import { Button, TextField, Link, Typography, Box, TextareaAutosize, Stack, InputLabel, Select, MenuItem } from '@mui/material';

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import backendCall from "../../utils/network";
import { useNavigate } from 'react-router-dom';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function Register({ setLoggedIn }) {

  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [role, setRole] = useState('');
  const [email, setEmail] = useState('');
  const [bio, setBio] = useState('');
  const [errorMesage, setErrorMessage] = useState('');
  const [isErrSnackbarOpen, setIsErrSnackbarOpen] = useState(false);

  const [errors, setErrors] = useState({
    password: '',
    confirmPassword: '',
    bio: '',
    email: '',
    firstName: '',
    lastName: '',
    role: ''
  });

  const validateForm = () => {
    let valid = true;

    // Validate password
    if (password.length < 8) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        password: 'Password must be at least 8 characters.'
      }));
      valid = false;
    } else if (!/(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*()_+])/.test(password)) {
      setErrors((prevErrors) => ({
        ...prevErrors,
        password: 'Password must contain at least one letter, one number, and one special character.'
      }));
      valid = false;
    } else {
      setErrors((prevErrors) => ({ ...prevErrors, password: '' }));
    }

    // Validate confirmPassword
    if (confirmPassword !== password) {
      setErrors((prevErrors) => ({ ...prevErrors, password: 'Passwords do not match.' }));
      valid = false;
    } else {
      setErrors((prevErrors) => ({ ...prevErrors, password: '' }));
    }

    // Validate bio
    if (bio.length > 150) {
      setErrors((prevErrors) => ({ ...prevErrors, bio: 'Bio should be less than 150 characters.' }));
      valid = false;
    } else {
      setErrors((prevErrors) => ({ ...prevErrors, bio: '' }));
    }

    // Validate email
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
      setErrors((prevErrors) => ({ ...prevErrors, email: 'Enter a valid email address.' }));
      valid = false;
    } else {
      setErrors((prevErrors) => ({ ...prevErrors, email: '' }));
    }

    // Validate firstName and lastName
    const nameRegex = /^[a-zA-Z]+$/;
    if (!nameRegex.test(firstName)) {
      setErrors((prevErrors) => ({ ...prevErrors, firstName: 'Only alphabet characters allowed for first name.' }));
      valid = false;
    } else {
      setErrors((prevErrors) => ({ ...prevErrors, firstName: '' }));
    }

    if (!nameRegex.test(lastName)) {
      setErrors((prevErrors) => ({ ...prevErrors, lastName: 'Only alphabet characters allowed for last name.' }));
      valid = false;
    } else {
      setErrors((prevErrors) => ({ ...prevErrors, lastName: '' }));
    }

    // Validate role
    const validRoles = ['STUDENT', 'INSTRUCTOR'];
    if (!validRoles.includes(role)) {
      setErrors((prevErrors) => ({ ...prevErrors, role: 'Invalid role.' }));
      valid = false;
    } else {
      setErrors((prevErrors) => ({ ...prevErrors, role: '' }));
    }

    if (!valid) {
      setIsErrSnackbarOpen(true);
    }
    return valid;
  };

  const navigate = useNavigate();

  const onUsernameChange = (event) => {
    setUserName(event.target.value);
  }

  const onEmailChange = (event) => {
    setEmail(event.target.value);
  }

  const onPasswordChange = (event) => {
    setPassword(event.target.value);
  }

  const onBioChange = (event) => {
    setBio(event.target.value);
  }

  const onRoleChange = (event) => {
    setRole(event.target.value);
  }

  const onFirstNameChange = (event) => {
    setFirstName(event.target.value);
  }

  const onLastNameChange = (event) => {
    setLastName(event.target.value);
  }

  const onConfirmPasswordChange = (event) => {
    setConfirmPassword(event.target.value);
  }

  const register = async () => {

    if (validateForm()) {
      await backendCall.post('/user/register',
        {
          username: username,
          password: password,
          firstName: firstName,
          lastName: lastName,
          email: email,
          role: role,
          bio: bio
        }
      ).then((res) => {
        window.localStorage.removeItem('token');
        setLoggedIn(false);
        navigate('/login', {
          state: { isRegisterSuccess: true }
        });
      }).catch((err) => {
        console.log('err : ', err);
        setErrorMessage(err.response.data.error);
        setIsErrSnackbarOpen(true);
      });
    } else {

    }



  }

  const hanldeErrSnackbarClose = () => {
    setIsErrSnackbarOpen(false);
  }

  return (
    <>
      <Box sx={{
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%, -50%)"
      }}>
        {/* <Header /> */}
        <div>
          <Typography variant="h4" sx={{
            color: "#555",
            margin: "20px"
          }}>Register</Typography>
        </div>

        <Box>
          <Stack direction="row" justifyContent="space-between">
            <TextField
              type="text"
              name="firstname"
              sx={{ width: "180px" }}
              value={firstName}
              onChange={onFirstNameChange}
              placeholder="First Name"
              required
            />
            <TextField
              type="text"
              name="lastname"
              sx={{ width: "180px" }}
              value={lastName}
              onChange={onLastNameChange}
              placeholder="Last Name"
              required
            />





          </Stack>
          <br />
          <TextField
            type="text"
            autoComplete="off"
            name="username"
            sx={{ width: "400px" }}
            value={username}
            onChange={onUsernameChange}
            placeholder="User Name"
            required
            className=''
          />
          <br /><br />
          <TextField
            id="standard-basic"
            type="text"
            name="username"
            sx={{ width: "400px" }}
            value={email}
            onChange={onEmailChange}
            placeholder="Email"
            required
            className=''
          />
          <br /><br />
          <TextField
            id="standard-basic"
            type="password"
            sx={{ width: "400px" }}
            name="password"
            value={password}
            onChange={onPasswordChange}
            placeholder="Password"
            required
          />
          <br /><br />
          <TextField
            id="standard-basic"
            type="password"
            sx={{ width: "400px" }}
            name="confirm_password"
            value={confirmPassword}
            onChange={onConfirmPasswordChange}
            placeholder="Confirm Password"
            required
          />
          <br /><br />

          <InputLabel id="role-label">Role</InputLabel>
          <Select
            labelId="role-label"
            value={role}
            sx={{ width: "400px" }}
            onChange={onRoleChange}
            placeholder='Role'
          // variant='standard'
          >
            <MenuItem value="">Select Role</MenuItem>
            <MenuItem value='INSTRUCTOR'>Instructor</MenuItem>
            <MenuItem value='STUDENT'>Student</MenuItem>
          </Select>

          <br /><br />

          <TextField
            id="standard-basic"
            label="Bio"
            multiline
            value={bio}
            onChange={onBioChange}
            sx={{ width: "400px" }}
            variant="outlined"
            maxRows={10}
          />

          <br /><br />


          <Button
            className="button_style"
            variant="contained"
            color="primary"
            size="small"
            disabled={username === '' || password === ''}
            onClick={register}
            sx={{ padding: "10px 20px" }}
          >
            Register
          </Button>
        </Box>

      </Box>
      <Snackbar
        open={isErrSnackbarOpen}
        autoHideDuration={4000}
        onClose={hanldeErrSnackbarClose}
        disableWindowBlurListener={true}
      >
        <Box>

          {
            errors.firstName &&
            <>
              <Alert onClose={hanldeErrSnackbarClose} severity="error">
                {errors.firstName}
              </Alert>
              <br></br>
            </>

          }

          {
            errors.lastName &&
            <>
              <Alert onClose={hanldeErrSnackbarClose} severity="error">
                {errors.lastName}
              </Alert>
              <br></br>
            </>
          }

          {
            errors.password &&
            <>
              <Alert onClose={hanldeErrSnackbarClose} severity="error">
                {errors.password}
              </Alert>
              <br></br>
            </>
          }

          {
            errors.email &&
            <>
              <Alert onClose={hanldeErrSnackbarClose} severity="error">
                {errors.email}
              </Alert>
              <br></br>
            </>
          }

          {
            errors.role &&
            <>
              <Alert onClose={hanldeErrSnackbarClose} severity="error">
                {errors.role}
              </Alert>
              <br></br>
            </>
          }

          {
            errors.bio &&
            <>
              <Alert onClose={hanldeErrSnackbarClose} severity="error">
                {errors.bio}
              </Alert>
              <br></br>
            </>
          }



        </Box>

      </Snackbar>
    </>


  );
}
