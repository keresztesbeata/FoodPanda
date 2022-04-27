import {BASE_URL, FetchRequest, GET_REQUEST, POST_REQUEST} from "./Utils";

export function LoadDeliveryZones() {
    const url = BASE_URL + "/delivery_zone/all";

    return FetchRequest(url, GET_REQUEST);
}

export function LoadAdminsRestaurant(admin) {
    const url = new URL(BASE_URL + "/admin/restaurant");
    const params = {
        admin: admin,
    };
    url.search = new URLSearchParams(params).toString();

    return FetchRequest(url, GET_REQUEST);
}

export function AddRestaurant(restaurant) {
    const url = BASE_URL + "/admin/restaurant/new";

    return FetchRequest(url, POST_REQUEST, restaurant)
        .then(response => {
            window.location.href = "/admin/restaurant"
        });
}

export function AddFood(food) {
    const url = BASE_URL + "/admin/restaurant/food/new";

    return FetchRequest(url, POST_REQUEST, food);
}


