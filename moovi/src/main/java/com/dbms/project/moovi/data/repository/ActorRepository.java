package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.Actor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ActorRepository extends CrudRepository<Actor, Long> {
	
	@Query("SELECT a FROM Actor a WHERE a.actorId=:actorId")
    Iterable<Actor> findActorById(@Param("actorId") long a);
}
