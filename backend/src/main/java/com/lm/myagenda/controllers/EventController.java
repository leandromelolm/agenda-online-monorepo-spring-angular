package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.AgendaDTO;
import com.lm.myagenda.dto.EventDTO;
import com.lm.myagenda.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/event")
public class EventController {

    @Autowired
    EventService service;
    
    @GetMapping()
    public ResponseEntity<List<EventDTO>> findAllOrFindByName(
            @RequestParam(value="search", defaultValue="") String name,
            @RequestParam(value="pastdate", defaultValue="true") Boolean pastDate,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value="orderBy", defaultValue="dateUTC") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction){
        Page<EventDTO> eventDTOS = service.findAllOrFindByName(name, pastDate, page, size, orderBy, direction);
        return ResponseEntity.ok().body(eventDTOS.getContent());
    }

    @PostMapping()
    public ResponseEntity<Void> create(@RequestBody EventDTO obj){
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(service.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
