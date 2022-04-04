import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import CustomerMenuView from './pages/CustomerMenuView';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
    <React.StrictMode>
        <CustomerMenuView />
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();