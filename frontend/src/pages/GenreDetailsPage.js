import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";

export default function GenreDetailsPage() {
    const { id } = useParams();
    const [genre, setGenre] = useState(null);
    const [movies, setMovies] = useState([]);
    const [loadingGenre, setLoadingGenre] = useState(true);
    const [loadingMovies, setLoadingMovies] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        // Получаем данные жанра (название)
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

        // Получаем фильмы по названию жанра из жанра
        // Жанр уже может быть не загружен, поэтому используем async функцию
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
        <div style={{ margin: 32 }}>
            <h2>Жанр: {genre.name}</h2>

            {movies.length === 0 ? (
                <p>Фильмы не найдены</p>
            ) : (
                <ul>
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
