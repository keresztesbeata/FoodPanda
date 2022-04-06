import React from 'react'
import {Alert, Button, FormControl, FormGroup, FormLabel} from 'react-bootstrap'
import {RegisterUser} from "../actions/UserActions";
import {ERROR, SUCCESS} from "../actions/Utils";

class Register extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            username: "",
            password: "",
            notification: {
                show: false,
                message: "",
                type: ERROR,
            }
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.hideNotification = this.hideNotification.bind(this);
    }

    handleInputChange(event) {
        // prevent page from reloading
        event.preventDefault();
        const target = event.target
        this.setState({
            [target.name]: target.value,
            notification: {
                show: false
            }
        });
    }


    handleSubmit(event) {
        // prevent page from reloading
        event.preventDefault();
        RegisterUser(this.state.username, this.state.password)
            .then(() => {
                this.setState({
                    notification: {
                        show: true,
                        message: "You have successfully registered! Please log in to access the application!",
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
            notification: {
                show: false
            }
        });
    }

    render() {
        return (
            <div className="background-container-register bg-image d-flex justify-content-center align-items-center">
                <div className="card col-sm-3 border-dark text-left">
                    <form onSubmit={this.handleSubmit} className="card-body">
                        <h3 className="card-title">Register</h3>
                    {
                        (this.state.notification.show) ?
                            <Alert dismissible={true} onClose={this.hideNotification} className={this.state.notification.type === SUCCESS? "alert-success" : "alert-danger"}>
                                {this.state.notification.message}
                            </Alert>
                            :
                            <div/>
                    }
                    <FormGroup className="mb-3" controlId="formBasicText">
                        <FormLabel>Username</FormLabel>
                        <FormControl type="text" placeholder="Enter username" name="username" onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup className="mb-3" controlId="formBasicPassword">
                        <FormLabel>Password</FormLabel>
                        <FormControl type="password" placeholder="Password" name="password" onChange={this.handleInputChange}/>
                    </FormGroup>
                    <Button variant="secondary" type="submit">
                        Register
                    </Button>
                </form>
                </div>
            </div>
        )
    }
}
export default Register;