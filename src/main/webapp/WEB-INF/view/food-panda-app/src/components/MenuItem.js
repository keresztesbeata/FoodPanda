import React from 'react'
import {Alert, Button, Card, Col, ListGroup, Row} from 'react-bootstrap'

import {ERROR, SUCCESS} from "../actions/Utils";
import {AddFoodToCart} from "../actions/CartActions";

class MenuItem extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            name: props.data.name,
            description: props.data.description,
            restaurant: props.data.restaurant,
            category: props.data.category,
            price: props.data.price,
            quantity: 1,
            notification: {
                show: false, message: "", type: ERROR,
            }
        }
        this.incrementQuantity = this.incrementQuantity.bind(this);
        this.decrementQuantity = this.decrementQuantity.bind(this);
        this.onAddFoodToCart = this.onAddFoodToCart.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
    }

    incrementQuantity() {
        const prevQuantity = this.state.quantity
        this.setState({
            quantity: prevQuantity + 1
        })
    }

    decrementQuantity() {
        const prevQuantity = this.state.quantity
        this.setState({
            quantity: prevQuantity - 1
        })
    }

    onAddFoodToCart() {
        AddFoodToCart(this.state.name, this.state.quantity)
            .then(() => {
                this.setState({
                    notification: {
                        show: true,
                        message: this.state.quantity + " x " + this.state.name + " has been added to the cart!",
                        type: SUCCESS
                    }
                });
            })
            .catch(error => {
                this.setState({
                    notification: {
                        show: true, message: error.message, type: ERROR
                    }
                });
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
        return (<Card>
            <Card.Body>
                {(this.state.notification.show) ? <Alert dismissible={true} onClose={this.hideNotification}
                                                         className={this.state.notification.type}>
                    {this.state.notification.message}
                </Alert> : <div/>}
                <Row>
                    <Col>
                        <Card.Title className="card-title">
                            {this.state.name}
                        </Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">
                            {this.state.category}
                        </Card.Subtitle>
                        <Card.Text>
                            {this.state.description}
                        </Card.Text>
                        <Card.Text>
                            Price: {this.state.price} $
                        </Card.Text>
                    </Col>
                    <Col>
                        <ListGroup horizontal>
                            <ListGroup.Item>
                                <Button variant="outline-danger" onClick={this.decrementQuantity}
                                        disabled={this.state.quantity <= 1}>
                                    -
                                </Button>
                            </ListGroup.Item>
                            <ListGroup.Item>
                                {this.state.quantity}
                            </ListGroup.Item>
                            <ListGroup.Item>
                                <Button variant="outline-success" onClick={this.incrementQuantity}>
                                    +
                                </Button>
                            </ListGroup.Item>
                            <ListGroup.Item>
                                <Button variant="outline-secondary" onClick={this.onAddFoodToCart}>
                                    Add to cart
                                </Button>
                            </ListGroup.Item>
                        </ListGroup>
                    </Col>
                </Row>
            </Card.Body>
        </Card>)
    }
}

export default MenuItem;