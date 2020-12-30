import React, { useEffect, useState } from 'react';

import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import Button from '@material-ui/core/Button';

import { connect } from 'react-redux';
import { Redirect } from 'react-router';
import { useStyles } from '../Styles';

import * as actions from '../../store/actions/index';

const InvoicePage = props => {

    // styles
    const classes = useStyles();

    let invoice = props.location.state.invoice

    let isAuth = !props.token ? <Redirect to='/' /> : null;

    // buttons function
    const onDeleteHandler = (event) => {
        event.preventDefault();
        props.onDeleteInvoice(invoice, props.token);
        props.history.push('/invoices');
    }

    return (
        <Container maxWidth="sm" >
            {isAuth}
            <Box boxShadow={3} style={{ padding: '20px' }}>  
                <div>
                    <h2>Invoice</h2>
                    <form onSubmit={onDeleteHandler} className={classes.invoiceDelete} noValidate autoComplete="off">
                        <div>
                            <Button type="submit" variant="contained" color="primary">Delete</Button>
                        </div>
                    </form>
                    <img style={{ width: "500px", objectFit: "cover" }} className={classes.image} src={invoice.image} alt={invoice.name}/>
                </div>
                </Box>
                <Box boxShadow={3} style={{ padding: '20px'}}>
                <div>
                    <h4>Content</h4>
                    {invoice.info}                           
                </div>
                </Box>
        </Container >
    )

}

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
        onDeleteInvoice: (invoice, token) => dispatch(actions.deleteInvoice(invoice, token)),
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(InvoicePage);