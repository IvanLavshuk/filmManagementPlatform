
import React from "react";

export default function Reviews({ review }) {
    return (
        <div style={{ borderBottom: "1px solid #eee", marginBottom: 8, paddingBottom: 8 }}>
            <span style={{ fontWeight: 500 }}>Фильм: {review.title}</span>
            <br />
            <span style={{ color: "#888" }}>Дата: {new Date(review.date).toLocaleDateString()}
            </span>
            <br />

            <span>Оценка: {review.rating}</span>
            <p style={{ margin: "4px 0" }}>{review.text}</p>
        </div>
    );
}
