import React, {useEffect, useState} from 'react';
import './announcement.module.css';
import Header from "../../components/Header";
import styles from "../Announcement/announcement.module.css";
import {
    TextField,
    Box,
    Button,
    InputLabel,
    Select,
    MenuItem,
    Alert,
    Typography,
    Accordion,
    AccordionDetails, AccordionSummary, Dialog, DialogTitle, DialogContent, DialogActions
} from "@mui/material";
import backendCall from "../../utils/network";
import Snackbar from "@mui/material/Snackbar";
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import {useParams} from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';

export default function Announcement({}) {
    const [token, setToken] = useState('');
    const [role, setRole] = useState('');

    const { courseId } = useParams();
    const [username, setUsername] = useState('');
    const [allAnnouncements, setAllAnnouncements] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [announcementTitle, setAnnouncementTitle] = useState('');
    const [announcementContent, setAnnouncementContent] = useState('');
    const [message, setMessage] = useState('');
    const [isSnackbarOpen, setIsSnackbarOpen] = useState(false);
    const [snackType, setSnackType] = useState('success');

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
        getUserDetails(token);

        if (role == 'ADMIN') {
            getAllAnnouncementsByCourse(token);
        }
    }, []);

    const getUserDetails = async (token) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.get('/api/v1/userDetails', config).then((res) => {
            console.log('getUserDetails response : ', res);
            let userData = res.data;
            setUsername(userData.username);
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

    const getAllAnnouncementsByCourse = async (token) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.get('/api/v1/announcement/allAnnouncements/' + courseId, config)
            .then((res) => {
                console.log('getAllAnnouncements response : ', res);
                let allAnnouncements = res.data;
                setAllAnnouncements(allAnnouncements);
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

    const handleOpenModal = () => {
        setIsModalOpen(true);
    };

    const handleCloseModal = () => {
        setIsModalOpen(false);
        setAnnouncementContent('');
        setAnnouncementTitle('');
    };

    const handleCreateAnnouncement = async () => {
        handleCloseModal();
        createAnnouncement(token);
    };

    const createAnnouncement = async (token) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.post('/api/v1/announcement/createAnnouncement', {
            courseId: courseId,
            title: announcementTitle,
            content: announcementContent
        }, config)
            .then((res) => {
                console.log('createAnnouncement response : ', res);
                getAllAnnouncementsByCourse(token);
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

    const handleDeleteAnnouncement = (announcementId) => {
        deleteAnnouncement(token, announcementId);
    }

    const deleteAnnouncement = async (token, announcementId) => {
        let config = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        };

        await backendCall.delete('/api/v1/announcement/deleteAnnouncement/' + announcementId, config)
            .then((res) => {
                console.log('deleteAnnouncement response : ', res);
                getAllAnnouncementsByCourse(token);
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

    return (
        <>
            <Header />
            <>
                <Box>
                    <Typography variant="h5" gutterBottom>
                        Announcements
                    </Typography>
                    {(role === 'ADMIN' || role === 'INSTRUCTOR') && (
                        <Button
                            className="button_style"
                            variant="contained"
                            color="primary"
                            size="small"
                            onClick={handleOpenModal}
                            sx={{ padding: "10px 20px" }}
                        >
                            + Create Announcement
                        </Button>
                    )}
                    {allAnnouncements && allAnnouncements.map((announcementItem) => (
                        <Accordion key={announcementItem.id} style={{paddingBottom: '16px'}}>
                            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                                <Typography style={{fontWeight: 'bold'}}>{announcementItem.title}</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <Typography>{announcementItem.content}</Typography>
                            </AccordionDetails>
                            {(role === 'ADMIN' || role === 'INSTRUCTOR') && (
                                <Button
                                    onClick={() => handleDeleteAnnouncement(announcementItem.id)}
                                    variant="outlined"
                                    color="error"
                                    startIcon={<DeleteIcon />}
                                >
                                    Delete
                                </Button>
                            )}
                        </Accordion>
                    ))}
                </Box>
                <Dialog open={isModalOpen} onClose={handleCloseModal} maxWidth="md" fullWidth>
                    <DialogTitle>Create Announcement</DialogTitle>
                    <DialogContent>
                        <TextField
                            id="announcement-title"
                            multiline
                            value={announcementTitle}
                            onChange={(e) => setAnnouncementTitle(e.target.value)}
                            variant="outlined"
                            placeholder="Announcement Title"
                            fullWidth
                        />
                    </DialogContent>
                    <DialogContent>
                        <TextField
                            id="announcement-content"
                            multiline
                            value={announcementContent}
                            onChange={(e) => setAnnouncementContent(e.target.value)}
                            variant="outlined"
                            placeholder="Announcement Content"
                            rows={8}
                            cols={20}
                            fullWidth
                        />
                    </DialogContent>
                    <DialogActions>
                        <Button
                            onClick={handleCreateAnnouncement}
                            color="primary"
                            variant="contained"
                        >
                            Submit
                        </Button>
                        <Button onClick={handleCloseModal} color="primary">
                            Cancel
                        </Button>
                    </DialogActions>
                </Dialog>
            </>
        </>
    );
}
