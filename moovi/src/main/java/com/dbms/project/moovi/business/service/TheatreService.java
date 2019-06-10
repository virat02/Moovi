package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.Theatre;
import com.dbms.project.moovi.data.entity.TheatreManager;
import com.dbms.project.moovi.data.repository.TheatreManagerRepository;
import com.dbms.project.moovi.data.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TheatreService {

    @Autowired
    private TheatreRepository theatreRepository;

    @Autowired
    private TheatreManagerRepository theatreManagerRepository;

    @GetMapping("/api/theatre")
    public List<Theatre> findAllTheatre(){
        return (List<Theatre>) theatreRepository.findAll();
    }

    @PostMapping("/api/theatre")
    public Theatre createTheatre(@RequestBody Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    @PostMapping("/api/manage/theatre/{theatreId}/manager/{username}")
    public void theatreManagedBy(
            @PathVariable("username") String username,
            @PathVariable("theatreId") long theatreId){
        if(theatreRepository.findById(theatreId).isPresent()
                && theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).isPresent()) {
            Theatre theatre = theatreRepository.findById(theatreId).get();
            TheatreManager theatreManager = theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).get();
            theatreManager.managedTheatres(theatre);
            theatreRepository.save(theatre);
        }
    }
}
