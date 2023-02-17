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

@Service
public class EventService {

    @Autowired
    EventRepository repository;

    public Page<EventDTO> findAllOrFindByName(String search, Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable =  PageRequest.of(page, size, Sort.Direction.valueOf(direction),orderBy);
        Page<Event> events;
        if(search.isBlank()){
            events = repository.findAllPaged(pageable);
        }else{
            events = repository.findByName(search.toUpperCase(), pageable);
        }
        return events.map(x -> new EventDTO(x));
    }
}