import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate, Link, useParams } from "react-router-dom";
import Review from "../components/Review";  // импорт компонента Review
import "./movie.css"
export default function MovieDetailsPage() {
    const { id } = useParams();
    const navigate = useNavigate();
    const [movie, setMovie] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [reviewText, setReviewText] = useState("");
    const [reviewRating, setReviewRating] = useState(5);
    const [adding, setAdding] = useState(false);
    const currentUser = JSON.parse(localStorage.getItem("currentUser"));

    useEffect(() => {
        async function fetchData() {
            try {
                const movieRes = await axios.get(`http://localhost:8082/movies/${id}`);
                setMovie(movieRes.data);

                const reviewsRes = await axios.get(`http://localhost:8082/reviews/movie-id/${id}`);
                setReviews(reviewsRes.data);
            } catch (error) {
                console.error("Ошибка при загрузке данных:", error);
                alert("Ошибка загрузки данных");
            } finally {
                setLoading(false);
            }
        }
        fetchData();
    }, [id]);

    const handleAddReview = async (e) => {
        e.preventDefault();
        if (!currentUser) {
            alert("Пожалуйста, войдите в систему, чтобы оставить отзыв.");
            return;
        }
        setAdding(true);
        try {
            await axios.post("http://localhost:8082/reviews/create", {
                title: movie.title,
                rating: reviewRating,
                text: reviewText,
                userFullName: currentUser.name + " " + currentUser.surname,
            });

            const reviewsRes = await axios.get(`http://localhost:8082/reviews/movie-id/${id}`);
            setReviews(reviewsRes.data);
            setReviewText("");
            setReviewRating(5);
        } catch (error) {
            console.error("Ошибка при добавлении отзыва:", error);
            alert("Ошибка при добавлении отзыва");
        } finally {
            setAdding(false);
        }
    };

    const handleDeleteReview = async (reviewId) => {
        if (!currentUser) {
            alert("Пожалуйста, войдите в систему.");
            return;
        }
        try {
            await axios.delete(`http://localhost:8082/reviews/${reviewId}`);

            const reviewsRes = await axios.get(`http://localhost:8082/reviews/movie-id/${id}`);
            setReviews(reviewsRes.data);
        } catch (error) {
            console.error("Ошибка при удалении отзыва:", error);
            alert("Ошибка при удалении отзыва");
        }
    };

    const handleEditReview = async (reviewId, newText, newRating) => {
        if (!currentUser) {
            alert("Пожалуйста, войдите в систему.");
            return;
        }
        try {
            await axios.put("http://localhost:8082/reviews", {
                id: reviewId,
                title: movie.title,
                rating: newRating,
                text: newText,
                userFullName: currentUser.name + " " + currentUser.surname,
            });

            const reviewsRes = await axios.get(`http://localhost:8082/reviews/movie-id/${id}`);
            setReviews(reviewsRes.data);
        } catch (error) {
            console.error("Ошибка при редактировании отзыва:", error);
            alert("Ошибка при редактировании отзыва");
            throw error;
        }
    };

    const hasUserReview = currentUser && reviews.some(
        review =>
            (review.userFullName || (review.user ? review.user.fullName : "")) ===
            (currentUser.name + " " + currentUser.surname)
    );

    if (loading) return <div>Загрузка...</div>;
    if (!movie) return <div>Фильм не найден</div>;

    return (
        <div className="movie-details-container">
            <div className="movie-details-main">
                <div className="movie-details-nav">
                    <button onClick={() => navigate(-1)}>Назад</button>
                    <Link to="/home">
                        <button>Главная</button>
                    </Link>
                </div>
                <h2>{movie.title}</h2>
                <div><strong>Страна:</strong> {movie.country}</div>
                <div><strong>Дата выхода:</strong> {new Date(movie.releaseDate).toLocaleDateString()}</div>
                <div style={{ marginTop: 12 }}><strong>Описание:</strong> {movie.description}</div>
                <div style={{ marginTop: 12 }}>
                    <strong>Жанры:</strong> {movie.genreNames?.join(", ")}
                </div>
                <div>
                    <strong>Режиссёры:</strong> {movie.directorFullNames?.join(", ")}
                </div>

                {!hasUserReview && (
                    <div className="add-review-section">
                        <h3>Добавить отзыв</h3>
                        <form onSubmit={handleAddReview}>
                            <textarea
                                value={reviewText}
                                onChange={e => setReviewText(e.target.value)}
                                placeholder="Ваш отзыв"
                                rows={3}
                                required
                            />
                            <div className="add-review-controls">
                                <label>
                                    Оценка:
                                    <input
                                        type="number"
                                        value={reviewRating}
                                        onChange={e => setReviewRating(parseFloat(e.target.value))}
                                        min={1}
                                        max={10}
                                        step={0.1}
                                        required
                                    />
                                </label>
                                <button type="submit" disabled={adding}>
                                    {adding ? "Добавление..." : "Добавить отзыв"}
                                </button>
                            </div>
                        </form>
                    </div>
                )}
                {hasUserReview && (
                    <div className="review-info-message">
                        Вы уже оставили отзыв для этого фильма.
                    </div>
                )}

                <h3>Отзывы</h3>
                {reviews.length === 0 ? (
                    <p>Пока нет отзывов.</p>
                ) : (
                    reviews.map(review => (
                        <Review
                            key={review.id}
                            review={{
                                ...review,
                                userFullName: review.userFullName || (review.user ? review.user.fullName : "Аноним"),
                                date: review.date || review.createDate
                            }}
                            currentUser={currentUser}
                            onDelete={handleDeleteReview}
                            onEdit={handleEditReview}
                        />
                    ))
                )}
            </div>
            <div className="movie-details-poster">
                <img
                    src={movie.urlPoster}
                    alt={`Постер фильма ${movie.title}`}
                />
            </div>
        </div>
    );
}
