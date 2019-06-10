package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.Movie;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends CrudRepository<Movie, Long>{
	
	@Query("SELECT m FROM Movie m WHERE m.movieId=:movieId")
    Iterable<Movie> findMovieById(@Param("movieId") long m);

	@Query("SELECT m.movieName FROM Movie m WHERE m.movieId=:movieId")
    String findMovieNameById(@Param("movieId") long movieId);
}
