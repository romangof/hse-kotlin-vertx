import React, { useState, useEffect } from 'react';
// import { DeleteIcon } from '@material-ui/icons';
import { makeStyles, createStyles } from '@material-ui/core/styles';
import { Paper, IconButton, List, ListItem, ListItemText, ListItemSecondaryAction } from '@material-ui/core';
import DeleteIcon from '@material-ui/icons/Delete';

import axios from 'axios';
import _ from 'lodash';

import CardHeader from './cardHeader';

const useStyles = makeStyles(theme => createStyles({
    root: {
        padding: theme.spacing(3),
    }
}));

async function makeRequest(method, route, data) {
    const request = await axios({
        method, data,
        url: '/api/' + route
    });

    return request.data;
}

export default function EntityCard({entity}) {
    const classes = useStyles();

    const [entityList, setEntityList] = useState([]);

    useEffect(() => {
        async function getEntityList() {
            const data = await makeRequest('get', entity || 'categories');

            setEntityList(prevState => ([ ...prevState, ...data ]));
        }

        getEntityList();
    }, [entity]);


    const addEntity = async (value) => {
        const data = await makeRequest('post', entity || 'categories', value)

        setEntityList(prevState => ([...prevState, data]));
    }

    const removeEntity = async (value) => {
        const data = await makeRequest('delete', `${entity || 'categories'}/${value}`)

        setEntityList(prevState => _.reject(prevState, data));
    }

    return (
        <Paper elevation={3} className={classes.root}>
            <CardHeader addEntity={addEntity} />

            <List component="nav" className={classes.root} aria-label="contacts">
                {_.map(entityList, object => (
                    <ListItem button>
                        {/* Missing edit in here */}
                        <ListItemText primary={object.name} secondary={object.description} />
                        {/* Need to fix id generation in backend so there's no repeated ids */}
                        <ListItemSecondaryAction onClick={() => removeEntity(object.id)}>
                            <IconButton edge="end" aria-label="delete">
                                <DeleteIcon />
                            </IconButton>
                        </ListItemSecondaryAction>
                    </ListItem>
                ))}
            </List>
        </Paper>
    );
}
