package com.film.management.platform.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "movies")
public class Movie {
    public Movie(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movie")
    private Integer id;
    @Column(name = "title", nullable = false, length = 45)
    private String title;
    @Column(name = "country", nullable = false, length = 45)
    private String country;
    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Column(name = "url_poster", nullable = false, length = 45)
    public String urlPoster;

    @ManyToMany
    @JoinTable(name = "movies_genres",
            joinColumns = @JoinColumn(name = "id_movie", referencedColumnName = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_genre", referencedColumnName = "id_genre"))
    @Builder.Default
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "movies_directors",
            joinColumns = @JoinColumn(name = "id_movie", referencedColumnName = "id_movie"),
            inverseJoinColumns = @JoinColumn(name = "id_director", referencedColumnName = "id_director"))
    @Builder.Default
    private Set<Director> directors = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<MovieActor> movieActors = new HashSet<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<Review> reviews = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", releaseDate=" + releaseDate +
                ", description='" + description + '\'' +
                ", urlPoster='" + urlPoster + '\'' +
                '}';
    }

}
