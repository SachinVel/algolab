
import React, { useEffect, useState } from 'react';
import { Alert, Box, Button, CircularProgress, Snackbar, Stack, Typography } from "@mui/material";
import styles from './course.module.css';
import Header from '../../components/Header';
import AddIcon from '@mui/icons-material/Add';

export default function Course() {

    const [courses, setCourses] = useState([]);
    const [role, setRole] = useState('');

    useEffect(() => {
        let role = window.localStorage.getItem('role');
        setRole(role);
    }, []);




    return (
        <>
            <Header />
            <Stack className={styles.bodyContainer}>
                <Stack flexDirection="row" justifyContent="right" sx={{ padding: '20px' }}>
                    <Button variant='contained'>
                        <AddIcon /> Course
                    </Button>
                </Stack>

            </Stack>
        </>
    );
}