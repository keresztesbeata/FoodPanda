import {BASE_URL} from "./Utils";

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
        headers: {'Content-Type': 'application/json'}
    };

    return fetch(BASE_URL + "/category", requestOptions)
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
