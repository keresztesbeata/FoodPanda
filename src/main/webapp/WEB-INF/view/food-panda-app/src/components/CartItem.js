import React from 'react'
import {Alert, Button, Card} from 'react-bootstrap'
import {LoadFoodDetails} from "../actions/CustomerActions";

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
            showError: false,
            errorMessage: "",
        }
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
                    showError: true,
                    errorMessage: error.message,
                });
            });
    }

    render() {
        return (
            <Card>
                <Card.Body>
                    {(this.state.showError) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
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
                    <Button variant="primary" onClick={this.props.onRemoveFoodFromCart}>
                        Remove
                    </Button>
                </Card.Body>
            </Card>
        )
    }
}

export default CartItem;