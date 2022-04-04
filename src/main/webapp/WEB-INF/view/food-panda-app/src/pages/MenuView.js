import React from 'react'
import MenuItem from "../components/MenuItem";
import {Alert, Button, Container, Form, FormControl, FormLabel, ListGroup, Nav, Navbar} from "react-bootstrap";
import {LoadMenuForRestaurant} from "../actions/MenuActions";

class MenuView extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurant: "",
            menu: [],
            showError: false,
            errorMessage: "",
        };
        this.loadRestaurantMenu = this.loadRestaurantMenu.bind(this);
    }

    loadRestaurantMenu() {
        const restaurantName = document.getElementById("search-restaurant").value;

        LoadMenuForRestaurant(restaurantName)
            .then(restaurantMenu => {
                if(restaurantMenu.length > 0) {
                    this.setState({
                        restaurant: restaurantName,
                        menu: restaurantMenu
                    });
                }else{
                    this.setState({
                        restaurant: "",
                        menu: []
                    });
                }
            })
            .catch(error => {
                this.setState({
                    showError: true,
                    errorMessage: error.message,
                })
            });
    }

    componentDidMount() {
       this.loadRestaurantMenu();
    }

    render() {

        return (
            <div>
                {(this.state.showError) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
                <Navbar className="justify-content-center">
                <Form className="d-flex">
                    <FormControl
                        type="search"
                        placeholder="Search restaurant..."
                        className="me-2"
                        aria-label="Search"
                        id="search-restaurant"
                    />
                    <Button variant="outline-success" onClick={this.loadRestaurantMenu}>Search</Button>
                </Form>
                </Navbar>
            <div className="flex justify-content-center">
                <div className="header-image-home d-flex justify-content-center align-items-center">
                    <h1 className="text-white">{this.state.restaurant}</h1>
                </div>
                <Container className="fluid">
                    <ListGroup variant="flush">
                        {this.state.menu.map(item =>
                            <ListGroup.Item key={item.name}>
                                <MenuItem isEditable={this.props.isAdmin} data={item}/>
                            </ListGroup.Item>
                        )}
                    </ListGroup>
                </Container>
            </div>
            </div>
        );
    }
}

export default MenuView;