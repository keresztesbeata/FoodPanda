import React from 'react'
import {Alert, Button, Card, Col, Container, Form, ListGroup, Navbar, Row} from "react-bootstrap";
import {LoadFoodCategories, LoadMenuForRestaurantByCategory} from "../actions/MenuActions";
import {ERROR} from "../actions/Utils";
import PlainMenuItem from "../components/PlainMenuItem";
import {LoadAdminsRestaurant} from "../actions/RestaurantActions";
import {GetCurrentUser} from "../actions/UserActions";

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
            selectedCategory: "All",
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        };
        this.onLoadRestaurantMenu = this.onLoadRestaurantMenu.bind(this);
        this.onLoadInitialData = this.onLoadInitialData.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
    }

    onLoadInitialData() {
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

    onLoadRestaurantMenu() {
        LoadAdminsRestaurant()
            .then(restaurantData => {
                LoadMenuForRestaurantByCategory(restaurantData.name, this.state.selectedCategory)
                    .then(restaurantMenu => {
                        if (restaurantMenu.length > 0) {
                            this.setState({
                                ...this.state,
                                restaurant: restaurantData,
                                menu: restaurantMenu,
                                notification: {
                                    show: false,
                                }
                            });
                        } else {
                            this.setState({
                                ...this.state,
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
        this.onLoadInitialData();
        this.onLoadRestaurantMenu();
    }

    handleInputChange(event) {
        // prevent page from reloading
        event.preventDefault();
        const target = event.target
        this.setState({
            [target.name]: target.value,
            notification: {
                show: false,
            }
        });
    }

    hideNotification() {
        this.setState({
            notification: {
                show: false
            }
        });
    }

    render() {
        return (
            <div className="background-container-menu bg-image justify-content-center ">
                {
                    (this.state.notification.show) ?
                        <Alert dismissible={true} onClose={this.hideNotification}
                               className={this.state.notification.type}>
                            {this.state.notification.message}
                        </Alert>
                        :
                        <div/>
                }
                <div className="flex justify-content-center">
                    <Navbar className="justify-content-center">
                        <Form className="d-flex">
                            <Form.Select aria-label="Food Category" className="me-2" name="selectedCategory"
                                         onChange={this.handleInputChange}>
                                <option value="All" key="All">All</option>
                                {
                                    this.state.categories.map(category =>
                                        <option value={category} key={category}>{category}</option>
                                    )
                                }
                            </Form.Select>
                            <Button variant="success" onClick={this.onLoadRestaurantMenu}>Filter</Button>
                        </Form>
                    </Navbar>
                    <Container className="fluid">
                        <ListGroup variant="flush">
                            {this.state.menu.map(item =>
                                <ListGroup.Item key={item.name}>
                                    <PlainMenuItem data={item}/>
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