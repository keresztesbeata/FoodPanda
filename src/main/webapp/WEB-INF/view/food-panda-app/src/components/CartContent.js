import React from 'react'
import {Alert, Button, Card, Col, Container, FormControl, FormGroup, FormLabel, ListGroup, Row} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";
import {LoadCustomerCart, PlaceOrder, RemoveFoodFromCart} from "../actions/CustomerActions";
import CartItem from "./CartItem";
import FormCheckInput from "react-bootstrap/FormCheckInput";

class CartContent extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            cart: {
                customerName: GetCurrentUser().username,
                foods: {},
                totalPrice: 0,
            },
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
            showNotification: false
        }
        this.onPlaceOrder = this.onPlaceOrder.bind(this);
        this.onRemoveFoodFromCart = this.onRemoveFoodFromCart.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
    }

    onPlaceOrder() {
        const userSession = GetCurrentUser()
        this.setState({
            orderDetails: {
                customer: userSession.username,
            }
        });

        PlaceOrder(this.state.orderDetails)
            .catch(error => {
                this.setState({
                    showError: true,
                    errorMessage: error.message,
                })
            });
    }

    onRemoveFoodFromCart() {
        const userSession = GetCurrentUser()
        RemoveFoodFromCart(userSession.username, this.state.name)
            .catch(error => {
                this.setState({
                    showError: true,
                    errorMessage: error.message,
                })
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

    handleInputChange(event) {
        // prevent page from reloading
        event.preventDefault();
        const target = event.target
        this.setState({
            orderDetails:{
                [target.name]: target.value
            },
            showError: false
        });
    }

    render() {
        return (
            <Container>
            <ListGroup>
                {
                    (this.state.cart.length === 0)?
                        <p>Empty cart</p>
                        :
                        Object.entries(this.state.cart.foods).map(
                            ([food,quantity]) =>
                                    <CartItem name={food} quantity={quantity} onRemoveFoodFromCart={this.onRemoveFoodFromCart} key={food}/>
                            )
                }
            </ListGroup>
                <form onSubmit={this.handleSubmit} className="card-body">
                    <h3 className="card-title">Order details</h3>
                    {(this.state.showError) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
                    <FormGroup className="mb-3" controlId="formBasicText">
                        <FormLabel>Delivery Address</FormLabel>
                        <FormControl type="text" placeholder="Delivery address..." name="deliveryAddress" onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup className="mb-3" controlId="formBasicPassword">
                        <FormLabel>Want cutlery</FormLabel>
                        <FormCheckInput placeholder="With cutlery" name="withCutlery" onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup className="mb-3" controlId="formBasicText">
                        <FormLabel>Remarks</FormLabel>
                        <FormControl type="text" placeholder="Remarks..." name="remark" onChange={this.handleInputChange}/>
                    </FormGroup>
                    <Button variant="secondary" type="submit">
                        Place order
                    </Button>
                </form>
            </Container>
        )
    }
}

export default CartContent;