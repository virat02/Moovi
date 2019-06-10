package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.TheatreManager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TheatreManagerRepository extends CrudRepository<TheatreManager, Long> {

    @Query("SELECT t FROM TheatreManager t WHERE t.username=:username")
    Iterable<TheatreManager> findManagerByUsername(@Param("username") String u);

    @Query("SELECT t.userId FROM TheatreManager t WHERE t.username=:username")
    long findManagerIdByUsername(@Param("username") String u);

    @Query("SELECT tm FROM TheatreManager tm where tm.username=:username and tm.password=:password")
    Iterable<TheatreManager> findManagerByCredential(@Param("username") String username, @Param("password") String password);
}
