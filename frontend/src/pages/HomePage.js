import React from "react";
import { Link, useNavigate } from "react-router-dom";
import "./home.css";

export default function HomePage() {
    const navigate = useNavigate();

    const handleLogout = () => {

        localStorage.removeItem("currentUser");
        navigate("/login");
    };

    return (
        <div className="home-container">
            <h2>Home</h2>
            <nav className="home-nav">
                <Link to="/actors">Actors</Link> |{" "}
                <Link to="/directors">Directors</Link> |{" "}
                <Link to="/movies">Movies</Link> |{" "}
                <Link to="/user">User page</Link> |{" "}
                <Link to="/genres">Genres</Link>
            </nav>
            <button className="logout-button" onClick={handleLogout}>Logout</button>
        </div>
    );
}
