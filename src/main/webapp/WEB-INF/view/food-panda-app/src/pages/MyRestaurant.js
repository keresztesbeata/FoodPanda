import React from 'react'
import {Alert, Button, Card, Container, FormControl, InputGroup, Table} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";
import {LoadAdminsRestaurant} from "../actions/RestaurantActions";
import {ERROR, INFO} from "../actions/Utils";
import {Link} from "react-router-dom";

class MyRestaurant extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
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
    }

    componentDidMount() {
        LoadAdminsRestaurant(GetCurrentUser().username)
            .then(data => {
                this.setState({
                    ...this.state,
                    restaurant: data,
                });
            })
            .catch(error => {
                this.setState({
                    notification: {
                        show: true,
                        message: "Add your restaurant to the app to begin!",
                        type: INFO,
                    }
                });
            });
    }

    render() {
        return (
            (this.state.notification.show) ?

                <Container className="text-center">
                    <Card className="sm-3">
                        <Card.Body>
                            <Alert
                                className={this.state.notification.type}>
                                {this.state.notification.message}
                            </Alert>
                            <Button variant="outline-info">
                                <Link to="/admin/restaurant/new" className="text-decoration-none">
                                    Add restaurant
                                </Link>
                            </Button>
                        </Card.Body>
                    </Card>
                </Container>
                :
                <div
                    className="background-container-register bg-image d-flex justify-content-center align-items-center">
                    <div className="card col-lg-5 border-dark text-left">
                        <Card className="card-body">
                            <h3 className="card-title text-center">My restaurant</h3>
                            <Card.Body>
                                <InputGroup className="mb-3">
                                    <InputGroup.Text>Name</InputGroup.Text>
                                    <FormControl type="text" placeholder="Name" disabled
                                                 value={this.state.restaurant.name}/>
                                </InputGroup>
                                <InputGroup className="mb-3">
                                    <InputGroup.Text>Address</InputGroup.Text>
                                    <FormControl type="text" placeholder="Address" disabled
                                                 value={this.state.restaurant.address}/>
                                </InputGroup>
                                <InputGroup className="mb-3">
                                    <InputGroup.Text>Delivery zones</InputGroup.Text>
                                    <Table>
                                        <tbody>
                                        {
                                            this.state.restaurant.deliveryZones.map(deliveryZone =>
                                                <tr key={"table" + deliveryZone}>
                                                    <td>{deliveryZone}</td>
                                                </tr>
                                            )
                                        }
                                        </tbody>
                                    </Table>
                                </InputGroup>
                                <InputGroup className="mb-3">
                                    <InputGroup.Text>Delivery fee</InputGroup.Text>
                                    <FormControl type="text" placeholder="Delivery fee" disabled
                                                 value={this.state.restaurant.deliveryFee}/>
                                </InputGroup>
                                <InputGroup className="mb-3">
                                    <InputGroup.Text>Opening hour</InputGroup.Text>
                                    <FormControl type="text" placeholder="Opening hour" disabled
                                                 value={this.state.restaurant.openingHour}/>
                                    <InputGroup.Text>Closing hour</InputGroup.Text>
                                    <FormControl type="text" placeholder="Closing hour" disabled
                                                 value={this.state.restaurant.closingHour}/>
                                </InputGroup>
                            </Card.Body>
                        </Card>
                    </div>
                </div>
        )
    }
}

export default MyRestaurant;