import logout from "../pages/Logout";

export const BASE_URL = 'http://localhost:8080/foodpanda';
const SESSION_KEY = 'userSession'

export function LoginUser(username, password) {
    const data = {
        username: username,
        password: password
    }
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };

    return fetch(BASE_URL + '/perform_login',requestOptions)
        .then(response => {
            if(!response.ok) {
                return response.json()
                    .then(function(err) {
                        throw new Error(err.message);
                    });
            }else{
                return response.json();
            }
        })
        .then(data => {
            console.log("Successfully logged in: " + data);
            const sessionData = JSON.stringify({
                isAdmin: data.userRole === "ADMIN",
                username: data.username
            });
            localStorage.setItem(SESSION_KEY, sessionData);
            window.location.href = "/"
        });
}

export function RegisterUser(username, password) {
    const data = {
        username: username,
        password: password
    }
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };

    return fetch(BASE_URL + '/perform_register',requestOptions)
        .then(response => {
            if(!response.created) {
                return response.json()
                    .then(function(err) {
                        throw new Error(err.message);
                    });
            }else{
                return response.json();
            }
        })
        .then(() => {
            console.log("Successfully registered! ");
            window.location.href = "/login"
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
        headers: { 'Content-Type': 'application/json' }
    };

    fetch(url,requestOptions)
        .then(response => {
            console.log(response)
            if(response.ok === false) {
                throw Error(JSON.stringify(response))
            }else{
                return response;
            }
        })
        .then(() => {
            localStorage.removeItem(SESSION_KEY)
            console.log("Successfully logged out!");
            window.location.href = "/"
        })
        .catch(error => {
            console.log("Failed to log out user!" + error.body);
        });
}

export function GetCurrentUser() {
    let userSessionData = localStorage.getItem(SESSION_KEY);
    if(userSessionData === null) {
       throw Error("No logged in user!")
    }else{
        return JSON.parse(userSessionData);
    }
}