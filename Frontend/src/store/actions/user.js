import axios from "axios";
import * as loadingErrorActions from "./index";
import * as api from "./api";


export const onUpdateUserInfo = (value, token) => {
    return (dispatch) => {

        console.log("\nNAME:"+value.name+ "\nNIF: "+ value.nif + "\nPASSWORD: " +value.password +"\nNEWPASSWORD: "+value.newPassword + "\nUSERNAME: " +value.username + "\nconfirmedNewPassword: " +value.confirmedNewPassword + "\nUSERID: "+ value.userid)

        axios({
            method: "POST",
            baseURL: api.URL_UPDATE_USER,
            data: {
                ID: value.userid,
                Name: value.name,
                NIF: value.nif,
                Password: value.password,
                NewPassword: value.newPassword,
                Username: value.username,
            },
            headers: {
                Authorization: `Bearer ${token}`,
            },
        })
            .then((res) => {
                dispatch(loadingErrorActions.startRequest());

                console.log(res)

                dispatch(loadingErrorActions.endRequest());
            })
            .catch((err) => {
                dispatch(loadingErrorActions.errorRequest(err.toString()));
            });
    };
};