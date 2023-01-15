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
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Transactional(readOnly = true)
    @Query(value = "SELECT p FROM Person p JOIN FETCH p.addresses WHERE p.id = :id")
    Optional<Person> findById(@Param("id")Long id);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Person obj JOIN FETCH obj.addresses WHERE obj IN :persons")
    List<Person> findAllPersonsWithAddress(List<Person> persons);

    @Transactional(readOnly = true)
    @Query("SELECT new Person(p.name, p.cpf, p.cns, p.birthdate, p.emailAddress, p.id) " +
            "FROM Person p")
    Page<Person> findAllPerson(PageRequest of);

    @Transactional(readOnly = true)
    @Query("SELECT new Person(p.name, p.cpf, p.cns, p.birthdate, p.emailAddress, p.id) " +
            "FROM Person p WHERE p.cpf LIKE %:search% " +
            "OR p.cns LIKE %:search%")
    Page<Person> findByCpfOrCns(@Param("search")String search, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT new Person(p.name, p.cpf, p.cns, p.birthdate, p.emailAddress, p.id) " +
            "FROM Person p WHERE UPPER(p.name) " +
            "LIKE CONCAT('%',UPPER(:search),'%')")
    Page<Person> findByNameContaining(@Param("search")String search, Pageable pageable);

    Optional<Person> findByEmailAddress(String emailAddress);

    Optional<Person> findByCpf(String cpf);

    Optional<Person> findByCns(String cns);



    @Transactional(readOnly = true)
//    @Query("SELECT x FROM Person x WHERE UPPER(x.name) LIKE CONCAT('%',UPPER(:name),'%')") //JPQL
    Page<Person> findByNameContainingIgnoreCase(@Param("searchedname") String name, Pageable pageable); //Query with Spring Data JPA

    //    Optimized Query
    @Transactional(readOnly = true)
    @Query(value = "SELECT p FROM Person p JOIN FETCH p.addresses",
            countQuery = "SELECT COUNT(p) FROM Person p JOIN p.addresses")
    Page<Person> findAllWithAddress(Pageable pageable);

    boolean existsByCpfAndCns(String cpf, String cns);
}



// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation