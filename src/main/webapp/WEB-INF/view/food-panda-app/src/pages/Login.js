import React from 'react'
import {FormControl, FormLabel, FormGroup, Button, Alert} from 'react-bootstrap'
import Header from "../components/Header";
import {LoginUser} from "../components/UserSession";

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
                    console.log(error.message)
                    this.setState({
                        showError: true,
                        errorMessage: error.message
                    });
            });
    }

    render() {
        return (
            <>
                <Header />
            <form onSubmit={this.handleSubmit}>
                {(this.state.showError) ? <Alert className="alert-danger">{this.state.errorMessage}</Alert> : <div/>}
                <FormGroup className="mb-3" controlId="formBasicText">
                    <FormLabel>Username</FormLabel>
                    <FormControl type="text" placeholder="Enter username" name="username" onChange={this.handleInputChange}/>
                </FormGroup>
                <FormGroup className="mb-3" controlId="formBasicPassword">
                    <FormLabel>Password</FormLabel>
                    <FormControl type="password" placeholder="Password" name="password" onChange={this.handleInputChange}/>
                </FormGroup>
                <Button variant="primary" type="submit">
                    Log in
                </Button>
            </form>
            </>
        )
    }
}

export default Login;