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
import ProtectedRoute from "./pages/ProtectedRoute";

export default function AppRouter() {
    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegistrationPage />} />
                <Route
                    path="/home"
                    element={
                        <ProtectedRoute>
                            <HomePage />
                        </ProtectedRoute>
                    }
                />
                <Route path="/actors" element={
                    <ProtectedRoute>
                        <ActorsPage />
                    </ProtectedRoute>
                } />
                <Route path="/actors/:id" element={
                    <ProtectedRoute>
                        <ActorDetailsPage />
                    </ProtectedRoute>
                } />
                <Route path="/directors" element={
                    <ProtectedRoute>
                        <DirectorsPage />
                    </ProtectedRoute>
                }  />
                <Route path="/directors/:id" element={
                    <ProtectedRoute>
                        <DirectorDetailsPage />
                    </ProtectedRoute>
                } />
                <Route path="/movies" element={
                    <ProtectedRoute>
                        <MoviesPage />
                    </ProtectedRoute>
                } />
                <Route path="/movies/:id" element={
                    <ProtectedRoute>
                        <MovieDetailsPage />
                    </ProtectedRoute>
                } />
                <Route path="/user" element={
                    <ProtectedRoute>
                        <UserPage />
                    </ProtectedRoute>
                } />
                <Route path="/genres" element={
                    <ProtectedRoute>
                        <GenresPage />
                    </ProtectedRoute>
                } />
                <Route path="/genres/:id" element={
                    <ProtectedRoute>
                        <GenreDetailsPage />
                    </ProtectedRoute>
                } />

                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </Router>
    );
}
