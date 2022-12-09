package com.lm.myagenda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.repositories.AttendanceRepository;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository atr;

    public List<Attendance> findAll() {
        return atr.findAll();
    }
    
}
