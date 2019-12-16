import axios from 'axios';
import React from 'react';
import { Container, Grid, CssBaseline } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/styles';
import { createMuiTheme } from '@material-ui/core/styles';

import Header from './components/header';
import EntityCard from './components/entityCard';

import './App.css';

const theme = createMuiTheme({
    palette: {
        type: 'dark',
        primary: { main: '#FAAB1A' },
        secondary: { main: '#FFFFFF' },
    },
    typography: {
        fontFamily: [
            'Roboto',
            'Arial',
            'sans-serif'
        ].join(','),
    }
});

export async function fetchData(route) {
    const request = await axios.get(route);

    return request.data;
}

export default function App() {   
    return (
        <ThemeProvider theme={theme}>
            <Header />

            <Container className="App" maxWidth={false}>
                <Grid container className="Grid" justify="space-around" alignItems="flex-start" spacing={4}>
                    <Grid item xs={12} className="title" />

                    <Grid item sm={12} md={6}>
                        <EntityCard entity="category" />
                    </Grid>

                    <Grid item sm={12} md={6}>
                        <EntityCard entity="product" />
                    </Grid>
                </Grid>
            </Container>

            <CssBaseline />
        </ThemeProvider>
    );
}
