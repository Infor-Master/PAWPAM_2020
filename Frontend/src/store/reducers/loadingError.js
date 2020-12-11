import * as actionTypes from '../actions/actionTypes';

const initialState = {
    loading: false,
    error: null,
};

const reducer = (state = initialState, action) => {

    switch (action.type) {
        case (actionTypes.START_REQUEST):
            return {
                ...state,
                loading: true,
                error: null,
            };
        case (actionTypes.END_REQUEST):
            return {
                ...state,
                loading: false,
                error: null,
            };
        case (actionTypes.ERROR_REQUEST):
            return {
                ...state,
                loading: false,
                error: action.error,
            };
        default:
            return state;
    }
}

export default reducer;