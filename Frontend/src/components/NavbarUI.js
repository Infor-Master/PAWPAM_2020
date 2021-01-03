import React from 'react';

import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import { NavLink } from 'react-router-dom';

const NavbarUI = props => {

    return (
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
            <Navbar.Toggle aria-controls="responsive-navbar-nav" />
            <Navbar.Collapse id="responsive-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link as={NavLink} to="/invoices">Invoices</Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link as={NavLink} to="/profile">Profile</Nav.Link>
                </Nav>
                <Nav>
                    <Nav.Link as={NavLink} to="/logout">Logout</Nav.Link>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
}

export default NavbarUI;