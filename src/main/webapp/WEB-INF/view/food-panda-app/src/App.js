import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Route, Routes} from 'react-router-dom'
import './App.css';
import Login from "./pages/Login";
import Home from "./pages/Home";
import Register from "./pages/Register";
import Logout from "./pages/Logout";
import Header from "./components/Header";
import CustomerMenuView from "./pages/CustomerMenuView";
import AdminMenuView from "./pages/AdminMenuView";
import CartContent from "./components/CartContent";
import AddRestaurantView from "./pages/AddRestaurantView";
import MyRestaurant from "./pages/MyRestaurant";
import AddFoodView from "./pages/AddFoodView";
import CustomerOrderHistory from "./pages/CustomerOrderHistory";
import AdminRestaurantOrders from "./pages/AdminRestaurantOrders";

class App extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <Header/>
                <BrowserRouter>
                    <Routes>
                        <Route path="/" element={<Home/>}/>
                        <Route path="/login" element={<Login/>}/>
                        <Route path="/register" element={<Register/>}/>
                        <Route path="/logout" element={<Logout/>}/>
                        <Route path="/customer/menu" element={<CustomerMenuView/>}/>
                        <Route path="/customer/cart" element={<CartContent/>}/>
                        <Route path="/customer/orders_history" element={<CustomerOrderHistory/>}/>
                        <Route path="/admin/restaurant" element={<MyRestaurant/>}/>
                        <Route path="/admin/restaurant/view_menu" element={<AdminMenuView/>}/>
                        <Route path="/admin/restaurant/add_food" element={<AddFoodView/>}/>
                        <Route path="/admin/restaurant/new" element={<AddRestaurantView/>}/>
                        <Route path="/admin/restaurant/orders" element={<AdminRestaurantOrders/>}/>
                    </Routes>
                </BrowserRouter>
            </>
        );
    }
}

export default App;