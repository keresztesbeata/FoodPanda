import React from 'react'
import {GetCurrentUser} from "../actions/UserActions";

class Home extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        try {
            const userSession = GetCurrentUser();
            return (
                <div className="background-container-home bg-image d-flex justify-content-center align-items-center">
                    <div className="transparent-background">
                    <h1 className="text-white">
                        Welcome {userSession.username} !
                    </h1>
                    </div>
                </div>
            )
        } catch (e) {
            return (
                <div className="background-container-home bg-image d-flex justify-content-center align-items-center">
                    <div className="transparent-background">
                        <h1 className="text-white">
                            Welcome stranger !
                        </h1>
                    </div>
                </div>
            )
        }
    };
}

export default Home;