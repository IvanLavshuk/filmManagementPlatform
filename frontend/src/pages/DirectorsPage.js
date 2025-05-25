import React, { useEffect, useState } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function DirectorsPage() {
    const [directors, setDirectors] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get("http://localhost:8082/directors/all")
            .then(response => {
                setDirectors(response.data);
                setLoading(false);
            })
            .catch(() => {
                setError("Ошибка при загрузке списка режиссёров");
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Загрузка режиссёров...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h2>Список режиссёров</h2>
            <ul>
                {directors.map(director => (
                    <li key={director.id}>
                        <Link to={`/directors/${director.id}`}>
                            {director.name} {director.surname}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}
