import React from 'react'
import {Alert, Button, Card, Col, ListGroup, Row} from 'react-bootstrap'
import {GetCurrentUser} from "../actions/UserActions";
import {AddFoodToCart} from "../actions/CustomerActions";

class MenuItem extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            name: props.data.name,
            description: props.data.description,
            restaurant: props.data.restaurant,
            category: props.data.category,
            price: props.data.price,
            quantity: 1,
            showError: false,
            errorMessage: "",
        }
        this.incrementQuantity = this.incrementQuantity.bind(this);
        this.decrementQuantity = this.decrementQuantity.bind(this);
        this.onAddFoodToCart = this.onAddFoodToCart.bind(this);
    }

    incrementQuantity() {
        const prevQuantity = this.state.quantity
        this.setState({
            quantity: prevQuantity + 1
        })
    }

    decrementQuantity() {
        const prevQuantity = this.state.quantity
        this.setState({
            quantity: prevQuantity - 1
        })
    }

    onAddFoodToCart() {
        const userSession = GetCurrentUser()
        AddFoodToCart(userSession.username, this.state.name, this.state.quantity)
            .catch(error => {
                this.setState({
                    showError: true,
                    errorMessage: error.message,
                })
                this.props.showCartContent = true;
            });
    }

    render() {
        return (
          <Card>
              <Card.Body>
                  {(this.state.showError) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
                  <Row>
                  <Col>
                  <Card.Title className="card-title">
                      {this.state.name}
                  </Card.Title>
                  <Card.Subtitle className="mb-2 text-muted">
                      {this.state.category}
                  </Card.Subtitle>
                  <Card.Text>
                      {this.state.description}
                  </Card.Text>
                  <Card.Text>
                      Price: {this.state.price} $
                  </Card.Text>
                  </Col>
                  <Col>
                      <ListGroup horizontal>
                          <ListGroup.Item>
                              <Button variant="outline-danger" onClick={this.decrementQuantity} disabled={this.state.quantity <= 1}>
                                  -
                              </Button>
                          </ListGroup.Item>
                          <ListGroup.Item>
                              {this.state.quantity}
                          </ListGroup.Item>
                          <ListGroup.Item>
                              <Button variant="outline-success" onClick={this.incrementQuantity}>
                                  +
                              </Button>
                          </ListGroup.Item>
                          <ListGroup.Item>
                              <Button variant="outline-secondary" onClick={this.onAddFoodToCart}>
                                  Add to cart
                              </Button>
                          </ListGroup.Item>
                      </ListGroup>
                  </Col>
                  </Row>
              </Card.Body>
          </Card>
        )
    }
}

export default MenuItem;