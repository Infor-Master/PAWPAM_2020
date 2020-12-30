import React from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router';

import Grid from '@material-ui/core/Grid';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';

const HomePage = props => {

    let isAuth = !props.token ? <Redirect to='/' /> : null;

    return (
        <Container maxWidth="sm" >
            {isAuth}
            <Grid>
                <Box boxShadow={3} style={{ padding: '50px' }}>
                    <div>
                        <h2>Homepage</h2>
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
        error: state.loadingError.error,
    };
}

// actions to reducer (dispatch)
const mapDispatchToProps = (dispatch) => {
    return {

    };
}

export default connect(mapStateToProps, mapDispatchToProps)(HomePage);