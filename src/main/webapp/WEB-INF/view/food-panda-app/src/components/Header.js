import React from 'react';
import {Container, Navbar, Nav, Image} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";

class Header extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        try {
            const userSession = GetCurrentUser();
            console.log("Home of : " + userSession.username);

            if (userSession.isAdmin === true)
                return (
                    <div>
                        <Navbar bg="dark" variant="dark">
                            <Container>
                                <Navbar.Brand href="/admin">foodpanda</Navbar.Brand>
                                <Nav className="me-auto">
                                    <Nav.Link href="/admin/menu">Menu</Nav.Link>
                                    <Nav.Link href="/admin/orders_history">Orders</Nav.Link>
                                    <Nav.Link href="/logout">Logout</Nav.Link>
                                </Nav>
                            </Container>
                        </Navbar>
                    </div>
                );

            return (
                <div>
                    <Navbar bg="dark" variant="dark">
                        <Container>
                            <Navbar.Brand href="/customer">foodpanda</Navbar.Brand>
                            <Nav className="me-auto">
                                <Nav.Link href="/customer/menu">Menu</Nav.Link>
                                <Nav.Link href="/customer/cart">My Cart</Nav.Link>
                                <Nav.Link href="/customer/orders_history">Orders History</Nav.Link>
                                <Nav.Link href="/logout">Logout</Nav.Link>
                            </Nav>
                        </Container>
                    </Navbar>
                </div>
            );
        } catch (e) {
            return (
                <div>
                    <Navbar bg="dark" variant="dark">
                        <Container>
                            {/*<img src="https://www.gran-turismo.com/gtsport/decal/8296285827061809168_1.png" alt="Foodpanda icon" width="5%"/>*/}
                            <img src={require("../images/food-panda-logo.png")} alt="Foodpanda icon" width="5%"/>
                            <Navbar.Brand href="/">foodpanda</Navbar.Brand>
                            <Nav className="me-auto">
                                <Nav.Link href="/login">Log in</Nav.Link>
                                <Nav.Link href="/register">Register</Nav.Link>
                            </Nav>
                        </Container>
                    </Navbar>
                </div>
            );
        }
    }
}
export default Header;
