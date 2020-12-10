import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Redirect } from 'react-router-dom';

import * as authActions from '../store/actions/index';

const Logout = props => {

    const { onLogout } = props;

    useEffect(() => {
        onLogout();
    }, [onLogout])

    return (
        <div>
            <Redirect to="/" />
        </div>
    );
}

const mapDispatchToProps = dispatch => {
    return {
        onLogout: () => dispatch(authActions.logout()),
    };
}

export default connect(null, mapDispatchToProps)(Logout);