import React from 'react'
import {Alert, Button, FormControl, FormSelect, InputGroup, Table} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";
import {ERROR, SUCCESS} from "../actions/Utils";
import {AddRestaurant, LoadDeliveryZones} from "../actions/AdminActions";

class NewRestaurant extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allDeliveryZones: [],
            selectedDeliveryZone: "-",
            restaurant: {
                name: "",
                address: "",
                admin: GetCurrentUser().username,
                deliveryZones: [],
                openingHour: 0,
                closingHour: 0,
                deliveryFee: 0.0,
            },
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleNumericInputChange = this.handleNumericInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
        this.handleAddNewDeliveryZone = this.handleAddNewDeliveryZone.bind(this);
        this.handleRemoveDeliveryZone = this.handleRemoveDeliveryZone.bind(this);
        this.handleSelectDeliveryZone = this.handleSelectDeliveryZone.bind(this);
        this.resetRestaurantData = this.resetRestaurantData.bind(this);
    }

    componentDidMount() {
        LoadDeliveryZones()
            .then(data => {
                this.setState({
                    ...this.state,
                    allDeliveryZones: data,
                });
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

    handleInputChange(event) {
        // prevent page from reloading
        event.preventDefault();
        const target = event.target;
        let value = target.value;

        this.setState({
            ...this.state,
            restaurant: {
                ...this.state.restaurant,
                [target.name]: value,
            },
            notification: {
                show: false
            }
        });
    }

    handleNumericInputChange(event) {
        // prevent page from reloading
        event.preventDefault();
        const target = event.target;
        let value = target.value;

        if (isNaN(value)) {
            this.setState({
                notification: {
                    show: true,
                    message: "Input must be a number!",
                    type: ERROR,
                },
            });
            return;
        }

        this.setState({
            ...this.state,
            restaurant: {
                ...this.state.restaurant,
                [target.name]: value,
            },
            notification: {
                show: false
            }
        });
    }


    handleSubmit(event) {
        // prevent page from reloading
        event.preventDefault();

        AddRestaurant(this.state.restaurant)
            .then(() => {
                this.setState({
                    notification: {
                        show: true,
                        message: "The restaurant has been successfully added!",
                        type: SUCCESS
                    }
                });
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

    resetRestaurantData() {
        this.setState({
            ...this.state,
            restaurant: {
                name: "",
                address: "",
                admin: GetCurrentUser().username,
                deliveryZones: [],
                openingHour: 0,
                closingHour: 0,
                deliveryFee: 0.0,
            },
        })
    }

    handleAddNewDeliveryZone() {
        if (this.state.selectedDeliveryZone === "-" || this.state.restaurant.deliveryZones.includes(this.state.selectedDeliveryZone)) {
            return;
        }
        const deliveryZones = this.state.restaurant.deliveryZones;

        deliveryZones.push(this.state.selectedDeliveryZone);

        this.setState({
            ...this.state,
            restaurant: {
                ...this.state.restaurant,
                deliveryZones: deliveryZones,
            }
        });
    }

    handleRemoveDeliveryZone(deliveryZoneToRemove) {
        const deliveryZones = this.state.restaurant.deliveryZones;
        const updatedDeliveryZones = deliveryZones.filter(deliveryZone => deliveryZone !== deliveryZoneToRemove);

        this.setState({
            ...this.state,
            restaurant: {
                ...this.state.restaurant,
                deliveryZones: updatedDeliveryZones,
            }
        });
    }

    handleSelectDeliveryZone() {
        const deliveryZone = document.getElementById("deliveryZones").value;

        this.setState({
            ...this.state,
            selectedDeliveryZone: deliveryZone,
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
        return (
            <div className="background-container-register bg-image d-flex justify-content-center align-items-center">
                <div className="card col-lg-5 border-dark text-left">
                    <form onSubmit={this.handleSubmit} className="card-body">
                        <h3 className="card-title text-center">Add restaurant</h3>
                        {
                            (this.state.notification.show) ?
                                <Alert dismissible={true} onClose={this.hideNotification}
                                       className={this.state.notification.type === SUCCESS ? "alert-success" : "alert-danger"}>
                                    {this.state.notification.message}
                                </Alert>
                                :
                                <div/>
                        }
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Name</InputGroup.Text>
                            <FormControl type="text" required placeholder="Name" name="name"
                                         onChange={this.handleInputChange}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Address</InputGroup.Text>
                            <FormControl type="text" required placeholder="Address" name="address"
                                         onChange={this.handleInputChange}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Delivery zones</InputGroup.Text>
                            <Table>
                                <tbody>
                                {
                                    this.state.restaurant.deliveryZones.map(deliveryZone =>
                                        <tr key={"table" + deliveryZone}>
                                            <td>{deliveryZone}</td>
                                            <td>
                                                <Button variant="danger"
                                                        onClick={() => this.handleRemoveDeliveryZone(deliveryZone)}>Remove</Button>
                                            </td>
                                        </tr>
                                    )
                                }
                                </tbody>
                            </Table>
                            <FormSelect placeholder="Delivery zones" id="deliveryZones"
                                        onChange={this.handleSelectDeliveryZone}>
                                <option value="-" key="-">-</option>
                                {
                                    this.state.allDeliveryZones.map(deliveryZone =>
                                        <option value={deliveryZone} key={"new_" + deliveryZone}>
                                            {deliveryZone}
                                        </option>
                                    )
                                }
                            </FormSelect>
                            <Button variant="outline-success" onClick={this.handleAddNewDeliveryZone}>Add</Button>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Delivery fee</InputGroup.Text>
                            <FormControl type="text" placeholder="Delivery fee" name="deliveryFee" defaultValue="0"
                                         onChange={this.handleNumericInputChange}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Opening hour</InputGroup.Text>
                            <FormControl type="text" placeholder="Opening hour" name="openingHour" defaultValue="0"
                                         onChange={this.handleNumericInputChange}/>
                            <InputGroup.Text>Closing hour</InputGroup.Text>
                            <FormControl type="text" placeholder="Closing hour" name="closingHour" defaultValue="0"
                                         onChange={this.handleNumericInputChange}/>
                        </InputGroup>
                        <div className="text-center">
                            <Button variant="secondary" type="submit">
                                Add restaurant
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}

export default NewRestaurant;