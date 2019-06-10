package com.dbms.project.moovi.data.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

@Entity
public class Actor {

    @Id
    private long actorId;
    private String actorName;
    private String dob;
    private String dod;
    private String imdbId;

    @Column(columnDefinition = "TEXT")
    private String biography;
    private String actorPopularity;
    private String profilePicture;

    @ManyToMany(mappedBy="recruitedActors", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<AdRecruiter> recruitedBy;

    @ManyToMany(mappedBy="actorsFollowed", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Fan> fansFollowingActor;

    @ManyToMany(mappedBy="listOfActors", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Movie> listOfMovies;

    public List<Fan> getFansFollowingActor() {
        return fansFollowingActor;
    }

    public void setFansFollowingActor(List<Fan> fansFollowingActor) {
        this.fansFollowingActor = fansFollowingActor;
    }

    public List<AdRecruiter> getRecruitedBy() {
        return recruitedBy;
    }

    public void setRecruitedBy(List<AdRecruiter> recruitedBy) {
        this.recruitedBy = recruitedBy;
    }

    public List<Movie> getListOfMovies() {
        return listOfMovies;
    }

    public void setListOfMovies(List<Movie> listOfMovies) {
        this.listOfMovies = listOfMovies;
    }

    public Actor() {
        super();
    }

    public long getActorId() {
        return actorId;
    }

    public void setActorId(long actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDod() {
        return dod;
    }

    public void setDod(String dod) {
        this.dod = dod;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getActorPopularity() {
        return actorPopularity;
    }

    public void setActorPopularity(String actorPopularity) {
        this.actorPopularity = actorPopularity;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void actorRecruitedBy(AdRecruiter adRecruiter) {
        this.recruitedBy.add(adRecruiter);
        if(!adRecruiter.getRecruitedActors().contains(this)) {
            adRecruiter.getRecruitedActors().add(this);
        }
    }

    public void followedBy(Fan fan) {
        this.fansFollowingActor.add(fan);
        if(!fan.getActorsFollowed().contains(this)) {
            fan.getActorsFollowed().add(this);
        }
    }

    public void setActor(Actor actor){
        this.actorName = actor.actorName != null?
                actor.actorName : this.actorName;
        this.actorPopularity = actor.actorPopularity != null?
                actor.actorPopularity : this.actorPopularity;
        this.biography = actor.biography != null?
                actor.biography : this.biography;
        this.dob = actor.dob != null?
                actor.dob : this.dob;
        this.dod = actor.dod != null?
                actor.dod : this.dod;
        this.imdbId = actor.imdbId != null?
                actor.imdbId : this.imdbId;
        this.profilePicture = actor.profilePicture != null?
                actor.profilePicture : this.profilePicture;
    }
}
