import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import ImageUploader from 'react-images-upload';

import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';
import { useStyles } from '../Styles';
import List from '@material-ui/core/List';


import Invoice from './Invoice';
import * as actions from '../../store/actions/index';
import jwt_decode from 'jwt-decode';

const Invoices = props => {

    // styles
    const classes = useStyles();


    // UseState
    const [picture, setPicture] = useState('');


    // buttons function
    const onSubmitHandler = (event) => {
        event.preventDefault();
        props.onAddInvoice(picture);
    }


    // Variables
    let error = props.error ? <label style={{ color: 'red' }}>Upload failed!</label> : null;

    let isAuth = !props.token ? <Redirect to='/' /> : null;

    const { onGetInvoices } = props;

    useEffect(() => {
        if (props.token !== null) {
           onGetInvoices(jwt_decode(props.token).id)
        }
    }, [onGetInvoices, props.token])

    let invoices

    // Map
    if (!props.invoices.loading) {

        console.log("entrei")
        console.log(props.invoices)

        invoices = props.invoices.map(invoice => {
            console.log(invoice)

            return <Invoice
                key={invoice.ID}
                id={invoice.ID}
                name={invoice.Name}
                image={invoice.Image}
            />
        });
    }

    if (invoices.length === 0) {
        invoices = (<h3>Not found!</h3>);
    }


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
                            imgExtension={['.jpg', '.jpeg', '.gif', '.png']}
                            maxFileSize={5242880}
                            singleImage={true}
                        />
                        {error}
                        <div>
                            <Button type="submit" variant="contained" color="primary">Upload</Button>
                        </div>
                    </form>

                    <div>
                        <label>
                            <h4>List of invoices</h4>
                            <div style={{ display: 'flex', width: '100%' }}>
                                <List>
                                    {invoices}
                                </List>
                            </div>
                        </label>
                    </div>
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
        invoices: state.invoices.invoices,
        error: state.loadingError.error,
    };
}

// actions to reducer (dispatch)
const mapDispatchToProps = (dispatch) => {
    return {
        onAddInvoice: (picture) => dispatch(actions.addInvoice(picture)),
        onGetInvoices: (id) => dispatch(actions.getInvoices(id))
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