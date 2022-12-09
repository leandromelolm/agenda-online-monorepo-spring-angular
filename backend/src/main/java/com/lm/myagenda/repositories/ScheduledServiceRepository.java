package com.lm.myagenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lm.myagenda.models.scheduledService;

@Repository
public interface ScheduledServiceRepository extends JpaRepository<scheduledService, Long> {
    
}
