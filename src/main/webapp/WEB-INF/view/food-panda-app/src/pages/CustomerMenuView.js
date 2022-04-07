import React from 'react'
import MenuItem from "../components/MenuItem";
import {Alert, Button, Card, Col, Container, Form, FormControl, ListGroup, Navbar, Row} from "react-bootstrap";
import {FindRestaurant, LoadFoodCategories, LoadMenuForRestaurantByCategory} from "../actions/MenuActions";
import {ERROR} from "../actions/Utils";
import {GetCurrentUser} from "../actions/UserActions";

class CustomerMenuView extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurantName: "",
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
            selectedCategory: "All",
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        };
        this.onLoadRestaurantMenu = this.onLoadRestaurantMenu.bind(this);
        this.loadInitialData = this.loadInitialData.bind(this);
        this.loadRestaurant = this.loadRestaurant.bind(this);
        this.resetRestaurantInformation = this.resetRestaurantInformation.bind(this);
        this.onLoadRestaurantMenu = this.onLoadRestaurantMenu.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
        this.applyCategoryFilter = this.applyCategoryFilter.bind(this);
    }

    loadInitialData() {
        LoadFoodCategories()
            .then(data => {
                this.setState({
                    ...this.state,
                    categories: data
                });
            })
            .catch(error => {
                this.setState({
                    notification: {
                        show: true,
                        message: error.message,
                        type: ERROR
                    }
                });
            });
    }

    loadRestaurant() {
        let restaurantName = document.getElementById("search-restaurant").value;

        return FindRestaurant(restaurantName)
            .then(data => {
                this.setState({
                    ...this.state,
                    restaurant: data,
                    notification: {
                        show: false,
                    }
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
        FindRestaurant(this.state.restaurantName)
            .then(restaurantData => {
                this.setState({
                    restaurant: restaurantData
                });
                LoadMenuForRestaurantByCategory(this.state.restaurantName, this.state.selectedCategory)
                    .then(restaurantMenu => {
                        if (restaurantMenu.length > 0) {
                            this.setState({
                                menu: restaurantMenu,
                                notification: {
                                    show: false,
                                }
                            });
                        } else {
                            this.setState({
                                menu: [],
                                notification: {
                                    show: false,
                                }
                            });
                        }
                    })
                    .catch(error => {
                        this.setState({
                            notification: {
                                show: true,
                                message: error.message,
                                type: ERROR
                            }
                        });
                    });
            })
            .catch(error => {
                this.setState({
                    notification: {
                        show: true,
                        message: error.message,
                        type: ERROR
                    }
                });
            });
    }

    componentDidMount() {
        this.loadInitialData();
    }

    handleInputChange(event) {
        const target = event.target
        this.setState({
            [target.name]: target.value,
            notification: {
                show: false,
            }
        });
    }

    applyCategoryFilter(event) {
        this.handleInputChange(event);
        this.onLoadRestaurantMenu();
    }

    hideNotification() {
        this.setState({
            notification: {
                show: false
            }
        });
    }

    render() {
        let isAdmin = GetCurrentUser().isAdmin;
        return (
            <Container>
                {
                    (this.state.notification.show) ?
                        <Alert dismissible={true} onClose={this.hideNotification}
                               className={this.state.notification.type}>
                            {this.state.notification.message}
                        </Alert>
                        :
                        <div/>
                }
                <Navbar className="justify-content-center">
                    <Form className="d-flex">
                        <FormControl
                            type="search"
                            placeholder="Search restaurant..."
                            className="me-2"
                            aria-label="Search"
                            name="restaurantName"
                            onChange={this.handleInputChange}
                        />
                        <Button variant="outline-success" onClick={this.onLoadRestaurantMenu}>Search</Button>
                    </Form>
                </Navbar>
                <div className="flex justify-content-center">
                    <Container>
                        <Card className={(this.state.restaurant.name !== "") ? "visible" : "invisible"}>
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
                                            <b>Open - Close</b> : {this.state.restaurant.openingHour}:00
                                            - {this.state.restaurant.closingHour}:00
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
                            <Form.Select aria-label="Food Category" className="me-2" name="selectedCategory"
                                         onChange={this.applyCategoryFilter}>
                                <option value="All" key="All">All</option>
                                {
                                    this.state.categories.map(category =>
                                        <option value={category} key={category}>{category}</option>
                                    )
                                }
                            </Form.Select>
                        </Form>
                    </Navbar>
                    <Container className="fluid">
                        <ListGroup variant="flush">
                            {this.state.menu.map(item =>
                                <ListGroup.Item key={item.name}>
                                    <MenuItem data={item} showSuccess={this.state.showSuccess}
                                              successMessage={this.state.successMessage}/>
                                </ListGroup.Item>
                            )}
                        </ListGroup>
                    </Container>
                </div>
            </Container>
        );
    }
}

export default CustomerMenuView;