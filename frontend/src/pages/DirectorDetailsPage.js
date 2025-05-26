import React, { useEffect, useState } from "react";
import axios from "axios";
import {useParams, useNavigate, Link } from "react-router-dom";
import "./director.css"
export default function DirectorDetailsPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [director, setDirector] = useState(null);
    const [movies, setMovies] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        async function fetchDirectorData() {
            try {
                const directorRes = await axios.get(`http://localhost:8082/directors/${id}`);
                setDirector(directorRes.data);

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
        <div className="director-details-container">
            <div className="director-navigation">
                <button onClick={() => navigate(-1)}>Назад</button>
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>
            <div className="director-content">
                <div className="director-photo">
                    <img
                        src={director.photoUrl}
                        alt={`${director.name} ${director.surname}`}
                    />
                </div>
                <div className="director-info">
                    <h2>{director.name} {director.surname}</h2>
                    <p><strong>Дата рождения:</strong> {new Date(director.birthdate).toLocaleDateString()}</p>

                    <h3>Фильмы</h3>
                    {movies.length === 0 ? (
                        <p>Фильмы не найдены</p>
                    ) : (
                        <ul className="director-movies-list">
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
        </div>
    );
}

