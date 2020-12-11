import axios from 'axios';
import * as actionTypes from './actionTypes';
import * as loadingErrorActions from './index';
import * as api from './api';

export const signUpSuccess = (statusCode) => {
    return {
        type: actionTypes.SIGNUP_SUCCESS,
        statuscode: statusCode
    };
}

export const signup = (username, password, name, nif) => {
    return dispatch => {
        const authData = {
            username: username,
            password: password,
            name: name,
            nif: parseInt(nif)
        };

        axios.post(api.URL_SIGNUP, authData).then(res => {
            dispatch(loadingErrorActions.startRequest());
            dispatch(signUpSuccess(res.data.status));
            dispatch(loadingErrorActions.endRequest());
        }).catch(err => {
            dispatch(loadingErrorActions.errorRequest(err.toString()));
        });
    }
}