package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.Actor;
import com.dbms.project.moovi.data.entity.AdRecruiter;
import com.dbms.project.moovi.data.entity.TheatreManager;
import com.dbms.project.moovi.data.repository.ActorRepository;
import com.dbms.project.moovi.data.repository.AdRecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class AdRecruiterService extends Utils {

    @Autowired
    private AdRecruiterRepository adRecruiterRepository;
    
    @Autowired
    private ActorRepository actorRepository;

    @PostMapping("/api/adrecruiter")
    public AdRecruiter createAdRecruiter(@RequestBody AdRecruiter adRecruiter){
        return adRecruiterRepository.save(adRecruiter);
    }

    @GetMapping("/api/adrecruiter")
    public List<AdRecruiter> findAllAdRecruiters(@RequestParam(name="username", required = false) String username,
                                                 @RequestParam(name = "password", required = false) String password){
        if(username != null && password != null)
            return (List<AdRecruiter>) adRecruiterRepository.findAdRecruiterByCredential(username, password);
        return (List<AdRecruiter>) adRecruiterRepository.findAll();
    }

    @PostMapping("/api/recruit/adrecruiter/{username}/actor/{actorId}")
    public void AdRecruiterRecruitsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()
                && adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            AdRecruiter adRecruiter = adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).get();
            adRecruiter.recruitsActor(actor);
            adRecruiterRepository.save(adRecruiter);
        }
    }

    @GetMapping("/api/recruit/adrecruiter/{username}/actorsrecruited")
    public List<Actor> getListOfActors(
            @PathVariable("username") String username){
        if (adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).isPresent()) {
            AdRecruiter adRecruiter = adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).get();
            return adRecruiter.getRecruitedActors();
        }
        return null;
    }

    @PostMapping("/api/delete/recruiter/{username}/actor/{actorId}")
    public void deleteRecruitedActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId){
        if(adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).isPresent()
                && actorRepository.findById(actorId).isPresent()){
            AdRecruiter adRecruiter = adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).get();
            Actor actor = actorRepository.findById(actorId).get();
            adRecruiter.getRecruitedActors().remove(actor);
            actor.getRecruitedBy().remove(adRecruiter);
            adRecruiterRepository.save(adRecruiter);
        }
    }
}
