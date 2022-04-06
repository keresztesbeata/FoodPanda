import {BASE_URL} from "./Utils";

export function LoadDeliveryZones() {
    const url = BASE_URL + "/delivery_zone/all";

    const requestOptions = {
        method: 'GET',
        headers: {'Content-Type': 'application/json'}
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

export function AddRestaurant(restaurant) {
    const url = BASE_URL + "/admin/restaurant/new";

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(restaurant)
    };

    return fetch(url, requestOptions)
        .then(response => {
            if (!response.ok) {
                response.json()
                    .then(function (err) {
                        throw new Error(err.message);
                    });
            }
        });
}