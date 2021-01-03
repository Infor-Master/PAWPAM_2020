import axios from "axios";
import jwt_decode from "jwt-decode";
import * as loadingErrorActions from "./index";
import * as api from "./api";
import * as actionTypes from "./actionTypes";

export const addInvoice = (picture, client, token) => {
  return (dispatch) => {
    let file = "";
    let reader = new FileReader();
    reader.readAsDataURL(picture[0]);
    reader.onload = function () {
      console.log(file);
      axios({
        method: "post",
        baseURL: api.URL_INVOICES,
        data: {
          image: reader.result,
          name: picture[0].name,
          userid: jwt_decode(token).id,
        },
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
        .then((res) => {
          dispatch(loadingErrorActions.startRequest());

          console.log(res)
          alert("Invoice added!")
          client.send(JSON.stringify({
            data: "invoices",
            type: "userevent"
          }));

          dispatch(loadingErrorActions.endRequest());
        })
        .catch((err) => {
          dispatch(loadingErrorActions.errorRequest(err.toString()));
        });
    };
    reader.onerror = function (error) {
      console.error(error);
    };
  };
};
////////////////////////////////////// GET INVOICES //////////////////////

const getUserInvoices = (invoices) => {
  //console.log(invoices)
  return {
    type: actionTypes.GET_ALL_INVOICES,
    invoices: invoices,
  };
};

export const getInvoices = (id, token) => {
  return (dispatch) => {
    dispatch(loadingErrorActions.startRequest());

    axios({
      method: "get",
      baseURL: api.URL_GET_INVOICES + "/" + id,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        dispatch(getUserInvoices(res.data.data));

        dispatch(loadingErrorActions.endRequest());
      })
      .catch((err) => {
        dispatch(loadingErrorActions.errorRequest(err.toString()));
      });
  };
};

export const deleteInvoice = (invoice, token) => {
  return (dispatch) => {
    dispatch(loadingErrorActions.startRequest());

    axios({
      method: "delete",
      baseURL: api.URL_INVOICES + "/" + invoice.ID,
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        console.log(res)
        dispatch(loadingErrorActions.endRequest());
      })
      .catch((err) => {
        dispatch(loadingErrorActions.errorRequest(err.toString()));
      });
  };
};
