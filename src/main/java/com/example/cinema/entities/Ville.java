package com.example.cinema.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.io.Serializable;
import java.util.Collection;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ville implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double langitude,latitude,altitude;
  @OneToMany(mappedBy = "ville")
    private Collection<Cinema>cinemas;
}
