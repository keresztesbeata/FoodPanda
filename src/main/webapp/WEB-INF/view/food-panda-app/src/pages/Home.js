import React from 'react'
import {GetCurrentUser} from "../actions/UserActions";

class Home extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            currentUser: null,
        }
    }

    componentDidMount() {
        GetCurrentUser()
            .then(currentUserData => {
                this.setState({
                    currentUser: currentUserData
                })
            })
            .catch(e => console.log(e));
    }

    render() {
        return (
            <div
                className="background-container-home bg-image d-flex justify-content-center align-items-center">
                <div className="transparent-background">
                    {(this.state.currentUser !== null) ?
                        <h1 className="text-white">Welcome {this.state.currentUser.username}!</h1>
                        :
                        <h1 className="text-white">Welcome stranger!</h1>
                    }
                </div>
            </div>
        )
    }
}
export default Home;