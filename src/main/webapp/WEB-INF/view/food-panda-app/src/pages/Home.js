import React from 'react'
import {Form, Button} from 'react-bootstrap'
import Header from "../components/Header";
import {GetCurrentUser} from "../components/UserSession";

class Home extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        try {
            const userSession = GetCurrentUser();
            console.log("Home of : " + userSession.username);
            return (
                <div className="background-container-home bg-image d-flex justify-content-center align-items-center">
                    <h1 className="text-white">
                        Welcome {userSession.username} !
                    </h1>
                </div>
            )
        }catch(e) {
            return (
                <div className="background-container-home bg-image d-flex justify-content-center align-items-center">
                    <h1 className="text-white">
                        Welcome stranger !
                    </h1>
                </div>
            )
        }
    };
}

export default Home;