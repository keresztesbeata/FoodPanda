import React from 'react'
import {Alert, Button, Container, FormCheck, FormControl, FormGroup, FormLabel, ListGroup} from 'react-bootstrap'
import CartItem from "./CartItem";
import {ERROR, SUCCESS} from "../actions/Utils";
import {LoadCustomerCart, RemoveFoodFromCart} from "../actions/CartActions";
import {PlaceOrder} from "../actions/OrderActions";

class CartContent extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            cart: {
                foods: {},
                totalPrice: 0,
            },
            orderDetails: {
                orderNumber: "",
                restaurant: "",
                orderStatus: "",
                dateCreated: null,
                orderedFoods: {},
                deliveryAddress: "",
                withCutlery: false,
                remark: ""
            },
            notification: {
                show: false, message: "", type: ERROR,
            }
        }
        this.onPlaceOrder = this.onPlaceOrder.bind(this);
        this.onRemoveFoodFromCart = this.onRemoveFoodFromCart.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.loadCartContent = this.loadCartContent.bind(this);
        this.onSelectCutlery = this.onSelectCutlery.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
    }

    hideNotification() {
        this.setState({
            notification: {
                show: false
            }
        });
    }

    onPlaceOrder() {
        PlaceOrder(this.state.orderDetails)
            .then(() => {
                this.setState({
                    notification: {
                        show: true,
                        message: "The order was successfully created!",
                        type: SUCCESS
                    }
                });
                this.loadCartContent();
            })
            .catch(error => {
                this.setState({
                    ...this.state, notification: {
                        show: true,
                        message: error.message,
                        type: ERROR
                    }
                });
            });
        document.getElementById("create-order-form").reset();
    }

    onRemoveFoodFromCart(foodName) {
        RemoveFoodFromCart(foodName)
            .then(() => this.loadCartContent())
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        message: error.message,
                        type: ERROR
                    }
                });
            });
    }

    loadCartContent() {
        LoadCustomerCart()
            .then(data => {
                this.setState({
                    ...this.state,
                    cart: data,
                    orderDetails: {
                        ...this.state.orderDetails,
                        orderedFoods: data.foods,
                    }
                })
            })
            .catch(error => {
                this.setState({
                    ...this.state,
                    notification: {
                        show: true,
                        message: error.message,
                        type: ERROR
                    }
                });
            });
    }

    componentDidMount() {
        this.loadCartContent();
    }

    onSelectCutlery(event) {
        // prevent page from reloading
        event.preventDefault();
        const withCutleryChecked = document.getElementById("withCutlery").checked;

        this.setState({
            ...this.state,
            orderDetails: {
                ...this.state.orderDetails,
                withCutlery: withCutleryChecked,
            }
        });
    }

    handleInputChange(event) {
        // prevent page from reloading
        event.preventDefault();
        const target = event.target

        this.setState({
            ...this.state,
            orderDetails: {
                ...this.state.orderDetails,
                [target.name]: target.value
            }, notification: {
                show: false,
            }
        });
    }

    render() {
        return (
            <div>
                <div className="background-header-cart bg-image justify-content-center ">
                </div>
                <Container>
                    <ListGroup>
                        {(this.state.cart.length === 0) ?
                            <p>Empty cart</p> : Object.entries(this.state.cart.foods).map(([food, quantity]) =>
                                <CartItem
                                    name={food} quantity={quantity}
                                    onRemoveFoodFromCart={this.onRemoveFoodFromCart} key={food}/>)}
                    </ListGroup>
                    <form onSubmit={this.handleSubmit} className="card-body" id="create-order-form">
                        <h3 className="card-title">Order details</h3>
                        {
                            (this.state.notification.show) ?
                            <Alert dismissible={true} onClose={this.hideNotification}
                                                                 className={this.state.notification.type}>
                            {this.state.notification.message}
                            </Alert>
                            :
                            <div/>
                        }
                        <FormGroup className="mb-3" controlId="formBasicText">
                            <FormLabel>Total price: {this.state.cart.totalPrice} $</FormLabel>
                        </FormGroup>
                        <FormGroup className="mb-3" controlId="formBasicText">
                            <FormLabel>Delivery Address</FormLabel>
                            <FormControl type="text" required placeholder="Delivery address..." name="deliveryAddress"
                                         onChange={this.handleInputChange}/>
                        </FormGroup>
                        <FormGroup className="mb-3" controlId="formBasicPassword">
                            <FormCheck
                                type="checkbox"
                                name="withCutlery"
                                id="withCutlery"
                                label="Want cutlery"
                                onChange={this.onSelectCutlery}
                            />
                        </FormGroup>
                        <FormGroup className="mb-3" controlId="formBasicText">
                            <FormLabel>Remarks</FormLabel>
                            <FormControl type="text" placeholder="Remarks..." name="remark"
                                         onChange={this.handleInputChange}/>
                        </FormGroup>
                        <Button variant="secondary" onClick={this.onPlaceOrder}>
                            Place order
                        </Button>
                    </form>
                </Container>
            </div>
        )
    }
}

export default CartContent;