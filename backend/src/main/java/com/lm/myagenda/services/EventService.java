package com.lm.myagenda.services;

import com.lm.myagenda.dto.EventDTO;
import com.lm.myagenda.models.Event;
import com.lm.myagenda.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
public class EventService {

    @Autowired
    EventRepository repository;

    public Page<EventDTO> findAllOrFindByName(String search, Boolean pastDate, Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable =  PageRequest.of(page, size, Sort.Direction.valueOf(direction),orderBy);
        Page<Event> events;
        if(search.isBlank() && pastDate){
            events = repository.findAllPaged(pageable);
        }
        if(search.isBlank() && !pastDate){
            events = repository.findAllPagedNoPastDate(Instant.now(),pageable);
        }
        else{
            events = repository.findByName(search.toUpperCase(), pageable);
        }
        return events.map(x -> new EventDTO(x));
    }
}