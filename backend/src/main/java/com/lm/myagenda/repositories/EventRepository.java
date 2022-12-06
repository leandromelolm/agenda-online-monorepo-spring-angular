package com.lm.myagenda.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lm.myagenda.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    
}
