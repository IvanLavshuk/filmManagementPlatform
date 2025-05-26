import React, { useEffect, useState } from "react";
import axios from "axios";
import {useNavigate, useParams, Link } from "react-router-dom";
import "./genre.css"
export default function GenreDetailsPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [genre, setGenre] = useState(null);
    const [movies, setMovies] = useState([]);
    const [loadingGenre, setLoadingGenre] = useState(true);
    const [loadingMovies, setLoadingMovies] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get(`http://localhost:8082/genres/${id}`)
            .then(res => {
                setGenre(res.data);
                setLoadingGenre(false);
            })
            .catch(err => {
                console.error("Ошибка при загрузке жанра:", err);
                setError("Ошибка при загрузке жанра");
                setLoadingGenre(false);
            });
    }, [id]);

    useEffect(() => {
        if (!genre) return;

        axios.get(`http://localhost:8082/movies/genre`, {
            params: { genre: genre.name }
        })
            .then(res => {
                setMovies(res.data);
                setLoadingMovies(false);
            })
            .catch(err => {
                console.error("Ошибка при загрузке фильмов:", err);
                setError("Ошибка при загрузке фильмов");
                setLoadingMovies(false);
            });
    }, [genre]);

    if (loadingGenre || loadingMovies) return <p>Загрузка...</p>;
    if (error) return <p>{error}</p>;
    if (!genre) return <p>Жанр не найден</p>;

    return (
        <div className="genre-details-container">
            <div className="genre-navigation">
                <button onClick={() => navigate(-1)}>Назад</button>
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>
            <h2 className="genre-title">Жанр: {genre.name}</h2>

            {movies.length === 0 ? (
                <p className="genre-no-movies">Фильмы не найдены</p>
            ) : (
                <ul className="genre-movies-list">
                    {movies.map(movie => (
                        <li key={movie.id}>
                            <Link to={`/movies/${movie.id}`}>
                                {movie.title} ({new Date(movie.releaseDate).getFullYear()})
                            </Link>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}
