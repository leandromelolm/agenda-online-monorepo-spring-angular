package com.lm.myagenda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lm.myagenda.models.Event;
import com.lm.myagenda.repositories.EventRepository;

@Service
public class EventService {

    @Autowired
    EventRepository er;

    public List<Event> findAll() {
        return er.findAll();
    }
    
}
