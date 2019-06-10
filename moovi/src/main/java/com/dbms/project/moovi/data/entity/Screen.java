package com.dbms.project.moovi.data.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Screen {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long screenId;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Movie screenHasMovie;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private Theatre theatre;

	public Screen() {
		super();
	}

	public long getScreenId() {
		return screenId;
	}

	public void setScreenId(long screenId) {
		this.screenId = screenId;
	}

	public Movie getScreenHasMovie() {
		return screenHasMovie;
	}

	public void setScreenHasMovie(Movie screenHasMovie) {
		this.screenHasMovie = screenHasMovie;
		if(!screenHasMovie.getMoviePlayingInScreen().contains(this)) {
			screenHasMovie.getMoviePlayingInScreen().add(this);
		}
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
		if(!theatre.getListOfScreens().contains(this)){
			theatre.getListOfScreens().add(this);
		}
	}

}
