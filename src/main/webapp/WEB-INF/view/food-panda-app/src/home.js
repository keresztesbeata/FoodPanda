import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import MenuView from './pages/MenuView';
import reportWebVitals from './reportWebVitals';

ReactDOM.render(
    <React.StrictMode>
        <MenuView />
    </React.StrictMode>,
    document.getElementById('root')
);

reportWebVitals();