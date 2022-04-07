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

export function LoadAdminsRestaurant(admin) {
    const url = new URL(BASE_URL + "/admin/restaurant");
    const params = {
        admin: admin,
    };
    url.search = new URLSearchParams(params).toString();

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

export function AddFood(food) {
    const url = BASE_URL + "/admin/restaurant/food/new";

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(food)
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

export function LoadOrdersOfRestaurant(restaurant) {
    const url = new URL(BASE_URL + "/admin/restaurant/orders")
    const params = {
        restaurant: restaurant,
    }
    url.search = new URLSearchParams(params).toString();

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

export function LoadOrdersOfRestaurantByStatus(restaurant, orderStatus) {
    if(orderStatus === null || orderStatus === "All") {
        return LoadOrdersOfRestaurant(restaurant);
    }
    const url = new URL(BASE_URL + "/admin/restaurant/orders/status")
    const params = {
        restaurant: restaurant,
        orderStatus: orderStatus
    }
    url.search = new URLSearchParams(params).toString();

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

export function LoadOrdersStates() {
    const url = new URL(BASE_URL + "/order_statuses")

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

