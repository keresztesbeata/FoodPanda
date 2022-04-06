import React from 'react'
import {Button, ListGroup} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";
import {LoadCustomerCart} from "../actions/CustomerActions";
import CartItem from "./CartItem";

class OrderDetails extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            orderDetails: {
                orderNumber: "",
                restaurant: "",
                customer: "",
                orderStatus: "",
                dateCreated: null,
                orderedFoods: {},
                deliveryAddress: "",
                withCutlery: false,
                remark: ""
            }
        }
    }

    componentDidMount() {
        const userSession = GetCurrentUser();
        LoadCustomerCart(userSession.username, this.state.name)
            .then(data => {
                this.setState({
                    cart: data
                })
            })
            .catch(error => {
                this.setState({
                    cart: []
                });
            });
    }

    render() {
        return (
            <ListGroup>
                {
                    (this.state.cart.length === 0) ?
                        <p>Empty cart</p>
                        :
                        this.state.cart.map(
                            item =>
                                <CartItem data={item} onRemoveFoodFromCart={this.onRemoveFoodFromCart}/>
                        )
                }
                <Button variant="success" onClick={this.onPlaceOrder} disabled={this.state.cart.length === 0}>
                    Place order
                </Button>
            </ListGroup>
        )
    }
}

export default OrderDetails;