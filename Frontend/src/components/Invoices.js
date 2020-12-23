import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import ImageUploader from 'react-images-upload';

import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';
import { useStyles } from './Styles';

import * as actions from '../store/actions/index';
import * as api from '../store/actions/api';

const Invoices = props => {

    const classes = useStyles();

    const [picture, setPicture] = useState('');

    const onSubmitHandler = (event) => {
        event.preventDefault();
        props.onAddInvoice(picture);
    }

    let error = props.error? <label style={{ color: 'red' }}>Upload failed!</label> : null;

    let isAuth = !props.token ? <Redirect to='/' /> : null;

    return (
        <Container maxWidth="sm" >
            {isAuth}
            <Grid>
                <Box boxShadow={3} style={{ padding: '50px' }}>
                    <form onSubmit={onSubmitHandler} className={classes.invoiceUpload} noValidate autoComplete="off">
                        <div>
                            <h2>Invoices</h2>
                        </div>
                        <ImageUploader
                            {...props}
                            withIcon={true}
                            withPreview={true}
                            buttonText='Choose image'
                            onChange={setPicture}
                            imgExtension={['.jpg', '.jpeg', '.gif','.png']}
                            maxFileSize={5242880}
                            singleImage={true}
                        />
                        {error}
                        <div>
                            <Button type="submit" variant="contained" color="primary">Upload</Button>
                        </div>
                    </form>
                    
                </Box>
            </Grid>
        </Container >
    );
}

// get state from reducer
const mapStateToProps = (state) => {
    return {
        token: state.auth.token,
        loading: state.loadingError.loading,
        error: state.loadingError.error,
    };
}

// actions to reducer (dispatch)
const mapDispatchToProps = (dispatch) => {
    return {
            onAddInvoice: (picture) => dispatch(actions.addInvoice(picture))
        /* 
        onGetAllPlaces: (token) => dispatch(actions.fetchAllPlaces(token)),
        onGetUserPlaces: (token) => dispatch(actions.fetchUserPlaces(token)),
        onUpdatePlace: (place, token) => dispatch(actions.editPlace(place, token)),
        onDeletePlace: (id, token) => dispatch(actions.deletePlace(id, token)),
        */

        /*  // sockets
        onAddSocketList: (placeID, users) => dispatch(actions.addSocketList(placeID, users)),
        onAddSocketPeople: (placeID, numUsers) => dispatch(actions.addSocketPeople(placeID, numUsers))
         */
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(Invoices);