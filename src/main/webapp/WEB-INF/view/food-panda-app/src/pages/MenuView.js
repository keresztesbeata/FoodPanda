import React from 'react'
import MenuItem from "../components/MenuItem";
import {
    Alert,
    Button,
    Card,
    Col,
    Container,
    Form,
    FormControl,
    FormLabel,
    ListGroup,
    Nav,
    Navbar, Row
} from "react-bootstrap";
import {
    FindRestaurant,
    LoadFoodCategories,
    LoadMenuForRestaurant,
    LoadMenuForRestaurantByCategory
} from "../actions/MenuActions";

class MenuView extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            restaurant: {
                name: "",
                address: "",
                deliveryZones: [],
                admin: "",
                openingHour: 0,
                closingHour: 0,
                deliveryFee: 0,
            },
            menu: [],
            selectedCategory: "",
            categories: [],
            showError: false,
            errorMessage: "",
        };
        this.loadRestaurantMenu = this.loadRestaurantMenu.bind(this);
        this.loadFoodCategories = this.loadFoodCategories.bind(this);
        this.onSelectCategory = this.onSelectCategory.bind(this);
        this.loadRestaurant = this.loadRestaurant.bind(this);
        this.resetRestaurantInformation = this.resetRestaurantInformation.bind(this);
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

    loadRestaurant() {
        let restaurantName = document.getElementById("search-restaurant").value;

        if(restaurantName === null || restaurantName === "") {
            return false;
        }

        FindRestaurant(restaurantName)
            .then(data => {
                this.setState({
                    restaurant: data,
                    showError: false,
                });
                return true;
            }).catch(error => {
            this.setState({
                showError: true,
                errorMessage: error.message,
            });
            this.resetRestaurantInformation();
        });
        return false;
    }

    resetRestaurantInformation() {
        this.setState({
            restaurant: {
                name: "",
                address: "",
                deliveryZones: [],
                admin: "",
                openingHour: 0,
                closingHour: 0,
                deliveryFee: 0,
            },
            menu: [],
        })
    }

    loadRestaurantMenu() {
        let validRestaurant = this.loadRestaurant()

        // do not display anything if restaurant is invalid!
        if(!validRestaurant) {
            return;
        }

        let menu = (this.state.selectedCategory === "All")?
            LoadMenuForRestaurant(this.state.restaurant.name)
                :
            LoadMenuForRestaurantByCategory(this.state.restaurant.name, this.state.selectedCategory)
                ;

        menu
                .then(restaurantMenu => {
                    if (restaurantMenu.length > 0) {
                        this.setState({
                            menu: restaurantMenu,
                            showError: false,
                        });
                    } else {
                        this.setState({
                            menu: [],
                            showError: false,
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

    onSelectCategory(e) {
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
                    <Card className={(this.state.restaurant.name !== "")?"visible transparent-background" : "invisible"}>
                        <Card.Title className="text-center">
                            {this.state.restaurant.name}
                        </Card.Title>
                        <Card.Body>
                            <Row>
                            <Col>
                            <Card.Text>
                                <b>Address</b> : {this.state.restaurant.address}
                            </Card.Text>
                            <Card.Text>
                                <b>DeliveryFee</b> : {this.state.restaurant.deliveryFee} $
                            </Card.Text>
                            <Card.Text>
                                <b>Open - Close</b> : {this.state.restaurant.openingHour}:00 - {this.state.restaurant.closingHour}:00
                            </Card.Text>
                            </Col>
                            <Col>
                            <ul>
                                <Card.Text>
                                    <b>DeliveryZones</b> :
                                        {this.state.restaurant.deliveryZones
                                            .sort()
                                            .map(deliveryZone =>
                                            <li key={deliveryZone}>
                                                {deliveryZone}
                                            </li>
                                        )}
                                </Card.Text>
                            </ul>
                            </Col>
                            </Row>
                        </Card.Body>
                    </Card>
                </div>
                <Navbar className="justify-content-center">
                    <Form className="d-flex">
                    <Form.Select aria-label="Food Category" className="me-2" onSelect={this.onSelectCategory} id="select-category">
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