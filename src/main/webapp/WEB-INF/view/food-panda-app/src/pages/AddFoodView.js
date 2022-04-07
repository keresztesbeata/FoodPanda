import React from 'react'
import {Alert, Button, FormControl, FormSelect, InputGroup} from 'react-bootstrap'
import {ERROR, SUCCESS} from "../actions/Utils";
import {AddFood, LoadAdminsRestaurant} from "../actions/AdminActions";
import {LoadFoodCategories, LoadMenuForRestaurantByCategory} from "../actions/MenuActions";
import {GetCurrentUser} from "../actions/UserActions";

class AddFoodView extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            allFoodCategories: [],
            selectedFoodCategory: "-",
            food: {
                name: "",
                category: "All",
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
            .then(data => {
                this.setState({
                    ...this.state,
                    allFoodCategories: data,
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

        LoadAdminsRestaurant(GetCurrentUser().username)
            .then(restaurantData => {
                this.setState({
                    ...this.state,
                    food: {
                        ...this.state.food,
                        restaurant: restaurantData.name
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

        console.log(this.state.food)
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
            return;
        }

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


    handleSubmit(event) {
        // prevent page from reloading
        event.preventDefault();

        console.log(this.state.food)

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
                <div className="card col-lg-5 border-dark text-left">
                    <form onSubmit={this.handleSubmit} className="card-body">
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
                            <FormControl type="text" required placeholder="Restaurant" disabled value={this.state.food.restaurant}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Description</InputGroup.Text>
                            <FormControl type="text-area" required placeholder="Description" name="description"
                                         onChange={this.handleInputChange}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Category</InputGroup.Text>
                            <FormSelect placeholder="Select category" name="selectedFoodCategory"
                                        onChange={this.handleInputChange}>
                                <option value="All" key="All">All</option>
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
                            <FormControl type="text" placeholder="Price" name="price" defaultValue="0"
                                         onChange={this.handleNumericInputChange}/>
                        </InputGroup>
                        <InputGroup className="mb-3">
                            <InputGroup.Text>Portion</InputGroup.Text>
                            <InputGroup.Text>(grams)</InputGroup.Text>
                            <FormControl type="text" placeholder="Portion" name="portion" defaultValue="0"
                                         onChange={this.handleNumericInputChange}/>
                        </InputGroup>
                        <div className="text-center">
                            <Button variant="secondary" type="submit">
                                Add food
                            </Button>
                        </div>
                    </form>
                </div>
        )
    }
}

export default AddFoodView;