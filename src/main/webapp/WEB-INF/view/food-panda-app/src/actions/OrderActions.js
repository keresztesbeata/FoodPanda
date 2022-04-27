import {
    BASE_URL,
    FetchRequest,
    FetchRequestWithNoReturnData,
    GET_REQUEST,
    POST_REQUEST
} from "./Utils";

export function LoadOrdersOfRestaurant(restaurant) {
    const url = new URL(BASE_URL + "/admin/restaurant/orders")
    const params = {
        restaurant: restaurant,
    }
    url.search = new URLSearchParams(params).toString();

    return FetchRequest(url, GET_REQUEST);
}

export function LoadOrdersOfRestaurantByStatus(restaurant, orderStatus) {
    if (orderStatus === null || orderStatus === "All") {
        return LoadOrdersOfRestaurant(restaurant);
    }
    const url = new URL(BASE_URL + "/admin/restaurant/orders/status")
    const params = {
        restaurant: restaurant,
        orderStatus: orderStatus
    }
    url.search = new URLSearchParams(params).toString();

    return FetchRequest(url, GET_REQUEST);
}

export function LoadOrdersOfCustomer() {
    const url = BASE_URL + "/customer/orders";

    return FetchRequest(url, GET_REQUEST);
}

export function LoadOrdersStatuses() {
    const url = new URL(BASE_URL + "/order_statuses")

    return FetchRequest(url, GET_REQUEST);
}

export function LoadOrderByOrderNumber(orderNumber) {
    const url = new URL(BASE_URL + "/admin/restaurant/orders/orderNumber")
    const params = {
        orderNumber: orderNumber
    }
    url.search = new URLSearchParams(params).toString();

    return FetchRequest(url, GET_REQUEST);
}

export function UpdateOrderStatus(orderNumber, orderStatus) {
    const url = new URL(BASE_URL + "/admin/restaurant/order/update_status");
    const params = {
        orderNumber: orderNumber,
        orderStatus: orderStatus
    }
    url.search = new URLSearchParams(params).toString();

    return FetchRequestWithNoReturnData(url, POST_REQUEST);
}

export function PlaceOrder(orderDetails) {
    const url = BASE_URL + "/customer/order/new";

    return FetchRequestWithNoReturnData(url, POST_REQUEST, orderDetails);
}