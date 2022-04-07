import {BASE_URL} from "./Utils";

export function AddFoodToCart(username, foodName, quantity) {
    const url = new URL(BASE_URL + "/customer/cart/add_food")
    const params = {
        username: username,
        foodName: foodName,
        quantity: quantity
    }
    url.search = new URLSearchParams(params).toString();

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(params)
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

export function RemoveFoodFromCart(username, foodName) {
    const url = new URL(BASE_URL + "/customer/cart/remove_food")
    const params = {
        username: username,
        foodName: foodName
    }
    url.search = new URLSearchParams(params).toString();

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(params)
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

export function PlaceOrder(orderDetails) {
    const url = BASE_URL + "/customer/order/new";

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(orderDetails)
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

export function LoadCustomerCart(username) {
    const url = new URL(BASE_URL + "/customer/cart")
    const params = {
        username: username,
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


export function LoadOrdersOfCustomer(customer) {
    const url = new URL(BASE_URL + "/customer/orders")
    const params = {
        customer: customer,
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
