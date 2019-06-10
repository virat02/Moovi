package com.dbms.project.moovi.data.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Theatre {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long theatreId;
	
	private String theatreName;
	//private Long totalScreens;
	private String location;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnore
	private TheatreManager theatreManager;
	
	@OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Screen> listOfScreens;

	public Theatre() {
	}

	public long getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(long theatreId) {
		this.theatreId = theatreId;
	}

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	/*public long getTotalScreens() {
		return totalScreens;
	}

	public void setTotalScreens(long totalScreens) {
		this.totalScreens = totalScreens;
	}*/

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public TheatreManager getTheatreManager() {
		return theatreManager;
	}

	public void setTheatreManager(TheatreManager theatreManager) {
		this.theatreManager = theatreManager;
		if(!theatreManager.getListOfTheatresManaged().contains(this))
			theatreManager.getListOfTheatresManaged().add(this);
	}

	public List<Screen> getListOfScreens() {
		return listOfScreens;
	}

	public void setListOfScreens(List<Screen> listOfScreens) {
		this.listOfScreens = listOfScreens;
	}

	public void set(Theatre newTheatre) {
		this.theatreName = newTheatre.theatreName != null?
				newTheatre.theatreName : this.theatreName;
		/*this.totalScreens = newTheatre.totalScreens != null?
				newTheatre.totalScreens : this.totalScreens;*/
		this.location = newTheatre.location != null?
				newTheatre.location : this.location;
	}
	
}
