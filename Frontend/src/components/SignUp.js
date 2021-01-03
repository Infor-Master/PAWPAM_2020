import React, { useState } from 'react';
import { connect } from 'react-redux';

import * as actions from '../store/actions/index';

import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import { BiArrowBack } from "react-icons/bi";

import { useStyles } from './Styles';

const SignUp = props => {

    // styles
    const classes = useStyles();

    // state
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [nif, setNif] = useState('');


    const onSubmitHandler = (event) => {
        event.preventDefault();
        props.onSignUp(username, password, name, nif);
    }

    let error = props.error ? <label style={{ color: 'red' }}>Login failed!</label> : null;

    return (
        <Container maxWidth="sm" >
            <Grid>
                <Box boxShadow={3} style={{ padding: '50px' }}>
                    <form onSubmit={onSubmitHandler} className={classes.authTextFileds} noValidate autoComplete="off">
                        
                        <div style={{marginTop: 10}} >
                            <Button  variant="contained" color="primary" href="/login"><BiArrowBack/></Button>
                        </div>

                        <h2>SignUp</h2>
                        <div>
                            <TextField required id="standard-basic" label="Username" onChange={event => {
                                setUsername(event.target.value);
                            }} />
                        </div>
                        <div>
                            <TextField required id="standard-password-input" label="Password" type="password" onChange={event => {
                                setPassword(event.target.value);
                            }} />
                        </div>
                        <div>
                            <TextField required id="standard-basic" label="Name" onChange={event => {
                                setName(event.target.value);
                            }} />
                        </div>
                        <div>
                            <TextField required id="standard-basic" label="Nif" onChange={event => {
                                setNif(event.target.value);
                            }} />
                        </div>
                        {error}
                        <div>
                            <Button type="submit" variant="contained" color="primary">Signup</Button>
                        </div>
                    </form>
                </Box>
            </Grid>
        </Container >
    );
}

// actions to reducer (dispatch)
const mapDispatchToProps = (dispatch) => {
    return {
        onSignUp: (username, password, name, nif) => dispatch(actions.signup(username, password, name, nif))
    };
}

export default connect(null, mapDispatchToProps)(SignUp);