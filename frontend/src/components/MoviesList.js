// src/components/MoviesList.jsx
import React from "react";
import { Link } from "react-router-dom";
import "./moviesList.css";

export default function MoviesList({ movies }) {
    if (movies.length === 0) {
        return <p>Фильмы не найдены.</p>;
    }

    return (
        <ul className="movies-list">
            {movies.map(movie => (
                <li key={movie.id}>
                    <Link to={`/movies/${movie.id}`}>
                        {movie.title} ({movie.releaseDate ? new Date(movie.releaseDate).getFullYear() : "Дата неизвестна"})
                    </Link>
                </li>
            ))}
        </ul>
    );
}

