import React from 'react'
import {Card} from 'react-bootstrap'

class PlainMenuItem extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            name: props.data.name,
            description: props.data.description,
            restaurant: props.data.restaurant,
            category: props.data.category,
            price: props.data.price,
        }
    }

    render() {
        return (<Card>
                <Card.Body>
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
                </Card.Body>
            </Card>)
    }
}

export default PlainMenuItem;