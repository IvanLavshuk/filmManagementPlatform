import React from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegistrationPage from "./pages/RegistrationPage";
import HomePage from "./pages/HomePage";
import ActorsPage from "./pages/ActorsPage";
import DirectorsPage from "./pages/DirectorsPage";
import MoviesPage from "./pages/MoviesPage";
import UserPage from "./pages/UserPage";
import GenresPage from "./pages/GenresPage";
import MovieDetailsPage from "./pages/MovieDetailsPage";
import ActorDetailsPage from "./pages/ActorDetailsPage";
import DirectorDetailsPage from "./pages/DirectorDetailsPage"
import GenreDetailsPage from "./pages/GenreDetailsPage";
// Пример проверки авторизации (можно усложнить)
const isAuthenticated = () => !!localStorage.getItem("token");

// Компонент для защищённых маршрутов
function PrivateRoute({ children }) {
    return isAuthenticated() ? children : <Navigate to="/login" />;
}

export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegistrationPage />} />
                <Route path="/home" element={<HomePage />} />
                <Route path="/actors" element={<ActorsPage />} />
                <Route path="/actors/:id" element={<ActorDetailsPage />} />
                <Route path="/directors" element={<DirectorsPage />} />
                <Route path="/directors/:id" element={<DirectorDetailsPage />} />
                <Route path="/movies" element={<MoviesPage />} />
                <Route path="/movies/:id" element={<MovieDetailsPage />} />
                <Route path="/user" element={<UserPage />} />
                <Route path="/genres" element={<GenresPage />} />
                <Route path="/genres/:id" element={<GenreDetailsPage />} />
                {/* Редирект на /login по умолчанию */}
                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </Router>
    );
}
