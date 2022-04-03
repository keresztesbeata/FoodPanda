import React from 'react'
import {Form, Button, Alert, FormGroup, FormLabel, FormControl} from 'react-bootstrap'
import Header from "../components/Header";
import {RegisterUser} from "../components/UserSession";

class Register extends React.Component {
    constructor(props) {
        super(props);
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
        RegisterUser(this.state.username, this.state.password)
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
                        Register
                    </Button>
                </form>
            </>
        )
    }
}
export default Register;