import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
export default function MoviesPage() {
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get("http://localhost:8082/movies/all")
            .then(response => {
                setMovies(response.data);
                setLoading(false);
            })
            .catch(() => {
                setError("Ошибка при загрузке фильмов");
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Загрузка фильмов...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h2>Список фильмов</h2>
            <ul>
                {movies.map(movie => (
                    <li key={movie.id}>
                        <Link to={`/movies/${movie.id}`}>
                            {movie.title /* или movie.name, зависит от твоего DTO */}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}