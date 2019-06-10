package com.dbms.project.moovi.data.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TheatreManager extends User {
	
	@OneToMany(mappedBy = "theatreManager", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Theatre> listOfTheatresManaged;
	
	public TheatreManager() {
		super();
	}

	public List<Theatre> getListOfTheatresManaged() {
		return listOfTheatresManaged;
	}

	public void setListOfTheatresManaged(List<Theatre> listOfTheatresManaged) {
		this.listOfTheatresManaged = listOfTheatresManaged;
	}

	public void managedTheatres(Theatre theatre){
		this.listOfTheatresManaged.add(theatre);
		if(theatre.getTheatreManager() != this)
			theatre.setTheatreManager(this);
	}

	public void set(TheatreManager newtheatreManager) {
		this.firstName = newtheatreManager.firstName != null? 
				newtheatreManager.firstName : this.firstName;
		this.lastName = newtheatreManager.lastName != null? 
				newtheatreManager.lastName : this.lastName;
		this.username = newtheatreManager.username != null? 
				newtheatreManager.username : this.username;
		this.password = newtheatreManager.password != null? 
				newtheatreManager.password : this.password;
		this.email = newtheatreManager.email != null? 
				newtheatreManager.email : this.email;
		this.dob = newtheatreManager.dob != null? 
				newtheatreManager.dob : this.dob;
	}
}

