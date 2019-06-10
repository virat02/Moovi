package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.Actor;
import com.dbms.project.moovi.data.entity.Critic;
import com.dbms.project.moovi.data.entity.Fan;
import com.dbms.project.moovi.data.entity.Movie;
import com.dbms.project.moovi.data.entity.Review;
import com.dbms.project.moovi.data.repository.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@RestController
@CrossOrigin
public class MovieService extends Utils {

    @Autowired
    private MovieRepository movieRepository;
    
    @Autowired
    private FanRepository fanRepository;
    
    @Autowired
    private CriticRepository criticRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("/api/movie")
    public List<Movie> findAllMovie(){
        return (List<Movie>) movieRepository.findAll();
    }

    @GetMapping("/api/movie/{movieId}")
    public Movie findMovieFromMovieId(@PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()){
            return movieRepository.findById(movieId).get();
        }
        return null;
    }

    @PostMapping("/api/movie")
    public Movie createMovie(@RequestBody Movie movie){
        return movieRepository.save(movie);
    }

    @GetMapping("/api/search/movie")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public JSONArray getMovies(@RequestParam(name = "movieName",required = false) String movieName,
                               @RequestParam(name = "nowPlaying",required = false,defaultValue = "false") Boolean nowPlaying,
                               @RequestParam(name = "getTopRated", required = false, defaultValue = "false") Boolean getTopRated) {
        String searchUrlString;
        if ((movieName != null)&&(!nowPlaying)) {
            searchUrlString = "https://api.themoviedb.org/3/search/movie?api_key="+apiKey+"&query=" + movieName.replace(" ","+");
        }
        else if ((getTopRated)&&(!nowPlaying))
            searchUrlString = "https://api.themoviedb.org/3/movie/top_rated?api_key="+apiKey+"&language=en-US&page=1";
        else if (nowPlaying)
            searchUrlString = "https://api.themoviedb.org/3/movie/now_playing?api_key="+apiKey+"&language=en-US&page=1";
        else
            searchUrlString = "https://api.themoviedb.org/3/movie/upcoming?api_key=" +apiKey+"&language=en-US&page=1";
        JSONObject jobj1;
        JSONArray jsonArray = new JSONArray();
        try {
            URL searchMovieUrl = new URL(searchUrlString);
            HttpURLConnection conn = (HttpURLConnection)searchMovieUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responseCode = conn.getResponseCode();
            if(responseCode != 200)
                throw new RuntimeException("HttpResponseCode: " +responseCode);
            else
            {
                StringBuilder inline = new StringBuilder();
                Scanner sc = new Scanner(searchMovieUrl.openStream());
                while(sc.hasNext())
                {
                    inline.append(sc.nextLine());
                }
                sc.close();
                JSONParser parse = new JSONParser();
                JSONObject jobj = (JSONObject)parse.parse(inline.toString());
                JSONArray jsonarr_1 = (JSONArray) jobj.get("results");
                long[] idArray;
                int n= jsonarr_1.size();
                if(n>5){
                    idArray = new long[5];
                }
                else
                    idArray = new long[jsonarr_1.size()];

                //Get data for Results array
                for(int i=0;i<idArray.length;i++)
                {
                    //Store the JSON objects in an array
                    //Get the index of the JSON object and print the values as per the index
                    JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
                    //Store the JSON object in JSON array as objects (For level 2 array element i.e Address Components)
                    //JSONArray jsonarr_2 = (JSONArray) jsonobj_1.get("address_components");
                    //System.out.println("Elements under results array");

                    idArray[i] = (long) jsonobj_1.get("id");
                    System.out.println("\nMovie id: " +jsonobj_1.get("id"));
                    System.out.println("Movie name: " +jsonobj_1.get("original_title"));
                    System.out.println("IMDb Rating: "+jsonobj_1.get("vote_average"));
                    //Get data for the Address Components array
//                    System.out.println("Elements under address_components array");
//                    System.out.println("The long names, short names and types are:");
//                    for(int j=0;j<jsonarr_2.size();j++)
//                    {
//                        //Same just store the JSON objects in an array
//                        //Get the index of the JSON objects and print the values as per the index
//                        JSONObject jsonobj_2 = (JSONObject) jsonarr_2.get(j);
//                        //Store the data as String objects
//                        String str_data1 = (String) jsonobj_2.get("long_name");
//                        System.out.println(str_data1);
//                        String str_data2 = (String) jsonobj_2.get("short_name");
//                        System.out.println(str_data2);
//                        System.out.println(jsonobj_2.get("types"));
//                        System.out.println("\n");
//                    }
                }

                Movie movie = new Movie();
                for (long movieId:idArray) {
                    String findMovieUrlString = "https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+apiKey+"&language=en-US";

                    URL findMovieUrl = new URL(findMovieUrlString);
                    HttpURLConnection conn1 = (HttpURLConnection)findMovieUrl.openConnection();
                    conn1.setRequestMethod("GET");
                    conn1.connect();

                    inline = new StringBuilder();
                    Scanner scanner = new Scanner(findMovieUrl.openStream());
                    while(scanner.hasNext())
                    {
                        inline.append(scanner.nextLine());
                    }

                    scanner.close();
                    jobj1 = (JSONObject)parse.parse(inline.toString());
                    JSONObject jsonObject = new JSONObject();
                    long l = 0;

                    if (jobj1.get("title") == null)
                        jsonObject.put("movieName","-");
                    else
                        jsonObject.put("movieName",jobj1.get("title"));
                    if (jobj1.get("imdb_id") == null)
                        jsonObject.put("imdbId","-");
                    else
                        jsonObject.put("imdbId",jobj1.get("imdb_id"));
                    if ((Long) jobj1.get("revenue") == 0)
                        jsonObject.put("revenue",l);
                    else
                        jsonObject.put("revenue",jobj1.get("revenue"));
                    if (jobj1.get("runtime") == null)
                        jsonObject.put("runtime",l);
                    else
                        jsonObject.put("runtime",jobj1.get("runtime"));
                    if (jobj1.get("overview") == "")
                        jsonObject.put("overview","-");
                    else
                        jsonObject.put("overview",jobj1.get("overview"));
                    if (jobj1.get("poster_path") == null)
                        jsonObject.put("posterSRC","-");
                    else
                        jsonObject.put("posterSRC",imgUrl+jobj1.get("poster_path").toString());
                    jsonObject.put("movieId",jobj1.get("id"));
                    jsonObject.put("imdbRating",jobj1.get("vote_average"));
                    jsonObject.put("releaseDate",jobj1.get("release_date"));
                    jsonObject.put("releaseStatus",jobj1.get("status"));

                    //Movie Saving to Database Code

                    movie.setMovieId((Long) jsonObject.get("movieId"));
                    movie.setMovieName((String) jsonObject.get("movieName"));
                    movie.setImdbId((String) jsonObject.get("imdbId"));
                    movie.setOverview((String) jsonObject.get("overview"));
                    movie.setPosterSRC((String) jsonObject.get("posterSRC"));
                    movie.setRuntime((Long) jsonObject.get("runtime"));
                    movie.setImdbRating((Double) jsonObject.get("imdbRating"));
                    movie.setReleaseDate((String) jsonObject.get("releaseDate"));
                    movie.setRevenue((Long) jsonObject.get("revenue"));
                    movie.setReleaseStatus((String) jsonObject.get("releaseStatus"));
                    movieRepository.save(movie);

                    String findMovieCreditUrlString = "https://api.themoviedb.org/3/movie/"+movieId+"/credits?api_key="+apiKey+"&language=en-US";

                    URL findMovieCreditUrl = new URL(findMovieCreditUrlString);
                    HttpURLConnection conn2 = (HttpURLConnection)findMovieCreditUrl.openConnection();
                    conn2.setRequestMethod("GET");
                    conn2.connect();

                    inline = new StringBuilder();
                    Scanner scanner1 = new Scanner(findMovieCreditUrl.openStream());
                    while(scanner1.hasNext())
                        inline.append(scanner1.nextLine());
                    scanner1.close();
                    JSONObject jsonObject1 = (JSONObject) parse.parse(inline.toString());
                    System.out.println(inline);

                    JSONArray array = (JSONArray) jsonObject1.get("cast");
                    int t = array.size();
                    long[] idArray1;
                    if(t > 5)
                        idArray1 = new long[5];
                    else
                        idArray1 = new long[array.size()];

                    Actor actor = new Actor();

                    for(int i = 0; i < idArray1.length; i++){
                        JSONObject jsonObject2 = (JSONObject) array.get(i);
                        long actorId = (long) jsonObject2.get("id");
                        String getActor = "https://api.themoviedb.org/3/person/"+actorId+"?api_key="+apiKey+"&language=en-US";
                        URL url = new URL(getActor);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.connect();
                        inline = new StringBuilder();
                        Scanner scanner2 = new Scanner(url.openStream());
                        while (scanner2.hasNext())
                            inline.append(scanner2.nextLine());
                        scanner2.close();
                        JSONObject jsonObject3 = (JSONObject) parse.parse(inline.toString());
                        JSONObject object = new JSONObject();

                        if(jsonObject3.get("birthday") == null)
                            object.put("dob","-");
                        else
                            object.put("dob", jsonObject3.get("birthday"));
                        if(jsonObject3.get("deathday") == null)
                            object.put("dod","-");
                        else
                            object.put("dod", jsonObject3.get("deathday"));
                        if(jsonObject3.get("imdb_id") == null)
                            object.put("imdbId","-");
                        else
                            object.put("imdbId", jsonObject3.get("imdb_id"));
                        if(jsonObject3.get("biography") == null)
                            object.put("biography","-");
                        else
                            object.put("biography", jsonObject3.get("biography"));
                        if(jsonObject3.get("popularity") == null)
                            object.put("actorPopularity","-");
                        else
                            object.put("actorPopularity", jsonObject3.get("popularity"));
                        if (jsonObject3.get("profile_path") == null)
                            object.put("profilePicture","-");
                        else
                            object.put("profilePicture",imgUrl+jsonObject3.get("profile_path").toString());

                        object.put("actorName",jsonObject3.get("name"));
                        object.put("actorId", jsonObject3.get("id"));

                        actor.setActorId((Long) object.get("actorId"));
                        actor.setActorName((String) object.get("actorName"));
                        actor.setProfilePicture((String) object.get("profilePicture"));
                        actor.setActorPopularity(object.get("actorPopularity").toString());
                        actor.setBiography((String) object.get("biography"));
                        actor.setDob((String) object.get("dob"));
                        actor.setDod((String) object.get("dod"));
                        actor.setImdbId((String) object.get("imdbId"));

                        actorRepository.save(actor);

                        String actorToMovieMapping = "http://localhost:8080/api/cast/movie/"+movieId+"/actor/"+actorId;
                        URL url1 = new URL(actorToMovieMapping);

                        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
                        connection.setRequestMethod("POST");
                        connection.connect();
                        int responseCode3 = connection.getResponseCode();
                        System.out.println("response code: "+responseCode3);
                        if(responseCode3 != 200)
                            throw new RuntimeException("HttpResponseCode: " +responseCode);
                    }
                    jsonArray.add(jsonObject);
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @PostMapping("/api/like/movie/{movieId}/fan/{username}")
    public void likeMovie(
    		@PathVariable("username") String username,
    		@PathVariable("movieId") long movieId) {
    	if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            movie.likedByFan(fan);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/check/like/fan/{username}/movie/{movieId}")
    public Fan checkIfFanLikesMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            List<Fan> fansWhoLike = movie.getLikedByFans();

            if(fansWhoLike.contains(fan))
            {
                return fan;
            }
        }

        return null;
    }

    @PostMapping("/api/cast/movie/{movieId}/actor/{actorId}")
    public void castActor(
            @PathVariable("movieId") long movieId,
            @PathVariable("actorId") long actorId){
        if(movieRepository.findById(movieId).isPresent()
                && actorRepository.findById(actorId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            Actor actor = actorRepository.findById(actorId).get();
            movie.castActors(actor);
            movieRepository.save(movie);
        }
    }

    @PostMapping("/api/dislike/movie/{movieId}/fan/{username}")
    public void dislikeMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            movie.dislikedByFan(fan);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/check/dislike/fan/{username}/movie/{movieId}")
    public Fan checkIfFanDislikesMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && fanRepository.findById(fanRepository.findFanIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Fan fan = fanRepository.findById(fanRepository.findFanIdByUsername(username)).get();
            List<Fan> fansWhoDisliked = movie.getDislikedByFans();

            if(fansWhoDisliked.contains(fan))
            {
                return fan;
            }
        }

        return null;
    }

    @PostMapping("/api/recommend/movie/{movieId}/critic/{username}")
    public void recommendMovie(
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username)).get();
            movie.recommendedByCritic(critic);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/check/recommend/critic/{username}/movie/{movieId}")
    public Critic checkIfCriticRecommendsMovie (
            @PathVariable("username") String username,
            @PathVariable("movieId") long movieId) {
        if(movieRepository.findById(movieId).isPresent()
                && criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username)).get();
            List<Critic> criticsWhoRecommended = movie.getRecommendedBy();

            if(criticsWhoRecommended.contains(critic))
            {
                return critic;
            }
        }

        return null;
    }

    @PostMapping("/api/reviews/movie/{movieId}/review/{reviewId}")
    public void reviewMovie(
            @PathVariable("movieId") long movieId,
            @PathVariable("reviewId") long reviewId) {
        if(movieRepository.findById(movieId).isPresent()
                && reviewRepository.findById(reviewId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Review review = reviewRepository.findById(reviewId).get();
            movie.hasReviews(review);
            movieRepository.save(movie);
        }
    }

    @GetMapping("/api/recommend/movie/{movieId}/recommendedby")
    public List<Critic> listOfCriticsRecommended(
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getRecommendedBy();
        }
        return null;
    }

    @GetMapping("/api/like/movie/{movieId}/likedbyfans")
    public List<Fan> listOfFansLikedMovie(
            @PathVariable("movieID") long movieId){
        if(movieRepository.findById(movieId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getLikedByFans();
        }
        return null;
    }

    @GetMapping("/api/dislike/movie/{movieId}/dislikedbyfans")
    public List<Fan> listOfFansDislikedMovie(
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getDislikedByFans();
        }
        return null;
    }

    @GetMapping("/api/movie/{movieId}/reviews")
    public List<Review> listOfReviews(
            @PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getMovieReview();
        }
        return null;
    }

    @GetMapping("/api/movie/{movieId}/cast")
    public List<Actor> getMovieCast (@PathVariable("movieId") long movieId){
        if(movieRepository.findById(movieId).isPresent()){
            Movie movie = movieRepository.findById(movieId).get();
            return movie.getListOfActors();
        }
        return null;
    }
}
