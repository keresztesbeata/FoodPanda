import {BASE_URL} from "./Utils";

export function LoadMenuForRestaurant(restaurant) {
    const url = new URL(BASE_URL + "/restaurant/menu")
    const params = {
        restaurant: restaurant
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

export function LoadMenuForRestaurantByCategory(restaurant, category) {
    if(category === null || category === "") {
        return LoadMenuForRestaurant(restaurant)
    }

    const url = new URL(BASE_URL + "/restaurant/menu/category")
    const params = {
        restaurant: restaurant,
        category: category
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

export function LoadFoodCategories() {
    const requestOptions = {
        method: 'GET',
        headers: { 'Content-Type': 'application/json' }
    };

    return fetch(BASE_URL + "/category",requestOptions)
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
