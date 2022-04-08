import React from 'react'
import {Alert, Button, FormControl, FormSelect, InputGroup} from 'react-bootstrap'
import {ERROR, SUCCESS} from "../actions/Utils";
import {AddFood, LoadAdminsRestaurant} from "../actions/RestaurantActions";
import {LoadFoodCategories} from "../actions/MenuActions";
import {GetCurrentUser} from "../actions/UserActions";

class AddFoodView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allFoodCategories: [],
            restaurantName: "",
            food: {
                name: "",
                category: "",
                restaurant: "",
                portion: 0,
                description: "",
                price: 0.0,
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
    }

    componentDidMount() {
        LoadFoodCategories()
            .then(foodCategories => {
                LoadAdminsRestaurant(GetCurrentUser().username)
                    .then(restaurantData => {
                        this.setState({
                            ...this.state,
                            allFoodCategories: foodCategories,
                            restaurantName: restaurantData.name,
                            food: {
                                ...this.state.food,
                                restaurant: restaurantData.name,
                                category: foodCategories[0],
                            }
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
            food: {
                ...this.state.food,
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
                ...this.state,
                notification: {
                    show: true,
                    message: "Input must be a number!",
                    type: ERROR,
                },
            });
        } else {
            this.setState({
                ...this.state,
                food: {
                    ...this.state.food,
                    [target.name]: value,
                },
                notification: {
                    show: false
                }
            });
        }
    }


    handleSubmit(event) {
        // prevent page from reloading
        event.preventDefault();

        AddFood(this.state.food)
            .then(() => {
                this.setState({
                    notification: {
                        show: true,
                        message: "The food has been successfully added!",
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

        document.getElementById("add-food-form").reset();
    }

    hideNotification() {
        this.setState({
            ...this.state,
            notification: {
                show: false
            }
        });
    }

    render() {
        return (
            <div className="background-container-food bg-image d-flex justify-content-center align-items-center">
                <div className="card col-sm-5 border-dark text-left">
                    <form onSubmit={this.handleSubmit} className="card-body" id="add-food-form">
                        <h3 className="card-title text-center">Add food</h3>
                        {
                            (this.state.notification.show) ?
                                <Alert dismissible={true} onClose={this.hideNotification}
                                       className={this.state.notification.type}>
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
                            <InputGroup.Text>Restaurant</InputGroup.Text>
                            <FormControl type="text" required placeholder="Restaurant" disabled
                                         defaultValue={this.state.food.restaurant}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Description</InputGroup.Text>
                            <FormControl type="text-area" placeholder="Description" name="description"
                                         onChange={this.handleInputChange}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Category</InputGroup.Text>
                            <FormSelect placeholder="Select category" name="category"
                                        onChange={this.handleInputChange} required>
                                {
                                    this.state.allFoodCategories.map(foodCategory =>
                                        <option value={foodCategory} key={"new_" + foodCategory}>
                                            {foodCategory}
                                        </option>
                                    )
                                }
                            </FormSelect>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Price</InputGroup.Text>
                            <FormControl type="text" placeholder="Price" name="price" defaultValue={0}
                                         onChange={this.handleNumericInputChange}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Portion</InputGroup.Text>
                            <InputGroup.Text>(grams)</InputGroup.Text>
                            <FormControl type="text" placeholder="Portion" name="portion" defaultValue={0.0}
                                         onChange={this.handleNumericInputChange}/>
                        </InputGroup>
                        <div className="text-center">
                            <Button variant="secondary" type="submit">
                                Add food
                            </Button>
                        </div>
                    </form>
                </div>
            </div>
        )
    }
}

export default AddFoodView;