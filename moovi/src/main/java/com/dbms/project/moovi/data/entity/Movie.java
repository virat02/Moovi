package com.dbms.project.moovi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Movie {

    @Id
    private long movieId;
    private String movieName;
    private String imdbId;
    private String posterSRC;
    private long runtime;
    private double imdbRating;
    private String releaseDate;
    private long revenue;
    private String releaseStatus;
    
    @Column(columnDefinition = "TEXT")
    private String overview;

    @ManyToMany(mappedBy = "recommendedMovies", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Critic> recommendedBy;

    @ManyToMany(mappedBy = "likesMovies", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fan> likedByFans;

    @ManyToMany(mappedBy = "dislikesMovies", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fan> dislikedByFans;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MovieCast",
            joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "movieId"),
            inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actorId"))
    @JsonIgnore
    private List<Actor> listOfActors;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Genre> listOfGenres;

    @OneToMany(mappedBy = "rmovie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> movieReview;
    
    @OneToMany(mappedBy = "screenHasMovie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Screen> moviePlayingInScreen;
    
	public List<Review> getMovieReview() {
		return movieReview;
	}

	public void setMovieReview(List<Review> movieReview) {
		this.movieReview = movieReview;
	}

	public List<Screen> getMoviePlayingInScreen() {
		return moviePlayingInScreen;
	}

	public void setMoviePlayingInScreen(List<Screen> moviePlayingInScreen) {
		this.moviePlayingInScreen = moviePlayingInScreen;
	}

	public List<Critic> getRecommendedBy() {
		return recommendedBy;
	}

	public void setRecommendedBy(List<Critic> recommendedBy) {
		this.recommendedBy = recommendedBy;
	}

	public List<Fan> getLikedByFans() {
		return likedByFans;
	}

	public void setLikedByFans(List<Fan> likedByFans) {
		this.likedByFans = likedByFans;
	}

	public List<Fan> getDislikedByFans() {
		return dislikedByFans;
	}

	public void setDislikedByFans(List<Fan> dislikedByFans) {
		this.dislikedByFans = dislikedByFans;
	}

	public List<Actor> getListOfActors() {
		return listOfActors;
	}

	public void setListOfActors(List<Actor> listOfActors) {
		this.listOfActors = listOfActors;
	}

    public List<Genre> getListOfGenres() {
		return listOfGenres;
	}

	public void setListOfGenres(List<Genre> listOfGenres) {
		this.listOfGenres = listOfGenres;
	}

	public Movie() {
        super();
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String name) {
        this.movieName = name;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterSRC() {
        return posterSRC;
    }

    public void setPosterSRC(String posterSRC) {
        this.posterSRC = posterSRC;
    }

    public long getRuntime() {
        return runtime;
    }

    public void setRuntime(long runtime) {
        this.runtime = runtime;
    }

    public double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }
    
    public void likedByFan(Fan fan) {
		this.likedByFans.add(fan);
		if(!fan.getLikesMovies().contains(this)) {
			fan.getLikesMovies().add(this);
		}
    }

    public void dislikedByFan(Fan fan) {
        this.dislikedByFans.add(fan);
        if(!fan.getDislikesMovies().contains(this)) {
            fan.getDislikesMovies().add(this);
        }
    }
    
    public void recommendedByCritic(Critic critic) {
        this.recommendedBy.add(critic);
        if(!critic.getRecommendedMovies().contains(this)) {
            critic.getRecommendedMovies().add(this);
        }
    }
    
    public void screenedIn (Screen screen) {
    		this.moviePlayingInScreen.add(screen);
    		if(screen.getScreenHasMovie() != this) {
    			screen.setScreenHasMovie(this);
    		}
    }
   
    public void hasReviews(Review review) {
        	this.movieReview.add(review);
        	if(review.getRmovie() != this) {
        		review.setRmovie(this);
        }
    }
    
    public void hasGenres (Genre genre) {
    	this.listOfGenres.add(genre);
    	if(genre.getMovie() != this) {
    		genre.setMovie(this);
        }
    }

    public void castActors(Actor actor){
	    this.listOfActors.add(actor);
	    if(!actor.getListOfMovies().contains(this))
	        actor.getListOfMovies().add(this);
    }

    public void setMovie(Movie newMovie){
	    this.movieName = newMovie.movieName != null?
                newMovie.movieName : this.movieName;
	    this.imdbId = newMovie.imdbId != null?
                newMovie.imdbId : this.imdbId;
	    this.imdbRating = newMovie.imdbRating >= 0?
                newMovie.imdbRating : this.imdbRating;
	    this.overview = newMovie.overview != null?
                newMovie.overview : this.overview;
	    this.posterSRC = newMovie.posterSRC != null?
                newMovie.posterSRC : this.posterSRC;
	    this.releaseDate = newMovie.releaseDate != null?
                newMovie.releaseDate : this.releaseDate;
	    this.releaseStatus = newMovie.releaseStatus != null?
                newMovie.releaseStatus : this.releaseStatus;
	    this.revenue = newMovie.revenue >= 0?
                newMovie.revenue : this.revenue;
	    this.runtime = newMovie.runtime >= 0?
                newMovie.runtime : this.runtime;
    }
}
