import React from 'react'
import {Alert, Container, ListGroup, ListGroupItem} from "react-bootstrap";
import {ERROR, INFO} from "../actions/Utils";
import {GetCurrentUser} from "../actions/UserActions";
import PlainOrder from "../components/PlainOrder";
import {LoadOrdersOfCustomer} from "../actions/OrderActions";

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
                if (ordersData.length === 0) {
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
                                <ListGroupItem key={item.orderNumber}>
                                    <PlainOrder data={item}/>
                                    <b>Status:</b> {this.state.orderStatus}
                                </ListGroupItem>
                                )}
                        </ListGroup>
                    </Container>
                </div>
            </Container>
        );
    }
}

export default CustomerOrderHistory;