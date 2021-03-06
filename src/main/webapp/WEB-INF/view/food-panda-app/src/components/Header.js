import React from 'react';
import {Container, Nav, Navbar, NavDropdown} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";

class Header extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
        }
    }

    componentDidMount() {
        GetCurrentUser()
            .then(currentUserData => {
                this.setState({
                    currentUser: currentUserData
                })
            })
            .catch(e => {
                this.state = {
                    currentUser: null,
                }
            });
    }

    render() {
        if (this.state.currentUser !== null) {

            if (this.state.currentUser.isAdmin === true)
                return (<div>
                    <Navbar bg="dark" variant="dark">
                        <Container>
                            <img src={require("../images/food-panda-logo.png")} alt="FoodPanda icon" width="5%"/>
                            <Navbar.Brand href="/">foodpanda</Navbar.Brand>
                            <Nav className="me-auto">
                                <NavDropdown title="Menu">
                                    <NavDropdown.Item href="/admin/restaurant/view_menu">View
                                        Menu</NavDropdown.Item>
                                    <NavDropdown.Item href="/admin/restaurant/add_food">Add Food</NavDropdown.Item>
                                </NavDropdown>
                                <Nav.Link href="/admin/restaurant">My restaurant</Nav.Link>
                                <Nav.Link href="/admin/restaurant/orders">Orders</Nav.Link>
                                <Nav.Link href="/logout">Logout</Nav.Link>
                            </Nav>
                        </Container>
                    </Navbar>
                </div>);

            return (<div>
                <Navbar bg="dark" variant="dark">
                    <Container>
                        <img src={require("../images/food-panda-logo.png")} alt="FoodPanda icon" width="5%"/>
                        <Navbar.Brand href="/">foodpanda</Navbar.Brand>
                        <Nav className="me-auto">
                            <Nav.Link href="/customer/menu">Menu</Nav.Link>
                            <Nav.Link href="/customer/cart">My Cart</Nav.Link>
                            <Nav.Link href="/customer/orders_history">Orders History</Nav.Link>
                            <Nav.Link href="/logout">Logout</Nav.Link>
                        </Nav>
                    </Container>
                </Navbar>
            </div>);
        } else {
            return (<div>
                <Navbar bg="dark" variant="dark">
                    <Container>
                        <img src={require("../images/food-panda-logo.png")} alt="FoodPanda icon" width="5%"/>
                        <Navbar.Brand href="/">foodpanda</Navbar.Brand>
                        <Nav className="me-auto">
                            <Nav.Link href="/login">Log in</Nav.Link>
                            <Nav.Link href="/register">Register</Nav.Link>
                        </Nav>
                    </Container>
                </Navbar>
            </div>);
        }
    }
}

export default Header;
