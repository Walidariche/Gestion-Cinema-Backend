package com.example.cinema.service;

import com.example.cinema.dao.*;
import com.example.cinema.entities.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService{
     @Autowired
      private VilleRepository villeRepository;
     @Autowired
     private CinemaRepository cinemaRepository;
     @Autowired
     private SalleRepository salleRepository;
     @Autowired
     private PlaceRepository placeRepository;
     @Autowired
     private seanceRepository seanceRepository;

     @Autowired
     private CategorieRepository categorieRepository;
    @Autowired
     private FilmRepository filmRepository;
    @Autowired
     private ProjectionRepository projectionRepository;
    @Autowired
     private TicketRepository ticketRepository;
    @Override
    public void initVilles() {
        Stream.of("casablanca","rabat","kenitra","dakhla","marrakech").forEach(nameville->{
            Ville ville=new Ville();
            ville.setName(nameville);
            villeRepository.save(ville);
        });

    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v ->{
        Stream.of("megarama","imax","founoun","chahrazad","daouliz").forEach(nameCinema->{
            Cinema cinema=new Cinema();
            cinema.setName(nameCinema);
            cinema.setNombresalles((int) (3+Math.random()*7));
            cinema.setVille(v);
            cinemaRepository.save(cinema);
        });

        } );
    }
    @Override
    public void initSalles() {
     cinemaRepository.findAll().forEach(cinema -> {
     for(int i=0 ; i<cinema.getNombresalles();i++){
         Salle salle=new Salle();
         salle.setName("salle"+(i+1));
         salle.setCinema(cinema);
         salle.setNombrePlace((int) (15+Math.random()*20));
         salleRepository.save(salle);

     }

     });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle -> {

            for (int i=0;i< salle.getNombrePlace();i++){
                Place place=new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }


        });

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat =new SimpleDateFormat("HH:mm");
        Stream.of("15:00","04:00","18:00","19:45").forEach(s->{
            Seance seance=new Seance();
            try {
                seance.setHeuredebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        });

    }

    @Override
    public void initCategories() {
        Stream.of("histoire","comedie","drama","action").forEach(cat->{
            Categorie categorie =new Categorie();
            categorie.setName(cat);
            categorieRepository.save(categorie);
        });

    }

    @Override
    public void initFilms() {
        double[] durees=new double[]{1,1.5,3,2};
        List<Categorie>categories=categorieRepository.findAll();
        Stream.of("game of thrones","vikings","lost","prison breack").forEach(Nomfilm->{
            Film film=new Film();
            film.setTitre(Nomfilm);
            film.setDuree(durees[new Random().nextInt(durees.length)]);
            film.setPhoto(Nomfilm.replaceAll(" ","")+".jpg");
            film.setCategorie(categories.get(new Random().nextInt(categories.size())));
            filmRepository.save(film);
        });

    }

    @Override
    public void initProjections() {
        double[] prices =new double[]{120,150,50,30,40,45};
        List<Film> films=filmRepository.findAll();
        villeRepository.findAll().forEach(ville -> {
            ville.getCinemas().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    int index=new Random().nextInt(films.size());
                   Film film =films.get(index);
                        seanceRepository.findAll().forEach(seance -> {
                            Projection projection=new Projection();
                            projection.setDateProjection(new Date());
                            projection.setFilm(film);
                            projection.setPrix(prices[new Random().nextInt(prices.length)]);
                            projection.setSalle(salle);
                            projection.setSeance(seance);
                            projectionRepository.save(projection);

                        });

                });
            });
        });

    }

    @Override
    public void initTickets() {
        projectionRepository.findAll().forEach(p -> {
         p.getSalle().getPlaces().forEach(place -> {

             Ticket ticket=new Ticket();
             ticket.setPlace(place);
             ticket.setPrix(p.getPrix());
             ticket.setProjection(p);
             ticket.setReserve(false);
             ticketRepository.save(ticket);
         });

        });

    }
}
