package com.dbms.project.moovi.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity
public class AdRecruiter extends User{

    private String recruiterDescription;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="Recruit",
    joinColumns= @JoinColumn(name="recruiter_id", referencedColumnName="userId"),
    inverseJoinColumns= @JoinColumn(name= "actor_id", referencedColumnName="actorId"))
    @JsonIgnore
    private List<Actor> recruitedActors;

    public AdRecruiter() {
        super();
    }

    public String getRecruiterDescription() {
        return recruiterDescription;
    }

    public void setRecruiterDescription(String recruiterDescription) {
        this.recruiterDescription = recruiterDescription;
    }

    public List<Actor> getRecruitedActors() {
        return recruitedActors;
    }

    public void setRecruitedActors(List<Actor> recruitedActors) {
        this.recruitedActors = recruitedActors;
    }

	public void recruitsActor(Actor actor) {
		this.recruitedActors.add(actor);
		if(!actor.getRecruitedBy().contains(this)) {
			actor.getRecruitedBy().add(this);
		}	
		
	}

	public void set(AdRecruiter newAdrecruiter) {
		this.firstName = newAdrecruiter.firstName != null? 
				newAdrecruiter.firstName : this.firstName;
		this.lastName = newAdrecruiter.lastName != null? 
				newAdrecruiter.lastName : this.lastName;
		this.username = newAdrecruiter.username != null? 
				newAdrecruiter.username : this.username;
		this.password = newAdrecruiter.password != null? 
				newAdrecruiter.password : this.password;
		this.email = newAdrecruiter.email != null? 
				newAdrecruiter.email : this.email;
		this.dob = newAdrecruiter.dob != null? 
				newAdrecruiter.dob : this.dob;
		this.recruiterDescription = newAdrecruiter.recruiterDescription != null?
				newAdrecruiter.recruiterDescription : this.recruiterDescription;
	}
}
