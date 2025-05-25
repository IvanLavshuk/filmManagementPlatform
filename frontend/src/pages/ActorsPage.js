import React, { useState, useEffect } from "react";
import axios from "axios";
import { Link } from "react-router-dom";

export default function ActorsPage() {
    const [actors, setActors] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get("http://localhost:8082/actors/all")
            .then(response => {
                setActors(response.data);
                setLoading(false);
            })
            .catch(() => {
                setError("Ошибка при загрузке списка актёров");
                setLoading(false);
            });
    }, []);

    if (loading) return <p>Загрузка актёров...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h2>Список актёров</h2>
            <ul>
                {actors.map((actor, index) => (
                    <li key={index}>
                        <Link to={`/actors/${actor.id}`}>
                            {actor.name} {actor.surname}
                        </Link>
                    </li>
                ))}
            </ul>
        </div>
    );
}
