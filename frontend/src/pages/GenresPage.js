import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./genres.css"
export default function GenresPage() {
    const [genres, setGenres] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get("http://localhost:8082/genres/all")
            .then(response => {
                setGenres(response.data);
                setLoading(false);
            })
            .catch(() => {
                setError("Ошибка при загрузке списка жанров");
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Загрузка жанров...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div className="genres-container">
            <div className="genres-back">
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>
            <h2 className="genres-title">Список жанров</h2>
            <ul className="genres-list">
                {genres.map(genre => (
                    <li key={genre.id}>
                        <Link to={`/genres/${genre.id}`}>
                            {genre.name}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}