import * as actionTypes from '../actions/actionTypes';

const initialState = {
    username: null,
    token: null,
};

const reducer = (state = initialState, action) => {

    switch (action.type) {
        case (actionTypes.AUTH_SUCCESS):
            return {
                ...state,
                token: action.token,
                username: action.username,
            };
        case (actionTypes.AUTH_LOGOUT):
            return {
                ...state,
                token: null,
                username: null,
            };
        case (actionTypes.REFRESH_TOKEN):
            return {
                ...state,
                token: action.token,
            };
        case (actionTypes.SET_AUTH_REDIRECT_PATH):
            return {
                ...state,
                authRedirectPath: action.path,
            };
        default:
            return state;
    }
}

export default reducer;