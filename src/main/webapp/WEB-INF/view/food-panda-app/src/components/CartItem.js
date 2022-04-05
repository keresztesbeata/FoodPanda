import React from 'react'
import {Alert, Button, Card} from 'react-bootstrap'
import {LoadFoodDetails} from "../actions/CustomerActions";
import {ERROR, SUCCESS} from "../actions/Utils";

class CartItem extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            name: props.name,
            description: "",
            restaurant: "",
            category: "",
            price: 0,
            quantity: props.quantity,
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        }
        this.onRemoveItemFromCart = this.onRemoveItemFromCart.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
    }

    hideNotification() {
        this.setState({
            notification: {
                show: false
            }
        });
    }

    componentDidMount() {
        LoadFoodDetails(this.state.name)
            .then(data => {
                this.setState({
                    description: data.description,
                    restaurant: data.restaurant,
                    category: data.category,
                    price: data.price
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

    onRemoveItemFromCart() {
        this.props.onRemoveFoodFromCart(this.state.name);
    }

    render() {
        return (
            <Card>
                <Card.Body>
                    {
                        (this.state.notification.show) ?
                            <Alert dismissible={true} onClose={this.hideNotification} className={this.state.notification.type === SUCCESS? "alert-success" : "alert-danger"}>
                                {this.state.notification.message}
                            </Alert>
                            :
                            <div/>
                    }
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
                        Price x Quantity: {this.state.price} $ x {this.state.quantity}
                    </Card.Text>
                    <Button variant="primary" onClick={this.onRemoveItemFromCart}>
                        Remove
                    </Button>
                </Card.Body>
            </Card>
        )
    }
}

export default CartItem;