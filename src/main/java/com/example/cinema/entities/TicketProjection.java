package com.example.cinema.entities;


import org.springframework.data.rest.core.config.Projection;

@Projection(name = "ticketProj",types = {com.example.cinema.entities.Ticket.class})
public interface TicketProjection {

    public  Long getId();
    public String getNomClient();
    public Double getPrix();
   public Integer getCodePayement();
   public Boolean getReserve();
   public  Place getplace();


}

