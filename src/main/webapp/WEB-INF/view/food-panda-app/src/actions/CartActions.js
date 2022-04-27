import {BASE_URL, FetchRequest, FetchRequestWithNoReturnData, GET_REQUEST, POST_REQUEST} from "./Utils";

export function LoadCustomerCart() {
    const url = BASE_URL + "/customer/cart"

    return FetchRequest(url, GET_REQUEST);
}

export function AddFoodToCart(foodName, quantity) {
    const url = new URL(BASE_URL + "/customer/cart/add_food")
    const params = {
        foodName: foodName,
        quantity: quantity
    }
    url.search = new URLSearchParams(params).toString();

    return FetchRequestWithNoReturnData(url, POST_REQUEST);
}

export function RemoveFoodFromCart(foodName) {
    const url = new URL(BASE_URL + "/customer/cart/remove_food")
    const params = {
        foodName: foodName
    }
    url.search = new URLSearchParams(params).toString();

    return FetchRequestWithNoReturnData(url, POST_REQUEST);
}

