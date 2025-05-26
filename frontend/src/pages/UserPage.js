import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";
import Reviews from "../components/Reviews";
import "./user.css"

export default function UserDetailsPage() {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    const currentUser = JSON.parse(localStorage.getItem("currentUser"));
    const currentUserId = currentUser?.id;

    useEffect(() => {
        async function fetchUser() {
            try {
                const res = await axios.get(`http://localhost:8082/users/${currentUserId}`);
                setUser(res.data);
            } catch (e) {
                alert("Ошибка загрузки данных пользователя");
            } finally {
                setLoading(false);
            }
        }
        if (currentUserId) fetchUser();
    }, [currentUserId]);

    if (loading) return <div>Загрузка...</div>;
    if (!user) return <div>Пользователь не найден</div>;

    return (
        <div className="user-details-container">
            <div className="user-details-back">
                <Link to="/home">
                    <button>Главная</button>
                </Link>
            </div>

            <div className="user-info">
                <div className="user-info-text">
                    <div>
                        <span className="label">Name:</span> {user.name}
                    </div>
                    <div>
                        <span className="label">Surname:</span> {user.surname}
                    </div>
                    <div>
                        <span className="label">e-mail:</span> {user.email}
                    </div>
                    <div>
                        <span className="label">role:</span> {user.role?.name}
                    </div>
                </div>

                <div className="user-avatar">
                    <img
                        src={user.avatarUrl || "https://via.placeholder.com/100x125?text=Avatar"}
                        alt="Avatar"
                    />
                </div>
            </div>

            <div className="reviews-section">
                <h3>Reviews</h3>
                {user.reviews && user.reviews.length > 0 ? (
                    user.reviews.map((review) => (
                        <Reviews key={review.id} review={review} />
                    ))
                ) : (
                    <p>Нет отзывов.</p>
                )}
            </div>
        </div>
    );
}