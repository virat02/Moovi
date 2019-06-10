package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.Theatre;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TheatreRepository extends CrudRepository<Theatre, Long>{

    @Query("SELECT t FROM Theatre t WHERE t.theatreId=:theatreId")
    Iterable<Theatre> findTheatreById(@Param("theatreId") long t);
}
