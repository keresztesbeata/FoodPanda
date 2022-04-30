import {BASE_URL, FetchRequest, FetchRequestWithNoReturnData, GET_REQUEST, POST_REQUEST} from "./Utils";

export function LoadMenuForRestaurantByCategory(restaurant, category) {
    let url;
    let params;

    if(category === null || category === "All") {
        url = new URL(BASE_URL + "/restaurant/menu")
        params = {
            restaurant: restaurant
        };
    }else {
        url = new URL(BASE_URL + "/restaurant/menu/category")
        params = {
            restaurant: restaurant,
            category: category
        };
    }

    url.search = new URLSearchParams(params).toString();

    return FetchRequest(url, GET_REQUEST);
}

export function LoadFoodCategories() {
    const url = BASE_URL + "/food/categories";

    return FetchRequest(url, GET_REQUEST);
}

export function LoadFoodDetails(foodName) {
    const url = new URL(BASE_URL + "/food")
    const params = {
        food: foodName,
    }
    url.search = new URLSearchParams(params).toString();

    return FetchRequest(url, GET_REQUEST);
}

export function FindRestaurant(restaurantName) {
    const url = new URL(BASE_URL + "/restaurant")
    const params = {
        name: restaurantName
    };
    url.search = new URLSearchParams(params).toString();

    return FetchRequest(url, GET_REQUEST);
}

export function ExportMenu(restaurantName) {
    const url = new URL(BASE_URL + "/admin/restaurant/menu/export")
    const params = {
        restaurant: restaurantName
    };
    url.search = new URLSearchParams(params).toString();

    return FetchRequestWithNoReturnData(url, POST_REQUEST);
}