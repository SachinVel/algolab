
import React, { useEffect, useState } from 'react';
import { Alert, Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Grid, InputLabel, List, ListItem, ListItemText, MenuItem, Paper, Select, Snackbar, Stack, TextField, ToggleButtonGroup, Typography, styled, } from "@mui/material";
import styles from './lesson.module.css';
import Header from '../../components/Header';
import AddIcon from '@mui/icons-material/Add';
import MuiToggleButton from "@mui/material/ToggleButton";
import backendCall from '../../utils/network';
import { useParams } from 'react-router-dom';


export default function Lesson() {

    const { courseId } = useParams();


    const ToggleButton = styled(MuiToggleButton)({
        "&.Mui-selected, &.Mui-selected:hover": {
            color: "white",
            backgroundColor: '#295bcc',
        }
    });

    const [message, setMessage] = useState('');
    const [isSnackbarOpen, setIsSnackbarOpen] = useState(false);
    const [snackType, setSnackType] = useState(false);

    const [course, setCourse] = useState(null);
    const [lessons, setLessons] = useState([]);
    const [lessonData, setLessonData] = useState(null);
    const [isEditable, setIsEditable] = useState(false);
    const [role, setRole] = useState('');
    const [token, setToken] = useState('');

    const [open, setOpen] = useState(false);
    const [title, setTitle] = useState('');
    const [difficulty, setDifficulty] = useState('');
    const [description, setDescription] = useState('');
    const [image, setImage] = useState(null);

    const [userCourses, setUserCourses] = useState([]);
    const [allCourses, setAllCourses] = useState([]);

    const handleImageChange = (e) => {
        setImage(e.target.files[0]);
    };

    const handleSubmit = async () => {
        handleClose();
    };

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        let role = window.localStorage.getItem('role');
        let token = window.localStorage.getItem('token');
        setToken(token);
        if (token == null || token == '') {
            window.localStorage.removeItem('token');
            window.localStorage.removeItem('role');
            window.location = '/login';
        }
        setRole(role);
        checkCourseInstructor(token).then((isOwner) => {
            if (role == 'ADMIN' || isOwner) {
                setIsEditable(true);
            }
        });

        getCourse(token);
        getAllLessons(token);
        

    }, []);

    const getCourse = (token) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };
        let isOwner = false;
        backendCall.get('/api/v1/course?courseId=' + courseId, config).then((res) => {
            console.log('getCourse response : ', res);
            setCourse(res.data);
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

    const checkCourseInstructor = async (token) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };
        let isOwner = false;
        await backendCall.get('/api/v1/checkCourse?courseId=' + courseId, config).then((res) => {
            console.log('getUserDetails response : ', res);
            isOwner = true;
        }).catch((err) => {
            console.log('login error : ', err);
            if (err.response && err.response.data && err.response.data.error) {
                console.log('err : ', err);
                setMessage(err.response.data.error);
                setSnackType('error');
                setIsSnackbarOpen(true);
            }
        });
        return isOwner;
    }


    const getLesson = async (token, lessonId) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.get('/api/v1/getLesson?lessonId=' + lessonId, config).then((res) => {
            console.log('getUserDetails response : ', res);
            setLessonData(res.data);
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

    const getAllLessons = async (token) => {

        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.get('/api/v1/getLessonPages/' + courseId, config).then((res) => {
            console.log('getLessons response : ', res);
            setLessons(res.data);
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



    const hanldeSnackbarClose = () => {
        setIsSnackbarOpen(false);
    }

    const handleLessonClick = (lessonId) => {
        // setLessonData(lesson);
      };



    return (
        <>
            <Header />
            {
                course !== null &&
                <h1>{course.title}</h1>
            }

            <Grid container spacing={2}>
                <Grid item xs={3}>
                    <Paper elevation={3} style={{ height: '100%', overflow: 'auto' }}>
                        <List>
                            {lessons!='' && lessons.map((lesson) => (
                                <ListItem key={lesson.id} onClick={() => handleLessonClick(lesson.id)}>
                                    <ListItemText primary={lesson.title} />
                                </ListItem>
                            ))}
                        </List>
                    </Paper>
                </Grid>

                <Grid item xs={9}>
                    <Paper elevation={3} style={{ padding: '20px' }}>
                        
                    </Paper>
                </Grid>
            </Grid>

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