import axios from 'axios';
import * as actionTypes from './actionTypes';
import * as loadingErrorActions from './index';
import * as api from './api';

export const authSuccess = (token, username) => {
    return {
        type: actionTypes.AUTH_SUCCESS,
        token: token,
        username: username,
    };
}

export const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    localStorage.removeItem('expirationDate');
    localStorage.removeItem('expirationTime');
    return {
        type: actionTypes.AUTH_LOGOUT,
    };
}

export const auth = (username, password) => {
    return dispatch => {
        const authData = {
            username: username,
            password: password
        };

        axios.post(api.URL_LOGIN, authData).then(res => {
            dispatch(loadingErrorActions.startRequest());

            const expirationDate = new Date(Date.parse(res.data.expirationTime));
            // 60000 -> 1min to refresh token before it expires
            const expirationTime = Date.parse(res.data.expirationTime) - new Date().getTime() - 60000;

            localStorage.setItem('token', res.data.token);
            localStorage.setItem('expirationDate', expirationDate);
            localStorage.setItem('expirationTime', expirationTime);
            localStorage.setItem('username', res.data.username);

            //dispatch(checkAuthTimeout(expirationTime));
            dispatch(authSuccess(res.data.token, res.data.username));
            dispatch(loadingErrorActions.endRequest());
        }).catch(err => {
            dispatch(loadingErrorActions.errorRequest(err.toString()));
        });
    }
}

export const checkAuthTimeout = (expirationTime) => {
    return dispatch => {
        setTimeout(() => {
            // not working -> after token expiration date the user are redirect to login page
            // refreshToken();
            dispatch(logout());
        }, expirationTime);
    }
}

export const authCheckState = () => {
    return dispatch => {
        const token = localStorage.getItem('token');
        if (!token) {
            dispatch(logout());
        } else {
            const username = localStorage.getItem('username');
            const expirationDate = new Date(localStorage.getItem('expirationDate'));
            if (expirationDate <= new Date()) {
                dispatch(logout());
            } else {
                // 60000 -> 1min to refresh token before it expires
                const expirationTime = expirationDate.getTime() - new Date().getTime() - 60000;
                dispatch(authSuccess(token, username));
                //dispatch(checkAuthTimeout(expirationTime));
            }
        }
    };
}