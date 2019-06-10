package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.Actor;
import com.dbms.project.moovi.data.entity.Critic;
import com.dbms.project.moovi.data.entity.Fan;
import com.dbms.project.moovi.data.entity.Movie;
import com.dbms.project.moovi.data.repository.ActorRepository;
import com.dbms.project.moovi.data.repository.CriticRepository;
import com.dbms.project.moovi.data.repository.FanRepository;
import com.dbms.project.moovi.data.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FanService extends Utils {

    @Autowired
    private FanRepository fanRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private CriticRepository criticRepository;

    @PostMapping("/api/fan")
    public Fan createFan(@RequestBody Fan fan) {
        return fanRepository.save(fan);
    }

    @GetMapping("/api/fan")
    public List<Fan> findAllFans(@RequestParam(name = "username", required = false) String username,
                                 @RequestParam(name = "password", required = false) String password) {
        if (username != null && password != null)
            return (List<Fan>) fanRepository.findFanByCredential(username, password);
        return (List<Fan>) fanRepository.findAll();
    }

    @PostMapping("api/like/fan/{username}/movie/{movieId}")
    public void fanLikesMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            fan.likesMovie(movie);
            fanRepository.save(fan);
        }
    }

    @PostMapping("api/dislike/fan/{username}/movie/{movieId}")
    public void fanDislikesMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            fan.dislikesMovie(movie);
            fanRepository.save(fan);
        }
    }

    @PostMapping("api/follow/fan/{username}/actor/{actorId}")
    public void fanFollowsActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId){
        if(actorRepository.findById(actorId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Actor actor = actorRepository.findById(actorId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            fan.followsActor(actor);
            fanRepository.save(fan);
        }
    }

    @PostMapping("api/follow/fan/{FanUsername}/critic/{CriticUsername}")
    public void fanFollowsCritic(
            @PathVariable("FanUsername") String fan_username,
            @PathVariable("CriticUsername") String critic_username){
        if(criticRepository.findById(criticRepository.findCriticIdByUsername(critic_username)).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(fan_username)).isPresent()) {
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(critic_username)).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(fan_username)).get();
            fan.followsCritic(critic);
            fanRepository.save(fan);
        }
    }

    @GetMapping("api/follow/fan/{username}/actorfollowed")
    public List<Actor> listOfActorFollowed(
            @PathVariable("username") String username){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            return fan.getActorsFollowed();
        }
        return null;
    }

    @GetMapping("api/follow/fan/{username}/criticfollowed")
    public List<Critic> listOfCriticFollowed(
            @PathVariable("username") String username){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            return fan.getCriticsFollowed();
        }
        return null;
    }

    @GetMapping("api/like/fan/{username}/moviesliked")
    public List<Movie> listOfMoviesLiked(
            @PathVariable("username") String username){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            return fan.getLikesMovies();
        }
        return null;
    }

    @GetMapping("api/dislike/fan/{username}/moviesdisliked")
    public List<Movie> listOfMoviesDisliked(
            @PathVariable("username") String username){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            return fan.getDislikesMovies();
        }
        return null;
    }

    @PostMapping("/api/follow/fan1/{username1}/fan2/{username2}")
    public void fansFollowingfan(
            @PathVariable(name = "username1") String username1,
            @PathVariable(name = "username2" ) String username2){
        if (fanRepository.findById(fanRepository.findFanIdByUsername(username1)).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username2)).isPresent()){
            Fan fan1 = fanRepository.findById(fanRepository.findFanIdByUsername(username1)).get();
            Fan fan2 = fanRepository.findById(fanRepository.findFanIdByUsername(username2)).get();
            fan1.followsFan(fan2);
            fanRepository.save(fan1);
        }
    }

    @GetMapping("/api/check/follow/fan1/{username1}/fan2/{username2}")
    public Fan checkIfFanFollowsAnotherFan(
            @PathVariable("username1") String username1,
            @PathVariable("username2") String username2) {
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username1)).isPresent() &&
                fanRepository.findById(fanRepository.findFanIdByUsername(username2)).isPresent()) {
            Fan fan1 = fanRepository.findById(fanRepository.findFanIdByUsername(username1)).get();
            Fan fan2 = fanRepository.findById(fanRepository.findFanIdByUsername(username2)).get();
            List <Fan> fan2list = fan2.getFollowedByFans();
            if (fan2list.contains(fan1) && !(fan1.getUsername().equals(fan2.getUsername()))) {
                return fan1;
            }
        }

        return null;
    }

    @GetMapping("/api/follow/fan/{username}/fansfollowing")
    public List<Fan> getListOfFansFollowing(
            @PathVariable("username") String username){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            return fan.getFollowingFans();
        }
        return null;
    }

    @GetMapping("/api/follow/fan/{username}/followedby")
    public List<Fan> getListOfFollowedByFans(@PathVariable("username") String username) {
        if (fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            return fan.getFollowedByFans();
        }
        return null;
    }

    @PostMapping("/api/delete/unfollow/fan/{username}/actor/{actorId}")
    public Fan unfollowActor(
            @PathVariable("username") String username,
            @PathVariable("actorId") long actorId){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()
                && actorRepository.findById(actorId).isPresent()){
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            Actor actor = actorRepository.findById(actorId).get();
            fan.getActorsFollowed().remove(actor);
            return fanRepository.save(fan);
        }
        return null;
    }

    @PostMapping("/api/delete/dislike/fan/{username}/movie/{movieId}")
    public Fan undoDislike(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()
                && movieRepository.findById(movieId).isPresent()){
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            Movie movie = movieRepository.findById(movieId).get();
            fan.getDislikesMovies().remove(movie);
            return fanRepository.save(fan);
        }
        return null;
    }

    @PostMapping("/api/delete/like/fan/{username}/movie/{movieId}")
    public Fan undoLike(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()
                && movieRepository.findById(movieId).isPresent()){
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            Movie movie = movieRepository.findById(movieId).get();
            fan.getLikesMovies().remove(movie);
            return fanRepository.save(fan);
        }
        return null;
    }

    @PostMapping("/api/delete/unfollow/fan/{username}/critic/{username1}")
    public Fan unfollowCritic(
            @PathVariable("username") String username,
            @PathVariable("username1") String username1){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()
                && criticRepository.findById(criticRepository.findCriticIdByUsername(username1)).isPresent()){
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username1)).get();
            fan.getCriticsFollowed().remove(critic);
            return fanRepository.save(fan);
        }
        return null;
    }

    @PostMapping("/api/delete/following/fanfollowing/{username1}/fanfollowed/{username2}")
    public Fan unfollowFan(
            @PathVariable("username1") String username1,
            @PathVariable("username2") String username2){
        if(fanRepository.findById(fanRepository.findFanIdByUsername(username1)).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username2)).isPresent()){
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username1)).get();
            Fan fan1 = fanRepository.findById(fanRepository.findFanIdByUsername(username2)).get();
            fan.getFollowingFans().remove(fan1);
            return fanRepository.save(fan);
        }
        return null;
    }
}
