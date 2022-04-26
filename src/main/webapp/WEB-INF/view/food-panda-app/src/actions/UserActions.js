import {BASE_URL} from "./Utils";

const SESSION_TOKEN = 'sessionToken'

function getSessionToken() {
    let sessionToken = JSON.parse(localStorage.getItem(SESSION_TOKEN));
    if (sessionToken === null) {
        throw Error("You are not logged in!")
    }
    return sessionToken.tokenType + " " + sessionToken.accessToken;
}

export function LoginUser(username, password) {

    const data = {
        username: username,
        password: password
    };

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    };

    return fetch(BASE_URL + "/perform_login", requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(function (err) {
                        throw new Error(err.message);
                    });
            }
            return response.json()
        })
        .then(data => {
            const sessionToken = JSON.stringify({
                accessToken: data.accessToken,
                tokenType: data.tokenType
            });
            localStorage.setItem(SESSION_TOKEN, sessionToken);
        });
}

export function RegisterUser(username, password, asAdmin) {
    const url = new URL(BASE_URL + "/perform_register")
    const params = {
        asAdmin: asAdmin
    };
    url.search = new URLSearchParams(params).toString();

    const data = {
        username: username,
        password: password
    }

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    };

    return fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(function (err) {
                        throw new Error(err.message);
                    });
            }
            return response.json();
        });
}

export function LogoutUser() {
    const url = BASE_URL + "/perform_logout"

    const requestOptions = {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': getSessionToken(),
        }
    };

    return fetch(url, requestOptions)
        .then(response => {
            console.log(response)
            if (!response.ok) {
                return response.json()
                    .then(function (err) {
                        throw new Error(err.message);
                    });
            }
        })
        .then(() => {
            localStorage.removeItem(SESSION_TOKEN)
        });
}

export function GetCurrentUser() {
    const requestOptions = {
        method: 'GET',
        headers: {
            'Authorization': getSessionToken(),
        }
    };

    return fetch(BASE_URL + "/current_user", requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(function (err) {
                        throw new Error(err.message);
                    });
            }
            return response.json();
        })
        .then(currentUserData => {
                return {
                    username: currentUserData.username,
                    isAdmin: currentUserData.userRole === "ADMIN"
                }
            }
        );
}