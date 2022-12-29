package com.lm.myagenda.repositories;

import com.lm.myagenda.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Transactional(readOnly = true)
//    @Query("SELECT x FROM Person x WHERE UPPER(x.name) LIKE CONCAT('%',UPPER(:searchedname),'%')") //JPQL
    Page<Person> findByNameContainingIgnoreCase(@Param("searchedname") String searchedName, Pageable pageable); //Query with Spring Data JPA

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Person obj JOIN FETCH obj.enderecos WHERE obj IN :persons")
    List<Person> findPersonsAndAddress(List<Person> persons);

//    Optimized Query
    @Query(value = "SELECT p FROM Person p JOIN FETCH p.enderecos",
            countQuery = "SELECT COUNT(p) FROM Person p JOIN p.enderecos")
    Page<Person> findAllWithAddress(Pageable pageable);
}

// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation