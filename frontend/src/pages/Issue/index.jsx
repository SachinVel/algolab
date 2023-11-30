import React, { useEffect, useState } from 'react';
import { Button, TextField, Link, Box, Typography, Stack, InputLabel, Select, MenuItem, Grid, Paper, List, ListItem, ListItemText } from '@mui/material';

import MuiAlert from '@mui/material/Alert';
import { useNavigate } from 'react-router-dom';
import styles from './issue.module.css';
import Snackbar from '@mui/material/Snackbar';
import backendCall from '../../utils/network';

import Header from '../../components/Header';

const Alert = React.forwardRef(function Alert(props, ref) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});


export default function Issue() {

    const [token, setToken] = useState('');
    const [role, setRole] = useState('');

    const [issues, setIssues] = useState([]);
    const [curIssue, setCurIssue] = useState(null);

    const [message, setMessage] = useState('');
    const [isSnackbarOpen, setIsSnackbarOpen] = useState(false);
    const [snackType, setSnackType] = useState(false);


    const hanldeSnackbarClose = () => {
        setIsSnackbarOpen(false);
    }

    useEffect(() => {
        let role = window.localStorage.getItem('role');
        let token = window.localStorage.getItem('token');
        console.log('token : ', token)
        setToken(token);
        if (token == null || token == '') {
            window.localStorage.removeItem('token');
            window.localStorage.removeItem('role');
            window.location = '/login';
        }
        if (role !== 'ADMIN') {
            window.location = '/course';
        }
        setRole(role);
        getIssues(token);
    }, []);

    const getIssues = async (token) => {

        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.get('/api/v1/issues', config).then((res) => {
            console.log('getIssues response : ', res);
            setIssues(res.data);
        }).catch((err) => {
            console.log('login error : ', err);
            if (err.response && err.response.data && err.response.data.error) {
                console.log('err : ', err);
                setMessage(err.response.data.error);
                setSnackType('error');
                setIsSnackbarOpen(true);
            }
        });
    }

    const getIssueById = async (token, issueId) => {

        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.get('/api/v1/issues/' + issueId, config).then((res) => {
            setCurIssue(res.data);
        }).catch((err) => {
            console.log('err : ', err);
            setMessage(err.response.data.error);
            setSnackType('error');
            setIsSnackbarOpen(true);
        });



    }

    const handleIssueClick = (issue) => {
        getIssueById(token, issue.id);
    };

    const deleteIssue = (issueId) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };
        backendCall.delete('/api/v1/issues/' + issueId, config).then((res) => {
            getIssues(token);
        }).catch((err) => {
            console.log('err : ', err);
            setMessage(err.response.data.error);
            setSnackType('error');
            setIsSnackbarOpen(true);
        });
    }

    return (
        <>
            <Header />
            <Box className={styles.bodyContainer}>
                <Grid container spacing={2}>
                    {/* Left Pane (30%) */}
                    <Grid item xs={3}>
                        <Paper elevation={3} style={{ height: '100%', overflow: 'auto' }}>
                            <List>
                                {issues.map((issue) => (
                                    <ListItem key={issue.id} onClick={() => handleIssueClick(issue)}>
                                        <ListItemText primary={issue.name} />
                                        <Button onClick={()=>{deleteIssue(issue.id)}}>Delete</Button>
                                    </ListItem>
                                ))}
                            </List>
                        </Paper>
                    </Grid>

                    {/* Right Pane (70%) */}
                    <Grid item xs={9}>
                        <Paper elevation={3} style={{ padding: '20px', height: '100%' }}>
                            {curIssue ? (
                                <div>
                                    <Typography variant="h5">User:{curIssue.user.username}</Typography>
                                    <Typography variant="h5">Title:{curIssue.name}</Typography>
                                    <Typography variant="body1">Description:{curIssue.description}</Typography>
                                    <Typography variant="body1">Severity:{curIssue.severity}</Typography>

                                </div>
                            ) : (
                                <Typography variant="body1">Select an issue to view details.</Typography>
                            )}
                        </Paper>
                    </Grid>
                </Grid>
            </Box>

            <Snackbar
                open={isSnackbarOpen}
                autoHideDuration={4000}
                onClose={hanldeSnackbarClose}
                disableWindowBlurListener={true}
            >
                <Box>
                    {
                        message != '' &&
                        <>
                            <Alert onClose={hanldeSnackbarClose} severity={snackType}>
                                {message}
                            </Alert>
                            <br></br>
                        </>
                    }
                </Box>

            </Snackbar>
        </>


    );
}
