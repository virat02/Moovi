package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.Fan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FanRepository extends CrudRepository<Fan, Long> {

    @Query("SELECT f FROM Fan f WHERE f.username=:username")
    Iterable<Fan> findFanByUsername(@Param("username") String u);

    @Query("SELECT f.userId FROM Fan f where f.username=:username")
    long findFanIdByUsername(@Param("username") String u);

    @Query("SELECT f FROM Fan f where f.username=:username and f.password=:password")
    Iterable<Fan> findFanByCredential(@Param("username") String username, @Param("password") String password);
}
