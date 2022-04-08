import React from 'react'
import {Alert, Button, Container, Form, FormSelect, ListGroup, Navbar, Table} from "react-bootstrap";
import {ERROR, INFO, SUCCESS} from "../actions/Utils";
import {GetCurrentUser} from "../actions/UserActions";

import PlainOrder from "../components/PlainOrder";
import {
    LoadOrdersOfRestaurant,
    LoadOrdersOfRestaurantByStatus,
    LoadOrdersStatuses,
    UpdateOrderStatus
} from "../actions/OrderActions";
import {LoadAdminsRestaurant} from "../actions/RestaurantActions";
import {Link} from "react-router-dom";

class AdminOrdersView extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurantName: "",
            orders: [],
            allOrderStatuses: [],
            selectedOrderStatus: "All",
            updatedOrderStatus: "",
            showDetails: false,
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
        this.onShowDetails = this.onShowDetails.bind(this);
    }

    loadInitialData() {
        LoadOrdersStatuses()
            .then(data => {
                this.setState({
                    ...this.state,
                    allOrderStatuses: data
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
                LoadOrdersOfRestaurantByStatus(restaurantData.name, this.state.selectedOrderStatus)
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
                                    message: "This restaurant has no previous orders with state " + this.state.selectedOrderStatus + "!",
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
            ...this.state,
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
                ...this.state,
                show: false
            }
        });
    }

    onShowDetails(event) {
        event.preventDefault();
        const target = event.target

        this.setState({
            ...this.state,
            showDetails: target.checked,
        });
    }

    onUpdateOrderStatus(orderNumber) {
        UpdateOrderStatus(orderNumber, this.state.updatedOrderStatus)
            .then(() => {
                LoadOrdersOfRestaurantByStatus(this.state.restaurantName, this.state.selectedOrderStatus)
                    .then(ordersData => {
                        this.setState({
                            ...this.state,
                            orders: ordersData,
                        })
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

                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        message: "Status of the order has been successfully updated to " + this.state.updatedOrderStatus + "!",
                        type: SUCCESS
                    }
                })
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
                            <Form.Select aria-label="Order status" className="me-2" name="selectedOrderStatus"
                                         onChange={this.applyOrderStatusFilter}>
                                <option value="All" key="All">All</option>
                                {
                                    this.state.allOrderStatuses.map(state =>
                                        <option value={state} key={state}>{state}</option>
                                    )
                                }
                            </Form.Select>
                        </Form>
                    </Navbar>
                    <Form.Check
                        type="switch"
                        id="show-details-switch"
                        label="Detailed view"
                        onChange={this.onShowDetails}
                    />
                    <Container className="fluid">
                        <Table variant="flush">
                            <thead>
                            <tr>
                                {
                                    (this.state.showDetails) ?
                                        <th>
                                            Order details
                                        </th>
                                        :
                                        <th>
                                            Order number
                                        </th>

                                }
                                <th>
                                    Order status
                                </th>
                                <th>
                                    Actions
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.state.orders.map(item =>
                                <tr key={item.orderNumber}>
                                    {
                                        (this.state.showDetails) ?
                                            <td>
                                                <PlainOrder data={item}/>
                                            </td>
                                            :
                                            <td>
                                                {item.orderNumber}
                                            </td>
                                    }
                                    <td>
                                        <Form className="d-flex">
                                            <Form.Select aria-label="Order status" className="me-2"
                                                         name="updatedOrderStatus"
                                                         onChange={this.handleInputChange}
                                                         defaultValue={item.orderStatus}>
                                                {
                                                    this.state.allOrderStatuses
                                                        .map(status =>
                                                            <option value={status}
                                                                    key={"new_" + status}>
                                                                {status}
                                                            </option>
                                                        )
                                                }
                                            </Form.Select>
                                        </Form>
                                    </td>
                                    <td>
                                        <Button variant="outline-info"
                                                onClick={() => this.onUpdateOrderStatus(item.orderNumber)}>
                                            Update status
                                        </Button>
                                    </td>
                                </tr>
                            )}
                            </tbody>
                        </Table>
                    </Container>
                </div>
            </Container>
        );
    }
}

export default AdminOrdersView;