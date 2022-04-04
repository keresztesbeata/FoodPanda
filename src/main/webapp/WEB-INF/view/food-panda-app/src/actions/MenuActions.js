import {BASE_URL} from "./Utils";

export function LoadMenuForRestaurant(restaurantName) {
    const url = new URL(BASE_URL + "/restaurant/menu")
    const params = {
        name: restaurantName
    };
    url.search = new URLSearchParams(params).toString();

    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    };

    return fetch(url,requestOptions)
        .then(response => {
            if(!response.ok) {
                return response.json()
                    .then(function(err) {
                        throw new Error(err.message);
                    });
            }
            return response.json();
        });
}


export function FindRestaurant(restaurantName) {
    const url = new URL(BASE_URL + "/restaurant")
    const params = {
        name: restaurantName
    };
    url.search = new URLSearchParams(params).toString();

    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    };

    return fetch(url,requestOptions)
        .then(response => {
            if(!response.ok) {
                return response.json()
                    .then(function(err) {
                        throw new Error(err.message);
                    });
            }
            return response.json();
        });
}