import React from 'react'
import {FormControl, FormLabel, FormGroup, Button, Alert} from 'react-bootstrap'
import Header from "../components/Header";
import { LogoutUser} from "../components/UserSession";

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
                <Header />
                Logging out...
            </>
        )
    }
}

export default Logout;