import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate,useParams, Link } from "react-router-dom";
import "./actor.css"
export default function ActorDetailsPage() {
    const { id } = useParams();
    const navigate = useNavigate();
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
        <div className="actor-details-container">
            <div className="actor-navigation">
                <button onClick={() => navigate(-1)}>Назад</button>
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>

            <div className="actor-content">
                <div className="actor-photo">
                    <img
                        src={actor.photoUrl}
                        alt={`${actor.name} ${actor.surname}`}
                    />
                </div>

                <div className="actor-info">
                    <h2>{actor.name} {actor.surname}</h2>
                    <p><strong>Дата рождения:</strong> {new Date(actor.birthdate).toLocaleDateString()}</p>

                    <h3>Роли в фильмах</h3>
                    {roles.length === 0 ? (
                        <p>Роли не найдены</p>
                    ) : (
                        <ul className="actor-roles-list">
                            {roles.map(role => (
                                <li key={role.idMovie}>
                                    <Link to={`/movies/${role.idMovie}`}>
                                        {role.title}
                                    </Link> — роль: {role.role}
                                </li>
                            ))}
                        </ul>
                    )}
                </div>
            </div>
        </div>
    );
}
