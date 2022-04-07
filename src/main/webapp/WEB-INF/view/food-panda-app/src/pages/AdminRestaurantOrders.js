import React from 'react'
import {Alert, Container, Form, ListGroup, Navbar} from "react-bootstrap";
import {ERROR, INFO} from "../actions/Utils";
import {GetCurrentUser} from "../actions/UserActions";
import {
    LoadAdminsRestaurant,
    LoadOrdersOfRestaurant,
    LoadOrdersOfRestaurantByStatus,
    LoadOrdersStates
} from "../actions/AdminActions";
import PlainOrder from "../components/PlainOrder";

class AdminRestaurantOrders extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurantName: "",
            orders: [],
            allOrderStates: [],
            selectedOrderState: "All",
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        };
        this.onLoadRestaurantOrders = this.onLoadRestaurantOrders.bind(this);
        this.onLoadRestaurantOrdersByState = this.onLoadRestaurantOrdersByState.bind(this);
        this.loadInitialData = this.loadInitialData.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
        this.applyOrderStatusFilter = this.applyOrderStatusFilter.bind(this);
    }

    loadInitialData() {
        LoadOrdersStates()
            .then(data => {
                this.setState({
                    ...this.state,
                    allOrderStates: data
                });
                this.onLoadRestaurantOrders();
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

    onLoadRestaurantOrders() {
        LoadAdminsRestaurant(GetCurrentUser().username)
            .then(restaurantData => {
                LoadOrdersOfRestaurant(restaurantData.name)
                    .then(ordersData => {
                        this.setState({
                            ...this.state,
                            restaurantName: restaurantData.name,
                            orders: ordersData,
                        });
                        if (ordersData.length === 0) {
                            this.setState({
                                notification: {
                                    show: true,
                                    message: "This restaurant has no previous orders!",
                                    type: INFO
                                }
                            })
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

    onLoadRestaurantOrdersByState() {
        LoadAdminsRestaurant(GetCurrentUser().username)
            .then(restaurantData => {
                LoadOrdersOfRestaurantByStatus(restaurantData.name, this.state.selectedOrderState)
                    .then(ordersData => {
                        this.setState({
                            ...this.state,
                            restaurantName: restaurantData.name,
                            orders: ordersData,
                        });
                        if (ordersData.length === 0) {
                            this.setState({
                                notification: {
                                    show: true,
                                    message: "This restaurant has no previous orders with state " + this.state.selectedOrderState +"!",
                                    type: INFO
                                }
                            })
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
        event.preventDefault();
        const target = event.target
        this.setState({
            [target.name]: target.value,
        });
    }

    applyOrderStatusFilter(event) {
        this.handleInputChange(event);
        this.onLoadRestaurantOrdersByState();
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
                <div className="flex justify-content-center">
                    <Navbar className="justify-content-center">
                        <Form className="d-flex">
                            <Form.Select aria-label="Order status" className="me-2" name="selectedOrderState"
                                         onChange={this.applyOrderStatusFilter}>
                                <option value="All" key="All">All</option>
                                {
                                    this.state.allOrderStates.map(state =>
                                        <option value={state} key={state}>{state}</option>
                                    )
                                }
                            </Form.Select>
                        </Form>
                    </Navbar>
                    <Container className="fluid">
                        <ListGroup variant="flush">
                            {this.state.orders.map(item =>
                                <ListGroup.Item key={item.orderNumber}>
                                    <PlainOrder data={item}/>
                                </ListGroup.Item>
                            )}
                        </ListGroup>
                    </Container>
                </div>
            </Container>
        );
    }
}

export default AdminRestaurantOrders;