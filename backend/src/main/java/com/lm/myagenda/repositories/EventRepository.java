package com.lm.myagenda.repositories;

import com.lm.myagenda.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Event obj")
    Page<Event> findAllPaged(Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Event obj WHERE obj.title LIKE CONCAT('%',UPPER(:search),'%')")
    Page<Event> findByName(@Param("search") String search, Pageable pageable);

    @Transactional(readOnly = true)
    @Query("SELECT obj FROM Event obj WHERE obj.dateUTC >= :today")
    Page<Event> findAllPagedNoPastDate(Instant today, Pageable pageable);
}
