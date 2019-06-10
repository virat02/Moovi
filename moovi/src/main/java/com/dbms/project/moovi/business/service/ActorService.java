package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.Actor;
import com.dbms.project.moovi.data.entity.AdRecruiter;
import com.dbms.project.moovi.data.entity.Fan;

import com.dbms.project.moovi.data.entity.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dbms.project.moovi.data.repository.ActorRepository;
import com.dbms.project.moovi.data.repository.AdRecruiterRepository;
import com.dbms.project.moovi.data.repository.FanRepository;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@RestController
@CrossOrigin
public class ActorService extends Utils{

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private AdRecruiterRepository adRecruiterRepository;

    @Autowired
    private FanRepository fanRepository;

    @PostMapping("/api/actor")
    public Actor createActor(@RequestBody Actor actor) {
        return actorRepository.save(actor);
    }

    @GetMapping("/api/actor")
    public List<Actor> findAllActors(){
        return (List<Actor>) actorRepository.findAll();
    }

    @GetMapping("/api/actor/{actorId}")
    public Actor findActorById(@PathVariable(name = "actorId") long actorId) {
        if(actorRepository.findById(actorId).isPresent()){
            return actorRepository.findById(actorId).get();
        }
        return null;
    }

    @PostMapping("/api/recruit/actor/{actorId}/adrecruiter/{username}")
    public void AdRecruiterRecruitsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()
                && adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            AdRecruiter adRecruiter = adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).get();
            actor.actorRecruitedBy(adRecruiter);
            actorRepository.save(actor);
        }
    }

    @PostMapping("/api/follow/actor/{actorId}/fan/{username}")
    public void FanFollowsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            actor.followedBy(fan);
            actorRepository.save(actor);
        }
    }

    @GetMapping("/api/follow/actor/{actorId}/fanfollowing")
    public List<Fan> listOfFansFollowing(
            @PathVariable("actorId") long actorId) {
        if(actorRepository.findById(actorId).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            return actor.getFansFollowingActor();
        }
        return null;
    }

    @GetMapping("/api/search/actor")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public JSONArray getActor(
            @RequestParam(value = "actorName", required = false) String actorName){
                String searchUrlString;
        if(actorName != null)
            searchUrlString = "https://api.themoviedb.org/3/search/person?api_key="+apiKey+"&query="+actorName.replace(" ","+");
        else
            searchUrlString = "https://api.themoviedb.org/3/person/popular?api_key="+apiKey;
        JSONObject jsonObject;
        JSONArray jsonArray = new JSONArray();

        try {
            URL url = new URL(searchUrlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if(responseCode != 200)
                throw new RuntimeException("HttpResponseCode from Actor Service: " + responseCode);
            else{
                StringBuilder inline = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext())
                {
                    inline.append(sc.nextLine());
                }
                sc.close();
                JSONParser parse = new JSONParser();
                JSONObject jsonObject1 = (JSONObject) parse.parse(inline.toString());
                JSONArray results = (JSONArray) jsonObject1.get("results");
                long[] idArray = new long[results.size()];
                for(int i=0;i<results.size();i++)
                {
                    JSONObject jsonObject2 = (JSONObject)results.get(i);
                    idArray[i] = (long) jsonObject2.get("id");
                }
                Actor actor = new Actor();
                for(long actorId: idArray){
                    String getActor = "https://api.themoviedb.org/3/person/"+actorId+"?api_key="+apiKey+"&language=en-US";
                    URL url1 = new URL(getActor);
                    HttpURLConnection connection1 = (HttpURLConnection) url.openConnection();
                    connection1.setRequestMethod("GET");
                    connection1.connect();
                    inline = new StringBuilder();
                    Scanner scanner = new Scanner(url1.openStream());
                    while(scanner.hasNext())
                    {
                        inline.append(scanner.nextLine());
                    }
                    scanner.close();
                    jsonObject = (JSONObject) parse.parse(inline.toString());
                    JSONObject object = new JSONObject();
                    if(jsonObject.get("birthday") == null)
                        object.put("dob","-");
                    else
                        object.put("dob", jsonObject.get("birthday"));
                    if(jsonObject.get("deathday") == null)
                        object.put("dod","-");
                    else
                        object.put("dod", jsonObject.get("deathday"));
                    if(jsonObject.get("imdb_id") == null)
                        object.put("imdbId","-");
                    else
                        object.put("imdbId", jsonObject.get("imdb_id"));
                    if(jsonObject.get("biography") == null)
                        object.put("biography","-");
                    else
                        object.put("biography", jsonObject.get("biography"));
                    if(jsonObject.get("popularity") == null)
                        object.put("actorPopularity","-");
                    else
                        object.put("actorPopularity", jsonObject.get("popularity"));
                    if (jsonObject.get("profile_path") == null)
                        object.put("profilePicture","-");
                    else
                        object.put("profilePicture",imgUrl+jsonObject.get("profile_path").toString());

                    object.put("actorName",jsonObject.get("name"));
                    object.put("actorId", jsonObject.get("id"));

                    actor.setActorId((Long) object.get("actorId"));
                    actor.setActorName((String) object.get("actorName"));
                    actor.setProfilePicture((String) object.get("profilePicture"));
                    actor.setActorPopularity(object.get("actorPopularity").toString());
                    actor.setBiography((String) object.get("biography"));
                    actor.setDob((String) object.get("dob"));
                    actor.setDod((String) object.get("dod"));
                    actor.setImdbId((String) object.get("imdbId"));

                    actorRepository.save(actor);

                    jsonArray.add(object);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @GetMapping("/api/recruit/actor/{actorId}/recruitedby")
    public List<AdRecruiter> getListOfAdrecruiters(
            @PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            return actor.getRecruitedBy();
        }
        return null;
    }

    @GetMapping("/api/actor/{actorId}/moviesActed")
    public List<Movie> getMoviesActed (@PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()){
            Actor actor = actorRepository.findById(actorId).get();
            return actor.getListOfMovies();
        }
        return null;
    }

    @GetMapping("/api/check/follow/fan/{username}/actor/{actorId}")
    public Fan checkIfFanFollowsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId) {
        if(actorRepository.findById(actorId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            List<Fan> fansWhoFollowActor = actor.getFansFollowingActor();

            if(fansWhoFollowActor.contains(fan))
            {
                return fan;
            }
        }

        return null;
    }

    @GetMapping("/api/check/recruit/adrecruiter/{username}/actor/{actorId}")
    public AdRecruiter checkIfAdrecruiterRecruitsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId) {
        if(actorRepository.findById(actorId).isPresent()
                && adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            AdRecruiter adrecruiter = adRecruiterRepository.findById(adRecruiterRepository.findAdRecruiterIdByUsername(username)).get();
            List<AdRecruiter> recruitersWhoRecruitedActor = actor.getRecruitedBy();

            if(recruitersWhoRecruitedActor.contains(adrecruiter))
            {
                return adrecruiter;
            }
        }

        return null;
    }

}
