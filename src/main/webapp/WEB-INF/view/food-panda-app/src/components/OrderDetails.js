import React from 'react'
import {Alert, Button, Card, Col, ListGroup, Row} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";
import {LoadCustomerCart, PlaceOrder, RemoveFoodFromCart} from "../actions/CustomerActions";
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
            },
            showError: false,
            errorMessage: "",
        }
        this.onUpdateOrderStatus = this.onUpdateOrderStatus.bind(this);
    }

    // todo
    onUpdateOrderStatus() {
        this.setState({
            orderDetails: {
                orderStatus: "",
            }
        });

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
                    showError: true,
                    errorMessage: error.message,
                });
            });
    }

    render() {
        return (
            <ListGroup>
                {
                    (this.state.cart.length === 0)?
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