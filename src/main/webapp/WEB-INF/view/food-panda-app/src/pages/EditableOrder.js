import React from 'react'
import {Button, Card, ListGroup, Table} from 'react-bootstrap'
import {ERROR, SUCCESS} from "../actions/Utils";
import {AcceptOrder, LoadOrderByOrderNumber, LoadOrdersStatuses} from "../actions/OrderActions";

class EditableOrder extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            allOrderStatuses: [],
            orderNumber: props.orderNumber,
            order: null,
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        }
        this.loadInitialData = this.loadInitialData.bind(this);
        this.onAcceptOrder = this.onAcceptOrder.bind(this);
        this.onDeclineOrder = this.onDeclineOrder.bind(this);
        this.onSetOrderInDelivery = this.onSetOrderInDelivery.bind(this);
        this.setOrderDelivered = this.setOrderDelivered.bind(this);
    }

    loadInitialData() {
        LoadOrdersStatuses()
            .then(orderStatuses =>
                LoadOrderByOrderNumber(this.state.orderNumber)
                    .then(orderDetails => {
                        this.setState({
                            ...this.state,
                            allOrderStatuses: orderStatuses,
                            order: orderDetails,
                        })
                    })
                    .catch(error => {
                        this.setState({
                            notification: {
                                show: true, message: error.message, type: ERROR
                            }
                        });
                    }))
            .catch(error => {
                this.setState({
                    notification: {
                        show: true, message: error.message, type: ERROR
                    }
                });
            });
    }

    componentDidMount() {
        this.loadInitialData();
    }

    onAcceptOrder() {
        AcceptOrder(this.state.orderNumber)
            .then(updatedOrder =>
                this.setState({
                    ...this.state,
                    order: updatedOrder,
                    notification: {
                        show: true,
                        message: "Status of the order has been successfully updated to ACCEPTED!",
                        type: SUCCESS
                    }
                }))
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
        return (<Card>
            <Card.Body>
                <Card.Title className="card-title">
                    Order #{this.state.orderNumber}
                </Card.Title>
                <Card.Subtitle className="mb-2 text-muted">
                    {this.state.customer}
                </Card.Subtitle>
                <Card.Text>
                    Restaurant: {this.state.restaurant}
                </Card.Text>
                <Card.Text>
                    Created at: {this.state.dateCreated}
                </Card.Text>
                <Card>
                    <Card.Title>
                        Status: {this.state.orderStatus}
                    </Card.Title>
                    <Card.Body>
                        <ListGroup horizontal>
                            <Button variant="success" onClick={this.onAcceptOrder}>
                                Accept
                            </Button>
                        </ListGroup>
                    </Card.Body>
                </Card>
                <Card.Text>
                    Delivery address: {this.state.deliveryAddress}
                </Card.Text>
                <Card.Text>
                    With cutlery: {this.state.withCutlery ? "Yes" : "No"}
                </Card.Text>
                <Card.Text>
                    Remark: {this.state.remark}
                </Card.Text>
                <Card.Body>
                    <Card.Title>Ordered foods:</Card.Title>
                    <Table>
                        <tbody>{Object.entries(this.state.orderedFoods).map(([food, quantity]) => <tr
                            key={this.state.orderNumber + ":" + food}>
                            <td>{food}</td>
                            <td>{quantity}</td>
                        </tr>)}
                        </tbody>
                    </Table>
                    <Card.Subtitle>
                        <Card.Text>
                            Total amount: {this.state.totalPrice} $
                        </Card.Text>
                    </Card.Subtitle>
                </Card.Body>
            </Card.Body>
        </Card>)
    }
}

export default EditableOrder;