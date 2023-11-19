
import React from 'react';
import { Alert, Box, CircularProgress, Snackbar } from "@mui/material";
import { BrowserRouter, Routes, Route, Navigate, Router } from "react-router-dom";
import { lazy, Suspense, useEffect, useState } from "react";

export default function Home() {

    const greeting = 'Hello Function Component!';

    return (
        <Box>
            <h1>Home Component</h1>
        </Box>
    );
}