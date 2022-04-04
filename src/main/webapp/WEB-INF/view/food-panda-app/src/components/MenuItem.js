import React from 'react'
import {Button, Card, Col, ListGroup, Row} from 'react-bootstrap'

class MenuItem extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            name: props.data.name,
            description: props.data.description,
            restaurant: props.data.restaurant,
            category: props.data.category,
            price: props.data.price,
            quantity: 1
        }
        this.incrementQuantity = this.incrementQuantity.bind(this);
        this.decrementQuantity = this.decrementQuantity.bind(this);
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

    render() {
        return (
          <Card>
              <Card.Body>
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
                  {
                      (this.props.isEditable) ?
                          <ListGroup horizontal>
                              <ListGroup.Item>
                                  <Button variant="secondary" type="submit">
                                      Edit
                                  </Button>
                              </ListGroup.Item>
                          </ListGroup>
                          :
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
                                  <Button variant="outline-secondary" type="submit">
                                      Add to cart
                                  </Button>
                              </ListGroup.Item>
                          </ListGroup>
                  }
                  </Col>
                  </Row>
              </Card.Body>
          </Card>
        )
    }
}

export default MenuItem;