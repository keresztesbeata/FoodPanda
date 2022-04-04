import {BASE_URL} from "./Utils";

export function AddFoodToCart(username, foodName, quantity) {
    const data = {
        username: username,
        foodName: foodName,
        quantity: quantity
    }
    const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(data)
    };

    return fetch(BASE_URL + '/customer/cart/add_food',requestOptions)
        .then(response => {
            if(!response.ok) {
                response.json()
                    .then(function(err) {
                        throw new Error(err.message);
                    });
            }
        })
        .then(data => {
            console.log("Successfully registered: " + data);
            window.location.href = "/login"
        });
}