import React from "react";
import "./moviesFilter.css";

export default function MoviesFilter({
                                         searchTerm,
                                         setSearchTerm,
                                         country,
                                         setCountry,
                                         directorName,
                                         setDirectorName,
                                         directorSurname,
                                         setDirectorSurname,
                                         sortOrder,
                                         setSortOrder,
                                     }) {
    return (
        <div className="movies-filter">
            <input
                type="text"
                placeholder="Поиск по названию..."
                value={searchTerm}
                onChange={e => setSearchTerm(e.target.value)}
            />
            <input
                type="text"
                placeholder="Страна"
                value={country}
                onChange={e => setCountry(e.target.value)}
            />
            <input
                type="text"
                placeholder="Имя режиссёра"
                value={directorName}
                onChange={e => setDirectorName(e.target.value)}
            />
            <input
                type="text"
                placeholder="Фамилия режиссёра"
                value={directorSurname}
                onChange={e => setDirectorSurname(e.target.value)}
            />
            <select
                value={sortOrder}
                onChange={e => setSortOrder(e.target.value)}
            >
                <option value="">Сортировка</option>
                <option value="date-asc">Дата ↑</option>
                <option value="date-desc">Дата ↓</option>
                <option value="rating-desc">Рейтинг ↓</option>
            </select>
        </div>
    );
}
