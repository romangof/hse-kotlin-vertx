import React from 'react';
import { AppBar, Toolbar, Typography } from '@material-ui/core';

export default function Header() {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h5" color="inherit">
                    HSE24
                </Typography>
            </Toolbar>
        </AppBar>
    );
}