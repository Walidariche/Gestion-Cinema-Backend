package com.example.cinema.web;


import com.example.cinema.dao.FilmRepository;
import com.example.cinema.dao.TicketRepository;
import com.example.cinema.entities.Film;
import com.example.cinema.entities.Ticket;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
    @Autowired
   private FilmRepository filmRepository;
    @Autowired
    private TicketRepository ticketRepository;
   @GetMapping(path = "/imageFilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[]image(@PathVariable(name = "id")Long id) throws IOException {
       Film f=filmRepository.findById(id).get();
       String PhotoName =f.getPhoto();
       File file =new File(System.getProperty("user.home")+"/cinema/images/"+PhotoName);
       Path path=Paths.get(file.toURI());
       return Files.readAllBytes(path);

   }
   @PostMapping("/payerTickets")
   @Transactional
   public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
       List<Ticket>listTickets=new ArrayList<>();
     ticketForm.getTickets().forEach(idTicket->{
         Ticket ticket=ticketRepository.findById(idTicket).get();
         ticket.setNomClient(ticketForm.getNomClient());
         ticket.setReserve(true);
         ticketRepository.save(ticket);
         listTickets.add(ticket);

     });
     return listTickets;
   }
}
   @Data
  class  TicketForm{
    private String nomClient;
    private List<Long> tickets=new ArrayList<>();
  }