package com.lm.myagenda.services;

import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository atr;

    public List<Attendance> findAll() {
        return  atr.findAll();
    }
    
}
