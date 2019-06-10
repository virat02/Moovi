package com.dbms.project.moovi.data.repository;

import com.dbms.project.moovi.data.entity.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    @Query("SELECT a FROM Admin a where a.username=:username AND a.password=:password")
    Iterable<Admin> findAdminByCredentials(@Param("username") String username, @Param("password") String password);
}
