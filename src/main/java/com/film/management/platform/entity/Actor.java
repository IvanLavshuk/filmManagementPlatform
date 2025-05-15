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
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actor")
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "surname", nullable = false, length = 45)
    private String surname;
    @Column(name = "birthdate",nullable = false)
    private LocalDate birthdate;
    @Column(name = "url_photo",length = 45)
    private String photoUrl;

    @OneToMany(mappedBy = "actor", cascade = CascadeType.ALL)
    @Builder.Default
    private Set<MovieActor> movieActors = new HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthdate=" + birthdate +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }

}
