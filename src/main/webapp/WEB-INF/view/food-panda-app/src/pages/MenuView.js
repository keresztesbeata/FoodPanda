import React from 'react'
import MenuItem from "../components/MenuItem";
import {Alert, Button, Container, Form, FormControl, FormLabel, ListGroup, Nav, Navbar} from "react-bootstrap";
import {LoadFoodCategories, LoadMenuForRestaurant, LoadMenuForRestaurantByCategory} from "../actions/MenuActions";

class MenuView extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurant: "",
            menu: [],
            selectedCategory: "",
            categories: [],
            showError: false,
            errorMessage: "",
        };
        this.loadRestaurantMenu = this.loadRestaurantMenu.bind(this);
        this.loadFoodCategories = this.loadFoodCategories.bind(this);
        this.selectCategory = this.selectCategory.bind(this);
    }

    loadFoodCategories() {
        LoadFoodCategories()
            .then(data => {
                this.setState({
                    categories: data
                });
            })
            .catch(error => {
                this.setState({
                    showError: true,
                    errorMessage: error.message,
                })
            });
    }

    loadRestaurantMenu() {
        const restaurantName = document.getElementById("search-restaurant").value;
        const selectedCategory = document.getElementById("select-category").value;

        if(selectedCategory === "All") {
            LoadMenuForRestaurant(restaurantName)
                .then(restaurantMenu => {
                    if (restaurantMenu.length > 0) {
                        this.setState({
                            restaurant: restaurantName,
                            menu: restaurantMenu
                        });
                    } else {
                        this.setState({
                            restaurant: "",
                            menu: []
                        });
                    }
                })
                .catch(error => {
                    this.setState({
                        showError: true,
                        errorMessage: error.message,
                    })
                });
        }else {
            LoadMenuForRestaurantByCategory(restaurantName, selectedCategory)
                .then(restaurantMenu => {
                    if (restaurantMenu.length > 0) {
                        this.setState({
                            restaurant: restaurantName,
                            menu: restaurantMenu
                        });
                    } else {
                        this.setState({
                            restaurant: "",
                            menu: []
                        });
                    }
                })
                .catch(error => {
                    this.setState({
                        showError: true,
                        errorMessage: error.message,
                    })
                });
        }
    }

    selectCategory(e) {
        e.preventDefault();
        this.setState({
            selectedCategory: e.target.value
        });
    }

    componentDidMount() {
        this.loadFoodCategories();
        this.loadRestaurantMenu();
    }

    render() {

        return (
            <div>
                {(this.state.showError) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
                <Navbar className="justify-content-center">
                <Form className="d-flex">
                    <FormControl
                        type="search"
                        placeholder="Search restaurant..."
                        className="me-2"
                        aria-label="Search"
                        id="search-restaurant"
                    />
                    <Button variant="outline-success" onClick={this.loadRestaurantMenu}>Search</Button>
                </Form>
                </Navbar>
            <div className="flex justify-content-center">
                <div className="header-image-home d-flex justify-content-center align-items-center">
                    <h1 className="text-white">{this.state.restaurant}</h1>
                </div>
                <Navbar className="justify-content-center">
                    <Form className="d-flex">
                    <Form.Select aria-label="Food Category" className="me-2" onSelect={this.selectCategory} id="select-category">
                        <option value="All" key="All">All</option>
                        {
                            this.state.categories.map(category =>
                                <option value={category} key={category}>{category}</option>
                            )
                        }
                    </Form.Select>
                    </Form>
                </Navbar>
                <Container className="fluid">
                    <ListGroup variant="flush">
                        {this.state.menu.map(item =>
                            <ListGroup.Item key={item.name}>
                                <MenuItem isEditable={this.props.isAdmin} data={item}/>
                            </ListGroup.Item>
                        )}
                    </ListGroup>
                </Container>
            </div>
            </div>
        );
    }
}

export default MenuView;