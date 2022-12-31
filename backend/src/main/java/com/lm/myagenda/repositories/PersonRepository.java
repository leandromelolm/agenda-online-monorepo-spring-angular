package com.lm.myagenda.repositories;

import com.lm.myagenda.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Transactional(readOnly = true)
    @Query(value = "SELECT p FROM Person p JOIN FETCH p.enderecos",
            countQuery = "SELECT COUNT(p) FROM Person p JOIN p.enderecos")
    Page<Person> findAllWithAddress(Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT new Person(p.name, p.cpf, p.cns, p.birthdate) FROM Person p")
    Page<Person> findAllPerson(PageRequest of);

    @Transactional(readOnly = true)
    @Query("SELECT new Person(p.name, p.cpf, p.cns, p.birthdate) " +
            "FROM Person p WHERE p.cpf LIKE %:search%" +
            "OR p.cns LIKE %:search%")
    Page<Person> findByCpfOrCns(@Param("search")String search, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT new Person(p.name, p.cpf, p.cns, p.birthdate) " +
            "FROM Person p WHERE UPPER(p.name) " +
            "LIKE CONCAT('%',UPPER(:search),'%')")
    Page<Person> findByNameContaining(@Param("search")String search, Pageable pageable);
}



// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation