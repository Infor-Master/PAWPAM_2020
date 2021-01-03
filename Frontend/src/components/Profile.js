import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';

import clsx from 'clsx';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import jwt_decode from 'jwt-decode';
import InputAdornment from '@material-ui/core/InputAdornment';
import IconButton from '@material-ui/core/IconButton';
import Visibility from '@material-ui/icons/Visibility';
import VisibilityOff from '@material-ui/icons/VisibilityOff';
import Input from '@material-ui/core/Input';
import FormControl from '@material-ui/core/FormControl';
import InputLabel from '@material-ui/core/InputLabel';


import { useStyles } from './Styles';


import * as actions from '../store/actions/index'

const Profile = props => {

    const classes = useStyles();

    let isAuth = !props.token ? <Redirect to='/' /> : null;

    const [values, setValues] = React.useState({
        userid: jwt_decode(props.token).id,
        username: jwt_decode(props.token).username,
        name: jwt_decode(props.token).name,
        password: '',
        nif: parseInt(jwt_decode(props.token).NIF),
        newPassword: '',
        confirmedNewPassword: '',
    });

    let error = null;

    const handleClickShowPassword = () => {
        setValues({ ...values, showPassword: !values.showPassword });
    };

    const handleClickShowNewPassword = () => {
        setValues({ ...values, showNewPassword: !values.showNewPassword });
    };

    const handleClickShowCOnformedNewPassword = () => {
        setValues({ ...values, showConfirmedNewPassword: !values.showConfirmedNewPassword });
    };

    const handleChange = (prop) => (event) => {
        setValues({ ...values, [prop]: event.target.value });
    };

    const handleMouseDownPassword = (event) => {
        event.preventDefault();
    };

    const onSubmitHandler = (event) => {
        event.preventDefault()
        error = values.password
        if (error == ''){
            <label style={{ color: 'red' }}>Insert Password</label>
        }else{
            props.onUpdateUserInfo(values, props.token);
            props.history.push('/logout');
        }
    }

    return (
        <Container maxWidth="sm" >
            {isAuth}
            <Box boxShadow={3} style={{ padding: '50px' }}>
                <form onSubmit={onSubmitHandler} className={classes.update} noValidate autoComplete="off">
                    <div>
                        <h2>My Profile</h2>
                    </div>
                    <div>
                        <TextField required id="UserName" label="Username" value={values.username} onChange={handleChange('username')} />
                        <br></br>
                        <TextField required id="Name" label="Name" value={values.name} onChange={handleChange('name')} />
                        <br></br>
                        <TextField required id="Nif" label="Nif" type="number" value={values.nif} onChange={handleChange('nif')} />
                        <br></br>
                        <FormControl className={clsx(classes.margin, classes.textField)}>
                            <InputLabel htmlFor="Password">Password</InputLabel>
                            <Input
                                required
                                id="Password"
                                type={values.showPassword ? 'text' : 'password'}
                                value={values.password}
                                onChange={handleChange('password')}
                                endAdornment={
                                    <InputAdornment position="end">
                                        <IconButton
                                            aria-label="toggle password visibility"
                                            onClick={handleClickShowPassword}
                                            onMouseDown={handleMouseDownPassword}
                                        >
                                            {values.showPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                }
                            />
                        </FormControl>
                        <br></br>
                        <FormControl className={clsx(classes.margin, classes.textField)}>
                            <InputLabel htmlFor="New Password">New Password</InputLabel>
                            <Input
                                required
                                id="New Password"
                                type={values.showNewPassword ? 'text' : 'password'}
                                value={values.newPassword}
                                onChange={handleChange('newPassword')}
                                endAdornment={
                                    <InputAdornment position="end">
                                        <IconButton
                                            aria-label="toggle password visibility"
                                            onClick={handleClickShowNewPassword}
                                            onMouseDown={handleMouseDownPassword}
                                        >
                                            {values.showNewPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                }
                            />
                        </FormControl>
                        <br></br>
                        <FormControl className={clsx(classes.margin, classes.textField)}>
                            <InputLabel htmlFor="ConfirmedNewPassword">New Password</InputLabel>
                            <Input
                                required
                                id="ConfirmedNewPassword"
                                type={values.showConfirmedNewPassword ? 'text' : 'password'}
                                value={values.confirmedNewPassword}
                                onChange={handleChange('confirmedNewPassword')}
                                endAdornment={
                                    <InputAdornment position="end">
                                        <IconButton
                                            aria-label="toggle password visibility"
                                            onClick={handleClickShowCOnformedNewPassword}
                                            onMouseDown={handleMouseDownPassword}
                                        >
                                            {values.showConfirmedNewPassword ? <Visibility /> : <VisibilityOff />}
                                        </IconButton>
                                    </InputAdornment>
                                }
                            />
                        </FormControl>
                        <br></br>
                        {error}
                    </div>
                    <div>
                        <br></br>
                        <Button type="submit" variant="contained" color="primary">Update</Button>
                    </div>
                </form>
            </Box>
        </Container >
    );
}

const mapDispatchToProps = (dispatch) => {
    return {
        onUpdateUserInfo: (values, token) => dispatch(actions.onUpdateUserInfo(values, token))
    };
}

const mapStateToProps = (state) => {
    return {
        token: state.auth.token,
        loading: state.loadingError.loading,
        error: state.loadingError.error,
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Profile);