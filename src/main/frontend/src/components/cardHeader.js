import React from 'react';
import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import { AppBar, Button, Toolbar, FormControl, Typography, TextField } from '@material-ui/core';

const useStyles = makeStyles(theme => ({
    root: {
        background: theme.palette.primary.dark
    },
    toolbar: {
        justifyContent: 'space-between'
    },
    formControl: {
        minWidth: 120,
        padding: theme.spacing(1),
        flexDirection: "row"
    }
}));

export default function CardHeader({ addEntity, entityName }) {
    const classes = useStyles();

    const [entity, setEntity] = React.useState('');

    const handleChange = event => setEntity(event.target.value);

    const onClick = () => {
        entity && addEntity({ name: entity });
        setEntity('');
    }

    return (
        <AppBar position="static" className={classes.root}>
            <Toolbar className={classes.toolbar}>

                <Typography variant="h5" gutterBottom>
                    {entityName}
                </Typography>

                <FormControl className={classes.formControl}>
                    <TextField
                        variant="outlined"
                        label="New"
                        color="secondary"
                        value={entity}
                        onChange={handleChange}
                    />

                    <Button variant="contained" color="secondary" onClick={onClick}>
                        Add
                    </Button>
                </FormControl>

            </Toolbar>
        </AppBar>
    );
}

CardHeader.propTypes = {
    addEntity: PropTypes.func.isRequired,
    entityName: PropTypes.string.isRequired
};