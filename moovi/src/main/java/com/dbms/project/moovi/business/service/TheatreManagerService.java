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
public class TheatreManagerService extends Utils {

    @Autowired
    private TheatreManagerRepository theatreManagerRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    @GetMapping("/api/theatremanager")
    public List<TheatreManager> findManagerByCredential(@RequestParam(name = "username", required = false) String username,
                                                        @RequestParam(name = "password", required = false) String password){
        if(username != null && password != null)
            return (List<TheatreManager>) theatreManagerRepository.findManagerByCredential(username, password);
        return (List<TheatreManager>) theatreManagerRepository.findAll();
    }

    @PostMapping("/api/theatremanager")
    public TheatreManager createTheatreManager(@RequestBody TheatreManager theatreManager) {
        return theatreManagerRepository.save(theatreManager);
    }

    @PostMapping("/api/manage/manager/{username}/theatre/{theatreId}")
    public void theatreManagedBy(
            @PathVariable("username") String username,
            @PathVariable("theatreId") long theatreId){
        if(theatreRepository.findById(theatreId).isPresent()
                && theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).isPresent()) {
            Theatre theatre = theatreRepository.findById(theatreId).get();
            TheatreManager theatreManager = theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).get();
            theatre.setTheatreManager(theatreManager);
            theatreManagerRepository.save(theatreManager);
        }
    }

    @GetMapping("/api/manager/{username}/theatremanaged")
    public List<Theatre> listOfTheatreManaged(
            @PathVariable("username") String username){
        if(theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).isPresent()) {
            TheatreManager manager = theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).get();
            return manager.getListOfTheatresManaged();
        }
        return null;
    }

    @PostMapping("/api/delete/theatremanager/{username}/theatre/{theatreId}")
    public void deleteManagedTheatre(
            @PathVariable("username") String username,
            @PathVariable("theatreId") long theatreId){
        if(theatreRepository.findById(theatreId).isPresent()
                && theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).isPresent()){
            TheatreManager theatreManager = theatreManagerRepository.findById(theatreManagerRepository.findManagerIdByUsername(username)).get();
            Theatre theatre = theatreRepository.findById(theatreId).get();
            theatreManager.getListOfTheatresManaged().remove(theatre);
            theatreRepository.delete(theatre);
        }
    }
}
