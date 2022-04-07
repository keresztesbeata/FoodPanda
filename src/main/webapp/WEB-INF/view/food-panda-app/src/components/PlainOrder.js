import React from 'react'
import {Card, Table} from 'react-bootstrap'

class PlainOrder extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            orderNumber: props.data.orderNumber,
            customer: props.data.customer,
            restaurant: props.data.restaurant,
            orderStatus: props.data.orderStatus,
            dateCreated: props.data.dateCreated,
            orderedFoods: props.data.orderedFoods,
            deliveryAddress: props.data.deliveryAddress,
            withCutlery: props.data.withCutlery,
            totalPrice: props.data.totalPrice,
            remark: props.data.remark,
        }
    }

    render() {
        return (
            <Card>
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
                    <Card.Text>
                        Status: {this.state.orderStatus}
                    </Card.Text>
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
                            <tbody>{
                                Object.entries(this.state.orderedFoods).map(
                                    ([food, quantity]) =>
                                        <tr key={this.state.orderNumber + ":" + food}>
                                            <td>{food}</td>
                                            <td>{quantity}</td>
                                        </tr>
                                )
                            }
                            </tbody>
                        </Table>
                        <Card.Subtitle>
                            <Card.Text>
                                Total amount: {this.state.totalPrice} $
                            </Card.Text>
                        </Card.Subtitle>
                    </Card.Body>
                </Card.Body>
            </Card>
        )
    }
}

export default PlainOrder;