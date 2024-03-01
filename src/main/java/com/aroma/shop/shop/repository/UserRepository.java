package com.aroma.shop.shop.repository;

import com.aroma.shop.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from users where username = :name",nativeQuery = true)
    Optional<User> findUserByName(@Param("name") String name);

    @Query(value = "select * from users where email = :email", nativeQuery = true)
    Optional<User> findUserByEmail(@Param("email") String email);

    @Query(value = "select * from users where id_google = :id",nativeQuery = true)
    Optional<User> findUserByIdGoogle(String id);

    @Query(value = "SELECT EXISTS (select 1 from users where id_google = :id)", nativeQuery = true)
    Boolean findGoogleId(String id);

    @Query(value = "select * from users where activate_code = :code", nativeQuery = true)
    Optional<User> findActivationCode(String code);



}
