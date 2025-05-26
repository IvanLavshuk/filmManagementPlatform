import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./directors.css"
export default function DirectorsPage() {
    const [directors, setDirectors] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthdateBefore, setBirthdateBefore] = useState("");
    const [country, setCountry] = useState("");

    const fetchByName = useCallback(async (value) => {
        if (!value) return [];
        const res = await axios.get("http://localhost:8082/directors/name", { params: { name: value } });
        return res.data;
    }, []);

    const fetchBySurname = useCallback(async (value) => {
        if (!value) return [];
        const res = await axios.get("http://localhost:8082/directors/surname", { params: { surname: value } });
        return res.data;
    }, []);

    const fetchByBirthdateBefore = useCallback(async (value) => {
        if (!value) return [];
        const res = await axios.get("http://localhost:8082/directors/birthdate-before", { params: { date: value } });
        return res.data;
    }, []);

    const fetchByCountry = useCallback(async (value) => {
        if (!value) return [];
        const res = await axios.get("http://localhost:8082/directors/country", { params: { country: value } });
        return res.data;
    }, []);

    useEffect(() => {
        async function fetchDirectors() {
            setLoading(true);
            setError(null);

            try {
                const results = await Promise.all([
                    fetchByName(name.trim()),
                    fetchBySurname(surname.trim()),
                    fetchByBirthdateBefore(birthdateBefore),
                    fetchByCountry(country.trim()),
                ]);

                const combined = results.flat();
                const uniqueDirectors = Array.from(new Map(combined.map(d => [d.id, d])).values());

                setDirectors(uniqueDirectors);
            } catch (e) {
                setError("Ошибка при поиске режиссёров");
            } finally {
                setLoading(false);
            }
        }

        if (name || surname || birthdateBefore || country) {
            fetchDirectors();
        } else {
            setDirectors([]);
        }
    }, [name, surname, birthdateBefore, country, fetchByName, fetchBySurname, fetchByBirthdateBefore, fetchByCountry]);

    return (
        <div className="directors-container">
            <div className="directors-back">
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>
            <h2 className="directors-title">Поиск режиссёров</h2>

            <div className="directors-filters">
                <input
                    type="text"
                    placeholder="Имя"
                    value={name}
                    onChange={e => setName(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Фамилия"
                    value={surname}
                    onChange={e => setSurname(e.target.value)}
                />
                <input
                    type="date"
                    placeholder="Родился до"
                    value={birthdateBefore}
                    onChange={e => setBirthdateBefore(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Страна фильма"
                    value={country}
                    onChange={e => setCountry(e.target.value)}
                />
            </div>

            {loading && <p className="loading-text">Загрузка...</p>}
            {error && <p className="error-text">{error}</p>}

            <ul className="directors-list">
                {directors.length > 0 ? (
                    directors.map(director => (
                        <li key={director.id}>
                            <Link to={`/directors/${director.id}`}>
                                {director.name} {director.surname}
                            </Link>
                        </li>
                    ))
                ) : (
                    !loading && <p>Режиссёры не найдены.</p>
                )}
            </ul>
        </div>
    );
}
