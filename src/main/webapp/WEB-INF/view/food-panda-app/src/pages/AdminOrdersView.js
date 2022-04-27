import React from 'react'
import {Alert, Button, Container, Form, Navbar, Table} from "react-bootstrap";
import {ERROR, SUCCESS, WARNING} from "../actions/Utils";
import {GetCurrentUser} from "../actions/UserActions";

import PlainOrder from "../components/PlainOrder";
import {
    LoadOrdersOfRestaurant,
    LoadOrdersOfRestaurantByStatus,
    LoadOrdersStatuses,
    UpdateOrderStatus
} from "../actions/OrderActions";
import {LoadAdminsRestaurant} from "../actions/RestaurantActions";

class AdminOrdersView extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurantName: "",
            orders: [],
            allOrderStatuses: [],
            selectedOrderStatus: "All",
            updatedOrderStatus: "",
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
        LoadAdminsRestaurant()
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
                                    type: WARNING
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
        LoadAdminsRestaurant()
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
                                    type: WARNING
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
        const target = event.target

        this.setState({
            ...this.state,
            [target.name]: target.value,
        });
    }

    applyOrderStatusFilter(event) {
        this.handleInputChange(event);
        this.onLoadRestaurantOrdersByState();
        this.hideNotification();
    }

    hideNotification() {
        this.setState({
            notification: {
                ...this.state,
                show: false
            }
        });
    }

    onUpdateOrderStatus(orderNumber) {
        UpdateOrderStatus(orderNumber, this.state.updatedOrderStatus)
            .then(() => {
                LoadOrdersOfRestaurantByStatus(this.state.restaurantName, this.state.selectedOrderStatus)
                    .then(ordersData => {
                        this.setState({
                            orders: ordersData,
                            notification: {
                                show: true,
                                message: "Status of the order has been successfully updated to " + this.state.updatedOrderStatus + "!",
                                type: SUCCESS
                            }
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
            <div className="background-container-order bg-image justify-content-center ">
                <Container className="white-background">
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
                        <Container className="fluid">
                            {
                                (this.state.notification.show) ?
                                    <Alert dismissible={true} onClose={this.hideNotification}
                                           className={this.state.notification.type}>
                                        {this.state.notification.message}
                                    </Alert>
                                    :
                                    <div/>
                            }
                            <Table variant="flush">
                                <thead>
                                <tr>
                                    <th>
                                        Order
                                    </th>
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
                                        <td>
                                            <PlainOrder data={item}/>
                                        </td>
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
                                            <Button variant="outline-secondary"
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
            </div>
        );
    }
}

export default AdminOrdersView;