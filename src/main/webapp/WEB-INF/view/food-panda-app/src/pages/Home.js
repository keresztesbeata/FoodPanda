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
                <>
                    <Header />
                    <h1>
                        Welcome {userSession.username} !
                    </h1>
                </>
            )
        }catch(e) {
            return (
                <>
                    <Header />
                    <h1>
                        Welcome stranger !
                    </h1>
                </>
            )
        }
    };
}

export default Home;