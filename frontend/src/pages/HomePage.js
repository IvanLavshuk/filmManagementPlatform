import React from "react";
import { Link, useNavigate } from "react-router-dom";

export default function HomePage() {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem("token");
        navigate("/login");
    };

    return (
        <div>
            <h2>Home</h2>
            <nav>
                <Link to="/actors">Actors</Link> |{" "}
                <Link to="/directors">Directors</Link> |{" "}
                <Link to="/movies">Movies</Link> |{" "}
                <Link to="/user">User page</Link> |{" "}
                <Link to="/genres">Genres</Link>
            </nav>
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
}
