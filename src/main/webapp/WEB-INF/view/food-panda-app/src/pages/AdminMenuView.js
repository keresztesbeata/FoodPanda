import React from 'react'
import MenuItem from "../components/MenuItem";
import {
    Alert,
    Button,
    Card,
    Col,
    Container,
    Form,
    FormControl,
    ListGroup, Nav,
    Navbar, Offcanvas, Row
} from "react-bootstrap";
import {
    FindRestaurant,
    LoadFoodCategories,
    LoadMenuForRestaurantByCategory
} from "../actions/MenuActions";

class AdminMenuView extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurant: {
                name: "",
                address: "",
                deliveryZones: [],
                admin: "",
                openingHour: 0,
                closingHour: 0,
                deliveryFee: 0,
            },
            menu: [],
            categories: [],
            showError: false,
            errorMessage: "",
        };
        this.onLoadRestaurantMenu = this.onLoadRestaurantMenu.bind(this);
        this.loadFoodCategories = this.loadFoodCategories.bind(this);
        this.loadRestaurant = this.loadRestaurant.bind(this);
        this.resetRestaurantInformation = this.resetRestaurantInformation.bind(this);
        this.onLoadRestaurantMenu = this.onLoadRestaurantMenu.bind(this);
    }

    loadFoodCategories() {
        LoadFoodCategories()
            .then(data => {
                this.setState({
                    categories: data
                });
            })
            .catch(error => {
                this.setState({
                    showError: true,
                    errorMessage: error.message,
                })
            });
    }

    loadRestaurant() {
        let restaurantName = document.getElementById("search-restaurant").value;

        return FindRestaurant(restaurantName)
            .then(data => {
                this.setState({
                    restaurant: data,
                    showError: false,
                });
            })
            .catch(error => {
                throw new Error(error);
            });
    }

    resetRestaurantInformation() {
        this.setState({
            restaurant: {
                name: "",
                address: "",
                deliveryZones: [],
                admin: "",
                openingHour: 0,
                closingHour: 0,
                deliveryFee: 0,
            },
            menu: [],
        })
    }

    onLoadRestaurantMenu() {
        let restaurantName = document.getElementById("search-restaurant").value;
        const selectedCategory = document.getElementById("select-category").value;

        FindRestaurant(restaurantName)
            .then(restaurantData => {
                LoadMenuForRestaurantByCategory(restaurantName, selectedCategory)
                    .then(restaurantMenu => {
                        if (restaurantMenu.length > 0) {
                            this.setState({
                                restaurant: restaurantData,
                                menu: restaurantMenu,
                                showError: false,
                            });
                        } else {
                            this.setState({
                                menu: [],
                                showError: false,
                            });
                        }
                    })
                    .catch(error => {
                        this.setState({
                            showError: true,
                            errorMessage: error.message,
                        })
                    });
            })
            .catch(error => {
                this.setState({
                    showError: true,
                    errorMessage: error.message,
                });
                this.resetRestaurantInformation();
            });
    }

    componentDidMount() {
        this.loadFoodCategories();
    }

    render() {
        return (
            <div>
                {(this.state.showError ) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
                <Navbar className="justify-content-center">
                    <Form className="d-flex">
                        <FormControl
                            type="search"
                            placeholder="Search restaurant..."
                            className="me-2"
                            aria-label="Search"
                            id="search-restaurant"
                        />
                        <Button variant="outline-success" onClick={this.onLoadRestaurantMenu}>Search</Button>
                    </Form>
                </Navbar>
                <div className="flex justify-content-center">
                    <Container >
                        {/*className="header-image-home d-flex justify-content-center align-items-center">*/}
                        {/*<Card className={(this.state.restaurant.name !== "")?"visible transparent-background" : "invisible"}>*/}
                        <Card className={(this.state.restaurant.name !== "")?"visible" : "invisible"}>
                            <Card.Title className="text-center">
                                {this.state.restaurant.name}
                            </Card.Title>
                            <Card.Body>
                                <Row>
                                    <Col>
                                        <Card.Text>
                                            <b>Address</b> : {this.state.restaurant.address}
                                        </Card.Text>
                                        <Card.Text>
                                            <b>DeliveryFee</b> : {this.state.restaurant.deliveryFee} $
                                        </Card.Text>
                                        <Card.Text>
                                            <b>Open - Close</b> : {this.state.restaurant.openingHour}:00 - {this.state.restaurant.closingHour}:00
                                        </Card.Text>
                                    </Col>
                                    <Col>
                                        <ul>
                                            <Card.Text>
                                                <b>DeliveryZones</b> :
                                                {this.state.restaurant.deliveryZones
                                                    .sort()
                                                    .map(deliveryZone =>
                                                        <li key={deliveryZone}>
                                                            {deliveryZone}
                                                        </li>
                                                    )}
                                            </Card.Text>
                                        </ul>
                                    </Col>
                                </Row>
                            </Card.Body>
                        </Card>
                    </Container>
                    <Navbar className="justify-content-center">
                        <Form className="d-flex">
                            <Form.Select aria-label="Food Category" className="me-2" id="select-category">
                                <option value="All" key="All">All</option>
                                {
                                    this.state.categories.map(category =>
                                        <option value={category} key={category}>{category}</option>
                                    )
                                }
                            </Form.Select>
                            <Button variant="outline-success" onClick={this.onLoadRestaurantMenu}>Filter</Button>
                        </Form>
                    </Navbar>
                    <Container className="fluid">
                        <ListGroup variant="flush">
                            {this.state.menu.map(item =>
                                <ListGroup.Item key={item.name}>
                                    <MenuItem data={item} showCartContent={this.state.showCartContent}/>
                                </ListGroup.Item>
                            )}
                        </ListGroup>
                    </Container>
                </div>
            </div>
        );
    }
}

export default AdminMenuView;