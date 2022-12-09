package com.lm.myagenda.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.services.AttendanceService;

@RestController
@RequestMapping(value="/api")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;
    
    @RequestMapping(value="/service/all", method = RequestMethod.GET)
    public ResponseEntity<List<Attendance>> allService(){
        List<Attendance> ss = attendanceService.findAll();
        return ResponseEntity.ok().body(ss);
    }
}
