import * as actionTypes from './actionTypes';

export const startRequest = () => {
    return {
        type: actionTypes.START_REQUEST,
    }
}

export const endRequest = () => {
    return {
        type: actionTypes.END_REQUEST,
    }
}

export const errorRequest = (error) => {
    return {
        type: actionTypes.ERROR_REQUEST,
        error: error,
    }
}