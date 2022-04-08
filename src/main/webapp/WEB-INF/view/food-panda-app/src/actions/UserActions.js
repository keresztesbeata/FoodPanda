import {BASE_URL} from "./Utils";

const SESSION_KEY = 'userSession'

export function LoginUser(username, password) {
    const data = {
        username: username,
        password: password
    }
    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
    };

    return fetch(BASE_URL + '/perform_login', requestOptions)
        .then(response => {
            if (!response.ok) {
                return response.json()
                    .then(function (err) {
                        throw new Error(err.message);
                    });
            }
            return response.json();
        })
        .then(data => {
            console.log("Successfully logged in: " + data);
            const sessionData = JSON.stringify({
                isAdmin: data.userRole === "ADMIN",
                username: data.username
            });
            localStorage.setItem(SESSION_KEY, sessionData);
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
        })
        .then(data => {
            console.log("Successfully registered!");
        });
}

export function LogoutUser() {
    const url = new URL(BASE_URL + "/perform_logout")
    const params = {
        username: GetCurrentUser().username
    };
    url.search = new URLSearchParams(params).toString();

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'}
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
            localStorage.removeItem(SESSION_KEY)
            console.log("Successfully logged out!");
            window.location.href = "/"
        });

}

export function GetCurrentUser() {
    let userSessionData = localStorage.getItem(SESSION_KEY);
    if (userSessionData === null) {
        throw Error("No logged in user!")
    } else {
        return JSON.parse(userSessionData);
    }
}