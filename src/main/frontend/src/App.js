import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Container, Grid, CssBaseline } from '@material-ui/core';
import { ThemeProvider } from '@material-ui/styles';
import { createMuiTheme } from '@material-ui/core/styles';

// import CurrencyCard from './components/CurrencyCard';
import Header from './components/header';
import EntityCard from './components/entityCard';

import './App.css';

// export const API = 'https://api.exchangeratesapi.io/latest';

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
    // const [rates, setRates] = useState({ EUR: {}, USD: {}, GBP: {} });
    const [selected, setSelected] = useState({  });

    useEffect(() => {
        async function getRate() {
            // const rateData = await fetchData(defaultCurrencies, selected.origin);

            // setRates(prevState => ({ ...prevState, ...rateData }));
        }

        // if () {
        //     getRate();
        // }

    }, [selected.origin]); // eslint-disable-line react-hooks/exhaustive-deps

    // const handleInputChange = event => {
    //     setSelected({ ...selected, value: parseInt(event.target.value) });
    // };

    // const handleTabChange = (source, value) => {
    //     setSelected({ ...selected, [source]: value });
    // };

    return (
        <ThemeProvider theme={theme}>
            <Header />

            <Container className="App" maxWidth={false}>
                <Grid container className="Grid" justify="space-around" alignItems="flex-start" spacing={4}>
                    <Grid item xs={12} className="title" />
                        {/* */}
                    {/* </Grid> */}

                    <Grid item sm={12} md={6}>
                        <EntityCard />
                        {/* <CurrencyCard selected={selected} rates={rates} onChange={handleInputChange} tabChange={handleTabChange} /> */}
                    </Grid>

                    <Grid item sm={12} md={6}>
                        321
                        {/* <CurrencyCard selected={selected} rates={rates} readOnly={true} tabChange={handleTabChange} /> */}
                    </Grid>
                </Grid>
            </Container>

            <CssBaseline />
        </ThemeProvider>
    );
}
