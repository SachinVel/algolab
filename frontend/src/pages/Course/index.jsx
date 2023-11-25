
import React from 'react';
import { Alert, Box, Button, CircularProgress, Snackbar, Stack, Typography } from "@mui/material";
import { BrowserRouter, Routes, Route, Navigate, Router } from "react-router-dom";
import { lazy, Suspense, useEffect, useState } from "react";
import styles from './course.module.css';
import Header from '../../components/Header';

export default function Course() {

    const greeting = 'Hello Function Component!';



    return (
        <>
            <Header />
            
            <Stack className={styles.bodyContainer}>

            </Stack>
        </>
    );
}