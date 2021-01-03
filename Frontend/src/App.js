import React from 'react';
import { BrowserRouter, Redirect, Route, Switch } from 'react-router-dom';

import './App.css';
import HomePage from './components/HomePage';
import Layout from './components/Layout';
import Login from './components/Login';
import Logout from './components/Logout';
import SignUp from './components/SignUp';
import Invoices from './components/Invoices/Invoices';
import InvoicePage from './components/Invoices/InvoicePage';
import 'bootstrap/dist/css/bootstrap.min.css';
import { connect } from 'react-redux';

const App = props => {

  const routes = (
    <Switch>
      <Route path="/home" component={HomePage} />
      <Route path="/signup" component={SignUp} />
      <Route path="/logout" component={Logout} />
      <Route path="/signup" component={SignUp} />
      <Route path="/invoices" component={Invoices} />
      <Route path="/invoice" component={InvoicePage} />
      <Route path="/" component={Login} />
      <Route render={() => <h1>Not found!</h1>} />
      <Redirect to="/" />
    </Switch>
  );

  return (
    <React.Fragment>
      <BrowserRouter>
        <div className="App">
          <Layout>
            {routes}
          </Layout>
        </div>
      </BrowserRouter>
    </React.Fragment>
  );

}

// get state from reducer
const mapStateToProps = (state) => {
  return {
    token: state.auth.token,
  };
}

export default connect(mapStateToProps, null)(App);
