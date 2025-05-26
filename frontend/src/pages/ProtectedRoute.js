import React from "react";
import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ children }) {
    let currentUser = null;
    try {
        currentUser = JSON.parse(localStorage.getItem("currentUser"));
    } catch {
        currentUser = null;
    }

    if (!currentUser) {
        return <Navigate to="/login" replace />;
    }
    return children;
}

