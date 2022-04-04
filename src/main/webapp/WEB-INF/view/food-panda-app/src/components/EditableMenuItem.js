import React from 'react'
import {Alert, Button, Card, Col, ListGroup, Row} from 'react-bootstrap'

class EditableMenuItem extends React.Component {
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
                                    <Button variant="secondary" type="submit">
                                        Edit
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

export default EditableMenuItem;