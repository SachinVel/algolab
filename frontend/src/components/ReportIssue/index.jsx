import React, { useEffect, useState } from 'react';
import {
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    TextField,
    DialogActions,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
} from '@mui/material';
import backendCall from '../../utils/network';

const ReportIssue = () => {
    const [open, setOpen] = useState(false);
    const [issueTitle, setIssueTitle] = useState('');
    const [issueDescription, setIssueDescription] = useState('');
    const [severity, setSeverity] = useState('MINOR');
    const [token, setToken] = useState(null);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        let token = window.localStorage.getItem('token');
        setToken(token);

    }, []);

    const handleReportIssue = async() => {

        if (issueTitle != '' && issueDescription != '') {
            let data = {
                severity: severity,
                name: issueTitle,
                description: issueDescription
            }
            const response = await backendCall.post('/api/v1/reportIssue', data, {
                headers: {
                    'Authorization': 'Bearer ' + token, // Replace with your actual access token
                },
            });
            handleClose();
        }

        
        
    };

    return (
        <div>
            <Button variant="contained" color="primary" onClick={handleOpen}>
                Report Issue
            </Button>

            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Report Issue</DialogTitle>
                <DialogContent>
                    <TextField
                        label="Issue Title"
                        fullWidth
                        margin="normal"
                        value={issueTitle}
                        onChange={(e) => setIssueTitle(e.target.value)}
                    />
                    <FormControl fullWidth margin="normal">
                        <InputLabel id="severity-label">Severity</InputLabel>
                        <Select
                            labelId="severity-label"
                            id="severity"
                            value={severity}
                            onChange={(e) => setSeverity(e.target.value)}
                        >
                            <MenuItem value="MINOR">Minor</MenuItem>
                            <MenuItem value="MODERATE">Moderate</MenuItem>
                            <MenuItem value="CRITICAL">Critical</MenuItem>
                        </Select>
                    </FormControl>
                    <TextField
                        label="Issue Description"
                        fullWidth
                        multiline
                        rows={4}
                        margin="normal"
                        value={issueDescription}
                        onChange={(e) => setIssueDescription(e.target.value)}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="secondary">
                        Cancel
                    </Button>
                    <Button onClick={handleReportIssue} color="primary">
                        Report
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
};

export default ReportIssue;
