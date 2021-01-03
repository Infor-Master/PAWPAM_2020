import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import ImageUploader from 'react-images-upload';
import GridList from "@material-ui/core/GridList";

import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';
import { useStyles } from '../Styles';

import Invoice from './Invoice';
import * as actions from '../../store/actions/index';
import jwt_decode from 'jwt-decode';

import { w3cwebsocket as W3CWebSocket } from "websocket";
const client = new W3CWebSocket('ws://localhost:5000/ws');

const Invoices = props => {
    
    client.onopen = () => {
        console.log('WebSocket Client Connected');
    };
    client.onmessage = (msg) => {
        console.log(msg)
        const dataFromServer = JSON.parse(msg.data);
        switch (dataFromServer.type) {
            case "serverevent":
                switch (dataFromServer.data) {
                    case "invoices":
                        if (props.token !== null) {
                            onGetInvoices(jwt_decode(props.token).id, props.token)
                         }
                        break;
                    default:
                        console.log("defaulted: " + msg);
                }
              break;
            default:
              console.log("defaulted: " + msg);
        }
    };

    // styles
    const classes = useStyles();


    // UseState
    const [picture, setPicture] = useState('');


    // buttons function
    const onSubmitHandler = (event) => {
        event.preventDefault();
        if (props.token !== null) {
            props.onAddInvoice(picture, client, props.token);
        }
    }


    // Variables
    let error = props.error ? <label style={{ color: 'red' }}>Upload failed!</label> : null;

    let isAuth = !props.token ? <Redirect to='/' /> : null;

    const { onGetInvoices } = props;

    useEffect(() => {
        if (props.token !== null) {
           onGetInvoices(jwt_decode(props.token).id, props.token)
        }
    }, [onGetInvoices, props.token])

    let invoices

    // Map
    if (!props.invoices.loading) {

        invoices = props.invoices.map(invoice => {
            console.log(invoice)

            return <Invoice
                key={invoice.ID}
                id={invoice.ID}
                name={invoice.Name}
                image={invoice.Image}
                info={invoice.Info}
            />
        });
    }

    if (invoices.length === 0) {
        invoices = (<h4>empty!</h4>);
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
                </Box>
                <Box boxShadow={3} style={{ padding: '20px'}}>
                <div className={classes.root}>
                    <h4>List of invoices</h4>
                    <GridList className={classes.GridList} cols={2} cellHeight={400}>
                        {invoices}
                    </GridList>                            
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
        onAddInvoice: (picture, client, token) => dispatch(actions.addInvoice(picture, client, token)),
        onGetInvoices: (id, token) => dispatch(actions.getInvoices(id, token))
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