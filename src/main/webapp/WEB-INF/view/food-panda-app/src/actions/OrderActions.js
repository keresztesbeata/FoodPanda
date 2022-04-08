import {BASE_URL} from "./Utils";

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
    if (orderStatus === null || orderStatus === "All") {
        return LoadOrdersOfRestaurant(restaurant);
    }
    const url = new URL(BASE_URL + "/admin/restaurant/orders/status")
    const params = {
        restaurant: restaurant, orderStatus: orderStatus
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

export function LoadOrdersStatuses() {
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

export function LoadOrderByOrderNumber(orderNumber) {
    const url = new URL(BASE_URL + "/admin/restaurant/orders/orderNumber")
    const params = {
        orderNumber: orderNumber
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

export function UpdateOrderStatus(orderNumber, orderStatus) {
    const url = new URL(BASE_URL + "/admin/restaurant/order/update_status");
    const params = {
        orderNumber: orderNumber,
        orderStatus: orderStatus
    }
    url.search = new URLSearchParams(params).toString();

    const requestOptions = {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
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