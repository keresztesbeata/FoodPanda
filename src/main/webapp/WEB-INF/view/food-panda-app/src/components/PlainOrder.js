import React from 'react'
import {Accordion, Card, Table} from 'react-bootstrap'
import {LoadOrderByOrderNumber} from "../actions/OrderActions";

class PlainOrder extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = props.data;
        this.loadOrderData = this.loadOrderData.bind(this);
    }

    loadOrderData() {
        LoadOrderByOrderNumber(this.state.orderNumber)
            .then(orderData => {
                this.setState(orderData)
            })
            .catch(e => {
                console.log(e);
            });
    }

    render() {
        if (this.props.data.orderStatus !== this.state.orderStatus) {
            this.loadOrderData();
        }
        return (
            <Accordion>
                <Accordion.Item eventKey="0">
                    <Accordion.Header>
                        <Card.Body>
                            <Card.Title className="card-title">
                                Order #{this.state.orderNumber}
                            </Card.Title>
                            <Card.Subtitle className="mb-2 text-muted">
                                {this.state.customer}
                            </Card.Subtitle>
                        </Card.Body>
                    </Accordion.Header>
                    <Accordion.Body>
                        <Card.Body id={this.state.orderNumber + "_collapsable"} className="collapse show"
                                   aria-labelledby="headingOne"
                                   data-parent={"#" + this.state.orderNumber + "_accordion"}>
                            <Card.Text>
                                <b>Restaurant:</b> {this.state.restaurant}
                            </Card.Text>
                            <Card.Text>
                                <b>Created at:</b> {this.state.dateCreated}
                            </Card.Text>
                            <Card.Text>
                                <b>Status:</b> {this.state.orderStatus}
                            </Card.Text>
                            <Card.Text>
                                <b>Delivery address:</b> {this.state.deliveryAddress}
                            </Card.Text>
                            <Card.Text>
                                <b>With cutlery:</b> {this.state.withCutlery ? "Yes" : "No"}
                            </Card.Text>
                            <Card.Text>
                                <b>Remark:</b> {this.state.remark}
                            </Card.Text>

                            <Card.Text><b>Ordered foods:</b></Card.Text>
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
                                    <b>Total amount:</b> {this.state.totalPrice} <b>$</b>
                                </Card.Text>
                            </Card.Subtitle>

                        </Card.Body>
                    </Accordion.Body>
                </Accordion.Item>
            </Accordion>
        )
    }
}

export default PlainOrder;