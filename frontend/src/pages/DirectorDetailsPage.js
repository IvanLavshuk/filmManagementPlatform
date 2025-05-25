import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";

export default function DirectorDetailsPage() {
    const { id } = useParams();
    const [director, setDirector] = useState(null);
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        async function fetchDirectorData() {
            try {
                // Запрос данных режиссёра
                const directorRes = await axios.get(`http://localhost:8082/directors/${id}`);
                setDirector(directorRes.data);

                // Запрос фильмов режиссёра
                const moviesRes = await axios.get(`http://localhost:8082/movies/director/${id}`);
                setMovies(moviesRes.data);

                setLoading(false);
            } catch (err) {
                console.error("Ошибка при загрузке данных режиссёра:", err);
                setError("Ошибка при загрузке данных режиссёра");
                setLoading(false);
            }
        }
        fetchDirectorData();
    }, [id]);

    if (loading) return <p>Загрузка данных режиссёра...</p>;
    if (error) return <p>{error}</p>;
    if (!director) return <p>Режиссёр не найден</p>;

    return (
        <div style={{ display: "flex", gap: 32, margin: 32 }}>
            <div style={{ flex: 1 }}>
                <img
                    src={director.photoUrl}
                    alt={`${director.name} ${director.surname}`}
                    style={{ maxWidth: 250, borderRadius: 8, border: "1px solid #ccc" }}
                />
            </div>
            <div style={{ flex: 2 }}>
                <h2>{director.name} {director.surname}</h2>
                <p><strong>Дата рождения:</strong> {new Date(director.birthdate).toLocaleDateString()}</p>

                <h3>Фильмы</h3>
                {movies.length === 0 ? (
                    <p>Фильмы не найдены</p>
                ) : (
                    <ul>
                        {movies.map(movie => (
                            <li key={movie.id}>
                                <Link to={`/movies/${movie.id}`}>
                                    {movie.title}
                                </Link>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}
