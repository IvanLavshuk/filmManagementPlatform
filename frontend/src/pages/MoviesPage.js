// src/pages/MoviesPage.jsx
import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import MoviesFilter from "../components/MoviesFilter";
import MoviesList from "../components/MoviesList";
import "./movies.css"
export default function MoviesPage() {
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const [searchTerm, setSearchTerm] = useState("");
    const [country, setCountry] = useState("");
    const [directorName, setDirectorName] = useState("");
    const [directorSurname, setDirectorSurname] = useState("");
    const [sortOrder, setSortOrder] = useState("");

    const fetchByTitle = useCallback(async (title) => {
        if (!title) return [];
        const res = await axios.get("http://localhost:8082/movies/title", { params: { title } });
        return res.data;
    }, []);

    const fetchByCountry = useCallback(async (country) => {
        if (!country) return [];
        const res = await axios.get("http://localhost:8082/movies/country", { params: { country } });
        return res.data;
    }, []);

    const fetchByDirector = useCallback(async (name, surname) => {
        if (!name || !surname) return [];
        const res = await axios.get("http://localhost:8082/movies/director", { params: { name, surname } });
        return res.data;
    }, []);

    const fetchSortedMovies = useCallback(async (order) => {
        switch (order) {
            case "date-asc":
                return (await axios.get("http://localhost:8082/movies/order-date-asc")).data;
            case "date-desc":
                return (await axios.get("http://localhost:8082/movies/order-date-desc")).data;
            case "rating-desc":
                return (await axios.get("http://localhost:8082/movies/order-rating-desc")).data;
            default:
                return [];
        }
    }, []);

    useEffect(() => {
        async function fetchMovies() {
            setLoading(true);
            setError(null);

            try {
                const promises = [];

                if (searchTerm.trim()) promises.push(fetchByTitle(searchTerm.trim()));
                if (country.trim()) promises.push(fetchByCountry(country.trim()));
                if (directorName.trim() && directorSurname.trim()) promises.push(fetchByDirector(directorName.trim(), directorSurname.trim()));
                if (sortOrder) promises.push(fetchSortedMovies(sortOrder));

                if (promises.length === 0) {
                    setMovies([]);
                    setLoading(false);
                    return;
                }

                const results = await Promise.all(promises);
                const combined = results.flat();
                const uniqueMovies = Array.from(new Map(combined.map(m => [m.id, m])).values());

                setMovies(uniqueMovies);
            } catch (e) {
                setError("Ошибка при загрузке фильмов");
            } finally {
                setLoading(false);
            }
        }

        fetchMovies();
    }, [searchTerm, country, directorName, directorSurname, sortOrder, fetchByTitle, fetchByCountry, fetchByDirector, fetchSortedMovies]);

    return (
        <div className="movies-container">
            <div className="movies-back">
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>

            <h2 className="movies-title">Поиск и сортировка фильмов</h2>

            <MoviesFilter
                searchTerm={searchTerm}
                setSearchTerm={setSearchTerm}
                country={country}
                setCountry={setCountry}
                directorName={directorName}
                setDirectorName={setDirectorName}
                directorSurname={directorSurname}
                setDirectorSurname={setDirectorSurname}
                sortOrder={sortOrder}
                setSortOrder={setSortOrder}
            />

            {loading && <p className="loading-text">Загрузка...</p>}
            {error && <p className="error-text">{error}</p>}

            <MoviesList movies={movies} />
        </div>
    );
}
