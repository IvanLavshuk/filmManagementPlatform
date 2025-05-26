import React, { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import axios from "axios";
import "./registration.css"

export default function RegistrationPage() {
    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [avatarUrl, setAvatarUrl] = useState("");
    const [idRole] = useState(2); // Например, 2 — обычный пользователь
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post("/users/create", {
                name,
                surname,
                email,
                password,
                avatarUrl,
                id_role: idRole
            });
            alert("Регистрация успешна! Теперь войдите.");
            navigate("/login");
        } catch (err) {
            alert("Ошибка регистрации");
        }
    };

    return (
        <div className="registration-container">
            <h2>Регистрация</h2>
            <form className="registration-form" onSubmit={handleSubmit}>
                <input
                    type="text"
                    placeholder="Имя"
                    value={name}
                    onChange={e => setName(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Фамилия"
                    value={surname}
                    onChange={e => setSurname(e.target.value)}
                    required
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                    required
                />
                <input
                    type="password"
                    placeholder="Пароль"
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                    required
                />
                <input
                    type="text"
                    placeholder="Ссылка на аватар (необязательно)"
                    value={avatarUrl}
                    onChange={e => setAvatarUrl(e.target.value)}
                />
                <button type="submit">Зарегистрироваться</button>
            </form>
            <p>
                Уже есть аккаунт? <Link to="/login">Войти</Link>
            </p>
        </div>
    );
}