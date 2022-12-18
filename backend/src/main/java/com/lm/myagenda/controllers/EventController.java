package com.lm.myagenda.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lm.myagenda.models.Event;
import com.lm.myagenda.services.EventService;

@RestController
@RequestMapping(value="/api")
public class EventController {

    @Autowired
    EventService es;
    
    @RequestMapping(value="/events/all", method = RequestMethod.GET)
    public ResponseEntity<List<Event>> findAll(){
        List<Event> eventos = es.findAll();
        return ResponseEntity.ok().body(eventos);
    }
}
