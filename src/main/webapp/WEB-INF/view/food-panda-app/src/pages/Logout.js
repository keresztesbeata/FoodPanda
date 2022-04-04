import React from 'react'
import {FormControl, FormLabel, FormGroup, Button, Alert} from 'react-bootstrap'
import Header from "../components/Header";
import { LogoutUser} from "../actions/UserActions";

class Logout extends React.Component {
    constructor(props, context) {
        super(props, context);
    }

    componentDidMount() {
        LogoutUser();
    }

    render() {
        return (
            <>
                Logging out...
            </>
        )
    }
}

export default Logout;