import React, { useEffect, useState } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";

function Review({ review }) {
    return (
        <div style={{ borderBottom: "1px solid #ccc", marginBottom: 12, paddingBottom: 12 }}>
            <strong>{review.userFullName}</strong> — <em>{new Date(review.date).toLocaleDateString()}</em>
            <br />
            <span>Оценка: {review.rating}</span>
            <p>{review.text}</p>
        </div>
    );
}

export default function MovieDetailsPage() {
    const { id } = useParams();
    const [movie, setMovie] = useState(null);
    const [reviews, setReviews] = useState([]);
    const [loading, setLoading] = useState(true);
    const [reviewText, setReviewText] = useState("");
    const [reviewRating, setReviewRating] = useState(5);
    const [adding, setAdding] = useState(false);

    useEffect(() => {
        async function fetchData() {
            try {
                // Получаем данные фильма
                const movieRes = await axios.get(`http://localhost:8082/movies/${id}`);
                setMovie(movieRes.data);

                // Получаем отзывы по фильму
                const reviewsRes = await axios.get(`http://localhost:8082/reviews/movie-id`, {
                    params: { id }
                });
                setReviews(reviewsRes.data);
            } catch (e) {
                console.info(e)
                alert("Ошибка загрузкИ данных");
            } finally {
                setLoading(false);
            }
        }
        fetchData();
    }, [id]);

    const handleAddReview = async (e) => {
        e.preventDefault();
        setAdding(true);
        try {
            await axios.post("http://localhost:8082/reviews/create", {
                movieId: id,
                rating: reviewRating,
                text: reviewText
                // userId или userFullName нужно передавать, если требуется авторизация
            });
            // Обновляем список отзывов
            const reviewsRes = await axios.get(`http://localhost:8082/reviews/movie-id`, {
                params: { id }
            });
            setReviews(reviewsRes.data);
            setReviewText("");
            setReviewRating(5);
        } catch (e) {
            alert("Ошибка при добавлении отзыва");
        }
        setAdding(false);
    };

    if (loading) return <div>Загрузка...</div>;
    if (!movie) return <div>Фильм не найден</div>;

    return (
        <div style={{ display: "flex", gap: 32, margin: 32 }}>
            {/* Левая часть — информация о фильме */}
            <div style={{ flex: 2 }}>
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
                {/* Можно добавить вывод актёров, если потребуется */}
            </div>

            {/* Правая часть — постер */}
            <div style={{ flex: 1, textAlign: "center" }}>
                <img
                    src={movie.urlPoster}
                    alt={`Постер фильма ${movie.title}`}
                    style={{ maxWidth: 200, maxHeight: 300, border: "1px solid #ccc" }}
                />
            </div>

            {/* Отзывы и форма добавления */}
            <div style={{ flex: 2 }}>
                <h3>Отзывы</h3>
                <form onSubmit={handleAddReview} style={{ marginBottom: 16 }}>
          <textarea
              value={reviewText}
              onChange={e => setReviewText(e.target.value)}
              placeholder="Ваш отзыв"
              rows={3}
              style={{ width: "100%", resize: "vertical" }}
              required
          />
                    <div style={{ marginTop: 8 }}>
                        <label>
                            Оценка:
                            <select
                                value={reviewRating}
                                onChange={e => setReviewRating(Number(e.target.value))}
                                style={{ marginLeft: 8 }}
                            >
                                {[5, 4, 3, 2, 1].map(num => (
                                    <option key={num} value={num}>{num}</option>
                                ))}
                            </select>
                        </label>
                        <button type="submit" disabled={adding} style={{ marginLeft: 16 }}>
                            {adding ? "Добавление..." : "Добавить отзыв"}
                        </button>
                    </div>
                </form>

                <div>
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
                            />
                        ))
                    )}
                </div>
            </div>
        </div>
    );
}
