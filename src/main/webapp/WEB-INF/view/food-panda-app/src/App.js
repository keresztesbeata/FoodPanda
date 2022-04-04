import React, {Component} from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import './App.css';
import Login from "./pages/Login";
import Home from "./pages/Home";
import Register from "./pages/Register";
import Logout from "./pages/Logout";
import Header from "./components/Header";
import MenuView from "./pages/MenuView";

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
                    <Route path="/" element={<Home />} />
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register/>} />
                    <Route path="/logout" element={<Logout />} />
                    <Route path="/customer/menu" element={<MenuView isAdmin={false}/>} />
                    <Route path="/admin/menu" element={<MenuView isAdmin={true}/>} />
                </Routes>
            </BrowserRouter>
            </>
        );
    }
}

export default App;