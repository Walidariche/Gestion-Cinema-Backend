package com.example.cinema.entities;

import lombok.Data;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.webmvc.support.Projector;

import java.util.Date;
import java.util.Collection;

@Projection(name = "p1",types ={com.example.cinema.entities.Projection.class})
public interface Projectionproj {

    public Long getId();
    public double getPrix();
    public Date getDateProjection();
    public Salle getSalle();
    public Film getFilm();
    public Seance getSeance();
    public Collection<Ticket> getTickets();

}
