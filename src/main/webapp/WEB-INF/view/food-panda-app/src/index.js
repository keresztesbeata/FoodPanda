import React from 'react';
import './index.css';
import reportWebVitals from './reportWebVitals';
import * as ReactDOMClient from 'react-dom/client';
import App from "./App";

const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);

root.render(
    <App/>
);

reportWebVitals();
