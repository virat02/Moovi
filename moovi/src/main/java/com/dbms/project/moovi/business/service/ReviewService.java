package com.dbms.project.moovi.business.service;

import com.dbms.project.moovi.data.entity.Critic;
import com.dbms.project.moovi.data.entity.Movie;
import com.dbms.project.moovi.data.entity.Review;
import com.dbms.project.moovi.data.repository.CriticRepository;
import com.dbms.project.moovi.data.repository.MovieRepository;
import com.dbms.project.moovi.data.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CriticRepository criticRepository;

    @GetMapping("/api/review")
    public List<Review> findAllReview(){
        return (List<Review>) reviewRepository.findAll();
    }

    @PostMapping("/api/review")
    public Review createUser(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    @PostMapping("/api/reviews/review/{reviewId}/movie/{movieId}")
    public void reviwedMovie(
            @PathVariable("movieId") long movieId,
            @PathVariable("reviewId") long reviewId){
        if(movieRepository.findById(movieId).isPresent()
                && reviewRepository.findById(reviewId).isPresent()) {
            Movie movie = movieRepository.findById(movieId).get();
            Review review = reviewRepository.findById(reviewId).get();
            review.setRmovie(movie);
            reviewRepository.save(review);
        }
    }

    @PostMapping("/api/reviews/review/{reviewId}/critic/{username}/movie/{movieId}")
    public void reviewedByCritic(
            @PathVariable("username") String username,
            @PathVariable("reviewId") long reviewId,
            @PathVariable("movieId") long movieId){
        if(criticRepository.findById(criticRepository.findCriticIdByUsername(username)).isPresent()
                && reviewRepository.findById(reviewId).isPresent()
                && movieRepository.findById(movieId).isPresent()) {
            Critic critic = criticRepository.findById(criticRepository.findCriticIdByUsername(username)).get();
            Movie movie = movieRepository.findById(movieId).get();
            Review review = reviewRepository.findById(reviewId).get();
            review.setCritic(critic);
            review.setRmovie(movie);
            reviewRepository.save(review);
        }
    }

    @GetMapping("/api/review/{reviewId}/movie")
    public Movie getMovieByReview(@PathVariable("reviewId") long reviewId){
        if(reviewRepository.findById(reviewId).isPresent()){
            Review review = reviewRepository.findById(reviewId).get();
            return review.getRmovie();
        }
        return null;
    }
}
