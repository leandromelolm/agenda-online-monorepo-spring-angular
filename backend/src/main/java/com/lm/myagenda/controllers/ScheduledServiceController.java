package com.lm.myagenda.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lm.myagenda.models.scheduledService;
import com.lm.myagenda.services.ScheduledServService;

@RestController
@RequestMapping(value="/api")
public class ScheduledServiceController {

    @Autowired
    ScheduledServService scheduledServService;
    
    @RequestMapping(value="/service/all", method = RequestMethod.GET)
    public ResponseEntity<List<scheduledService>> allService(){
        List<scheduledService> ss = scheduledServService.findAll();
        return ResponseEntity.ok().body(ss);
    }
}
