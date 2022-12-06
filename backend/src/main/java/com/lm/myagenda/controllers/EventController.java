package com.lm.myagenda.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lm.myagenda.models.Event;
import com.lm.myagenda.repositories.EventRepository;

@RestController
@RequestMapping(value="/api")
public class EventController {

    @Autowired
    EventRepository er;

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public ResponseEntity<?> test(){
        List<Event>eventos = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss zzz");

        c.set(Calendar.YEAR, 2023);
        c.set(Calendar.MONTH, 9);
        c.set(Calendar.DAY_OF_MONTH,7);
        c.set(Calendar.HOUR_OF_DAY, 22);
        c.set(Calendar.MINUTE, 00);
        Event event = new Event();
        event.setTitle("test1");
        event.setDate(c);        
        event.setStart(df.format(c.getTime()).toString());
        c.add(Calendar.MINUTE, +14);
        event.setEnd(df.format(c.getTime()).toString());
        eventos.add(event);                
        
        Event event1 = new Event();
        c.set(Calendar.YEAR, 2023);
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DAY_OF_MONTH,19);
        c.set(Calendar.HOUR, 8);
        c.set(Calendar.MINUTE, 00);
        event1.setTitle("test2");
        event1.setDate(c);        
        event1.setStart(df.format(c.getTime()).toString());
        c.add(Calendar.MINUTE, +14);
        event1.setEnd(df.format(c.getTime()).toString());
        eventos.add(event1);

        er.saveAll(eventos);

        return ResponseEntity.ok().body(eventos);
    }
    
}
