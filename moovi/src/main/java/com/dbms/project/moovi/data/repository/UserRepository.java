package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.username=:username")
    Iterable<User> findUserByUsername(@Param("username") String u);

    @Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
    Iterable<User> findUserByCredentials(@Param("username") String username, @Param("password") String password);

}
