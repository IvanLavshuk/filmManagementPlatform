package com.film.management.platform.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_review")
    private Integer id;
    @Column(name = "rating", nullable = false)
    private Double rating;
    @Column(name = "text", nullable = false, length = 500)
    private String text;
    @Column(name = "create_date", nullable = false)
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "id_user", referencedColumnName = "id_user", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_movie", referencedColumnName = "id_movie", nullable = false)
    private Movie movie;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", rating=" + rating +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", userId=" + (user != null ? user.getId() : null) +
                ", movieId=" + (movie != null ? movie.getId() : null) +
                '}';
    }


}
