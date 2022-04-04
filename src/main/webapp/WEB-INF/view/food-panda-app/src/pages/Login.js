import React from 'react'
import {FormControl, FormLabel, FormGroup, Button, Alert, Image} from 'react-bootstrap'
import {LoginUser} from "../actions/UserActions";

class Login extends React.Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            username: "",
            password: "",
            showError: false,
            errorMessage: "",
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        // prevent page from reloading
        event.preventDefault();
        const target = event.target
        this.setState({
            [target.name]: target.value,
            showError: false
        });
    }

    handleSubmit(event) {
        // prevent page from reloading
        event.preventDefault();
        LoginUser(this.state.username, this.state.password)
            .catch(error => {
                    this.setState({
                        showError: true,
                        errorMessage: error.message
                    });
            });
    }

    render() {
        return (
            <div className="background-container-login bg-image d-flex justify-content-center align-items-center">
                <div className="card col-sm-3 border-dark text-left">
                <form onSubmit={this.handleSubmit} className="card-body">
                    <h3 className="card-title">Log in</h3>
                    {(this.state.showError) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
                    <FormGroup className="mb-3" controlId="formBasicText">
                        <FormLabel>Username</FormLabel>
                        <FormControl type="text" placeholder="Enter username" name="username" onChange={this.handleInputChange}/>
                    </FormGroup>
                    <FormGroup className="mb-3" controlId="formBasicPassword">
                        <FormLabel>Password</FormLabel>
                        <FormControl type="password" placeholder="Password" name="password" onChange={this.handleInputChange}/>
                    </FormGroup>
                    <Button variant="secondary" type="submit">
                        Log in
                    </Button>
                </form>
                </div>
            </div>
        )
    }
}

export default Login;