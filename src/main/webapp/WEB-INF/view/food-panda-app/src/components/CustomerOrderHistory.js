import React from 'react'
import MenuItem from "../components/MenuItem";
import {Alert, Button, Card, Col, Container, Form, FormControl, ListGroup, Navbar, Row} from "react-bootstrap";
import {FindRestaurant, LoadFoodCategories, LoadMenuForRestaurantByCategory} from "../actions/MenuActions";
import {ERROR, INFO} from "../actions/Utils";
import {GetCurrentUser} from "../actions/UserActions";
import {LoadOrdersOfCustomer} from "../actions/CustomerActions";
import PlainOrder from "./PlainOrder";

class CustomerOrderHistory extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            orders: [],
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        };
        this.loadCustomerOrders = this.loadCustomerOrders.bind(this);
    }

    loadCustomerOrders() {
        LoadOrdersOfCustomer(GetCurrentUser().username)
            .then(ordersData => {
                this.setState({
                    ...this.state,
                    orders: ordersData,
                });
                if(ordersData.length === 0) {
                    this.setState({
                        notification: {
                            show: true,
                            message: "You have no previous orders!",
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
    }

    componentDidMount() {
        this.loadCustomerOrders();
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
                    <Container className="fluid">
                        <ListGroup variant="flush">
                            {this.state.orders.map(item =>
                                <ListGroup.Item key={item.orderNumber}>
                                    <PlainOrder data={item} />
                                </ListGroup.Item>
                            )}
                        </ListGroup>
                    </Container>
                </div>
            </Container>
        );
    }
}

export default CustomerOrderHistory;