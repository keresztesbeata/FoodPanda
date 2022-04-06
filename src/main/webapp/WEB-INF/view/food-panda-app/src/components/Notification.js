import {Toast, ToastContainer} from "react-bootstrap";
import React from 'react'

export default function Notification(title, variant, message) {
    return (
        <ToastContainer className="p-3" position="top-center">
            <Toast>
                <Toast.Header closeButton="true">
                    <img src="holder.js/20x20?text=%20" className="rounded me-2" alt=""/>
                    <strong className="me-auto">{title}</strong>
                </Toast.Header>
                <Toast.Body variant={variant}>{message}</Toast.Body>
            </Toast>
        </ToastContainer>
    )
}