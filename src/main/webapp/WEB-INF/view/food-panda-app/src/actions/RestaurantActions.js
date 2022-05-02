import {BASE_URL, FetchRequest, FetchRequestWithNoReturnData, GET_REQUEST, POST_REQUEST} from "./Utils";

export function LoadDeliveryZones() {
    const url = BASE_URL + "/delivery_zone/all";

    return FetchRequest(url, GET_REQUEST);
}

export function LoadAdminsRestaurant() {
    const url = BASE_URL + "/admin/restaurant";

    return FetchRequest(url, GET_REQUEST);
}

export function AddRestaurant(restaurant) {
    const url = BASE_URL + "/admin/restaurant/new";

    return FetchRequestWithNoReturnData(url, POST_REQUEST, restaurant)
        .then(response => {
            window.location.href = "/admin/restaurant"
        });
}

export function AddFood(food) {
    const url = BASE_URL + "/admin/restaurant/food/new";

    return FetchRequestWithNoReturnData(url, POST_REQUEST, food);
}


