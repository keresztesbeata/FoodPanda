import React, {useState} from 'react';
import './index.css';
import reportWebVitals from './reportWebVitals';
import * as ReactDOMClient from 'react-dom/client';
import App from "./App";

const container = document.getElementById('root');
const root = ReactDOMClient.createRoot(container);

root.render(
    <App />
);

reportWebVitals();


/**TODO:
 * NOT WORKING:
 *  - redirect to another page inside component
 *  - get error message from server when exception is thrown (status code is sent but msg none)
 */