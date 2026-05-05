package com.maki.web.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.maki.web.entities.Operator;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Long> {

    @Query("SELECT o FROM Operator o WHERE o.username = :username")
    Optional<Operator> findByUsername(@Param("username") String username);

    @Query("SELECT o FROM Operator o WHERE o.username = :username AND o.password = :password")
    Optional<Operator> findByUsernameAndPassword(
            @Param("username") String username,
            @Param("password") String password);

    @Query("SELECT o FROM Operator o WHERE LOWER(o.name) LIKE LOWER(CONCAT('%', :term, '%')) "
            + "OR LOWER(o.username) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<Operator> searchByNameOrUsername(@Param("term") String term);

    @Query("SELECT COUNT(o) FROM Operator o WHERE o.username = :username")
    Long countByUsername(@Param("username") String username);

    @Query("SELECT o FROM Operator o ORDER BY o.name ASC")
    List<Operator> findAllOrderByNameAsc();
}
