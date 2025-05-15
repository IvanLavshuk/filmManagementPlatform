package com.film.management.platform.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "movies_actors")
public class MovieActor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "role_name", nullable = false)
    private String role;

    @ManyToOne
    @JoinColumn(name = "id_movie", referencedColumnName = "id_movie", nullable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "id_actor", referencedColumnName = "id_actor", nullable = false)
    private Actor actor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieActor that = (MovieActor) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MovieActor{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", movieId=" + (movie != null ? movie.getId() : "null") +
                ", actorId=" + (actor != null ? actor.getId() : "null") +
                '}';
    }

}
