package com.lm.myagenda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lm.myagenda.models.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
}
