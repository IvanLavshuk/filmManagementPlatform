import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import "./actors.css"
export default function ActorsPage() {
    const [actors, setActors] = useState([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [birthdateBefore, setBirthdateBefore] = useState("");

    const fetchByName = useCallback(async (value) => {
        if (!value) return [];
        const res = await axios.get("http://localhost:8082/actors/name", { params: { name: value } });
        return res.data;
    }, []);

    const fetchBySurname = useCallback(async (value) => {
        if (!value) return [];
        const res = await axios.get("http://localhost:8082/actors/surname", { params: { surname: value } });
        return res.data;
    }, []);

    const fetchByBirthdateBefore = useCallback(async (value) => {
        if (!value) return [];
        const res = await axios.get("http://localhost:8082/actors/birthdate-before", { params: { date: value } });
        return res.data;
    }, []);

    useEffect(() => {
        async function fetchActors() {
            setLoading(true);
            setError(null);

            try {
                const results = await Promise.all([
                    fetchByName(name.trim()),
                    fetchBySurname(surname.trim()),
                    fetchByBirthdateBefore(birthdateBefore),
                ]);

                const combined = results.flat();
                const uniqueActors = Array.from(new Map(combined.map(a => [a.id, a])).values());

                setActors(uniqueActors);
            } catch (e) {
                setError("Ошибка при поиске актёров");
            } finally {
                setLoading(false);
            }
        }

        if (name || surname || birthdateBefore) {
            fetchActors();
        } else {
            setActors([]);
        }
    }, [name, surname, birthdateBefore, fetchByName, fetchBySurname, fetchByBirthdateBefore]);

    return (
        <div className="actors-container">
            <div className="actors-back">
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>
            <h2 className="actors-title">Поиск актёров</h2>

            <div className="search-filters">
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
            </div>

            {loading && <p className="loading-text">Загрузка...</p>}
            {error && <p className="error-text">{error}</p>}

            <ul className="actors-list">
                {actors.length > 0 ? (
                    actors.map(actor => (
                        <li key={actor.id}>
                            <Link to={`/actors/${actor.id}`}>
                                {actor.name} {actor.surname}
                            </Link>
                        </li>
                    ))
                ) : (
                    !loading && <p style={{ textAlign: "center" }}>Актёры не найдены.</p>
                )}
            </ul>
        </div>
    );
}