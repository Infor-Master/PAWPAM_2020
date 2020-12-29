import * as actionTypes from '../actions/actionTypes';

const initialState = {
    invoices: [],
};

const reducer = (state = initialState, action) => {

    switch (action.type) {

        /*case (actionTypes.ADD_SOCKET_LIST):
            console.log(action)
            let newArrayinvoicesList = state.invoices.filter((place) => (place.ID !== action.id));
            let placeList = state.invoices.find(place => place.ID === action.id);

            placeList = { ...placeList, users: action.users };
            newArrayinvoicesList = newArrayinvoicesList.concat(placeList);
            
            return {
                ...state,
                invoices: newArrayinvoicesList,
            };
        case (actionTypes.ADD_SOCKET_PEOPLE):
            console.log(action)
            let newArrayinvoices = state.invoices.filter((place) => (place.ID !== action.id));
            let place = state.invoices.find(place => place.ID === action.id);
            place = { ...place, people: action.numUsers };
            newArrayinvoices = newArrayinvoices.concat(place);
            return {
                ...state,
                invoices: newArrayinvoices,
            };

            ////////////
*/
        case (actionTypes.GET_ALL_INVOICES):
            return {
                ...state,
                invoices: action.invoices,
            };
            
        case (actionTypes.CREATE_INVOICE):
            const newInvoice = {
                ...action.invoice,
                ID: action.id,
                name: '',
                image: ''
            }
            return {
                ...state,
                invoices: state.invoices.concat(newInvoice),
            };
        /*case (actionTypes.EDIT_PLACE):
            let placearray = state.invoices.filter((place) => (place.ID !== action.place.ID));
            let newarray = placearray.concat(action.place);
            return {
                ...state,
                invoices: newarray,
            };*/
        /*case (actionTypes.DELETE_PLACE):
            return {
                ...state,
                invoices: state.invoices.filter((invoice) => (invoice.ID !== action.id)),
            };*/
        default:
            return state;
    }
}

export default reducer;