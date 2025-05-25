import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams, Link } from "react-router-dom";

export default function ActorDetailsPage() {
    const { id } = useParams();
    const [actor, setActor] = useState(null);
    const [roles, setRoles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        async function fetchActorData() {
            if (!id || isNaN(Number(id))) {
                setError("Некорректный ID актёра");
                setLoading(false);
                return;
            }

            try {
                const actorRes = await axios.get(`http://localhost:8082/actors/${id}`);
                setActor(actorRes.data);

                const rolesRes = await axios.get(`http://localhost:8082/movies-actors/actor-id/${id}`);
                setRoles(rolesRes.data);

                setLoading(false);
            } catch (err) {
                console.error("Ошибка при загрузке актёра:", err.response ? err.response.data : err.message);
                setError("Ошибка при загрузке данных актёра");
                setLoading(false);
            }
        }
        fetchActorData();
    }, [id]);

    if (loading) return <p>Загрузка данных актёра...</p>;
    if (error) return <p>{error}</p>;
    if (!actor) return <p>Актёр не найден</p>;

    return (
        <div style={{ display: "flex", gap: 32, margin: 32 }}>
            <div style={{ flex: 1 }}>
                <img
                    src={actor.photoUrl}
                    alt={`${actor.name} ${actor.surname}`}
                    style={{ maxWidth: 250, borderRadius: 8, border: "1px solid #ccc" }}
                />
            </div>
            <div style={{ flex: 2 }}>
                <h2>{actor.name} {actor.surname}</h2>
                <p><strong>Дата рождения:</strong> {new Date(actor.birthdate).toLocaleDateString()}</p>

                <h3>Роли в фильмах</h3>
                {roles.length === 0 ? (
                    <p>Роли не найдены</p>
                ) : (
                    <ul>
                        {roles.map(role => (
                            <li key={role.idMovie}>
                                <Link to={`/movies-actors/${role.idMovie}`}>
                                    {role.title}
                                </Link> — роль: {role.role}
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}
