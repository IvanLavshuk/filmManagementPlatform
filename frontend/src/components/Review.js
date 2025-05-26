// src/components/Review.js
import React, { useState } from "react";

export default function Review({ review, currentUser, onDelete, onEdit }) {
    const canModify = currentUser && review.userFullName === currentUser.name + " " + currentUser.surname;
    const [isEditing, setIsEditing] = useState(false);
    const [editText, setEditText] = useState(review.text);
    const [editRating, setEditRating] = useState(review.rating);
    const [saving, setSaving] = useState(false);

    const handleSave = async () => {
        setSaving(true);
        try {
            await onEdit(review.id, editText, editRating);
            setIsEditing(false);
        } catch (error) {
            alert("Ошибка при сохранении отзыва");
        }
        setSaving(false);
    };

    if (isEditing) {
        return (
            <div style={{ borderBottom: "1px solid #ccc", marginBottom: 12, paddingBottom: 12 }}>
                <strong>{review.userFullName}</strong> — <em>{new Date(review.date).toLocaleDateString()}</em>
                <br />
                <textarea
                    value={editText}
                    onChange={e => setEditText(e.target.value)}
                    rows={3}
                    style={{ width: "100%", resize: "vertical", marginTop: 8 }}
                />
                <div style={{ marginTop: 8 }}>
                    <label>
                        Оценка:
                        <input
                            type="number"
                            value={editRating}
                            onChange={e => setEditRating(parseFloat(e.target.value))}
                            min={1}
                            max={10}
                            step={0.1}
                            style={{ marginLeft: 8, width: 60 }}
                            required
                        />
                    </label>
                </div>
                <button onClick={handleSave} disabled={saving} style={{ marginRight: 8 }}>
                    {saving ? "Сохраняю..." : "Сохранить"}
                </button>
                <button onClick={() => setIsEditing(false)} disabled={saving}>Отмена</button>
            </div>
        );
    }

    return (
        <div style={{ borderBottom: "1px solid #ccc", marginBottom: 12, paddingBottom: 12 }}>
            <strong>{review.userFullName}</strong> — <em>{new Date(review.date).toLocaleDateString()}</em>
            <br />
            <span>Оценка: {review.rating}</span>
            <p>{review.text}</p>
            {canModify && (
                <>
                    <button onClick={() => setIsEditing(true)} style={{ marginRight: 8 }}>
                        Редактировать
                    </button>
                    <button onClick={() => onDelete(review.id)} style={{ color: "red" }}>
                        Удалить
                    </button>
                </>
            )}
        </div>
    );
}
