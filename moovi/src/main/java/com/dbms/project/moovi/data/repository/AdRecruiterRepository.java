package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.AdRecruiter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdRecruiterRepository extends CrudRepository<AdRecruiter, Long>{

    @Query("SELECT a FROM AdRecruiter a WHERE a.username=:username")
    Iterable<AdRecruiter> findAdRecruiterByUsername(@Param("username") String u);

    @Query("SELECT a.userId FROM AdRecruiter a WHERE a.username=:username")
    long findAdRecruiterIdByUsername(@Param("username") String u);

    @Query("SELECT a FROM AdRecruiter a WHERE a.username=:username AND a.password=:password")
    Iterable<AdRecruiter> findAdRecruiterByCredential(@Param("username") String username,
                                                      @Param("password") String password);
}
