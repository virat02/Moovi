package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.Movie;
import com.dbms.project.moovi.data.entity.Screen;
import com.dbms.project.moovi.data.entity.Theatre;
import com.dbms.project.moovi.data.repository.MovieRepository;
import com.dbms.project.moovi.data.repository.ScreenRepository;
import com.dbms.project.moovi.data.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @GetMapping("/api/screen")
    public List<Screen> findAllScreen(){
        return (List<Screen>) screenRepository.findAll();
    }

    @PostMapping("/api/screen")
    public Screen createScreen(@RequestBody Screen screen){
        return screenRepository.save(screen);
    }

    @PostMapping("/api/screen/{screenId}/movie/{movieId}")
    public void screenShowsMovie(
            @PathVariable("screenId") long screenId,
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()
                && screenRepository.findById(screenId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            Screen screen = screenRepository.findById(screenId).get();
            screen.setScreenHasMovie(movie);
            screenRepository.save(screen);
        }
    }

    @PostMapping("/api/screen/{screenId}/theatre/{theatreId}")
    public void screenInTheatre(
            @PathVariable("screenId") long screenId,
            @PathVariable("theatreId") long theatreId){
        if(screenRepository.findById(screenId).isPresent()
                && theatreRepository.findById(theatreId).isPresent()){
            Screen screen = screenRepository.findById(screenId).get();
            Theatre theatre = theatreRepository.findById(theatreId).get();
            screen.setTheatre(theatre);
            screenRepository.save(screen);
        }
    }
}
