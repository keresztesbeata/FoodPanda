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
                        <Route path="/admin/menu" element={<AdminMenuView/>}/>
                    </Routes>
                </BrowserRouter>
            </>
        );
    }
}

export default App;