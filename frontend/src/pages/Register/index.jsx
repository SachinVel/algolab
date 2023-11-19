import React, { useState } from 'react';
import { Button, TextField, Link, Typography, Box, TextareaAutosize, Stack, InputLabel, Select, MenuItem } from '@mui/material';

import Snackbar from '@mui/material/Snackbar';
import MuiAlert from '@mui/material/Alert';
import { backendCall } from "../../utils/network";
import { useNavigate } from 'react-router-dom';

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function Register({ setLoggedIn }) {

  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastname, setLastName] = useState('');
  const [role, setRole] = useState(null);
  const [email, setEmail] = useState('');
  const [bio, setBio] = useState('');
  const [errorMesage, setErrorMessage] = useState('');
  const [isErrSnackbarOpen, setIsErrSnackbarOpen] = useState(false);

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

    if (password !== confirmPassword) {
      setErrorMessage('Passwords do not match');
      setIsErrSnackbarOpen(true);
      return;
    }

    await backendCall.post('/user/register', {
      username: username,
      password: password,
    }).then((res) => {
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
              value={lastname}
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
            <MenuItem value='instructor'>Instructor</MenuItem>
            <MenuItem value='student'>Student</MenuItem>
          </Select>

          <br /><br />

          <TextField
            id="standard-basic"
            label="Bio"
            multiline
            rows={3}
            value={bio}
            onChange={onBioChange}
            sx={{ width: "400px" }}
            defaultValue=""
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
      <Snackbar open={isErrSnackbarOpen} autoHideDuration={4000} onClose={hanldeErrSnackbarClose}>
        <Alert severity="error" onClose={hanldeErrSnackbarClose}>{errorMesage}</Alert>
      </Snackbar>
    </>


  );
}
