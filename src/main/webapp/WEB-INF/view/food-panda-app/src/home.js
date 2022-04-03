import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import Menu from './pages/Menu';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
    <React.StrictMode>
        <Menu />
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();