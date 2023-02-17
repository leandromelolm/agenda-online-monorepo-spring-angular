package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.EventDTO;
import com.lm.myagenda.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/event")
public class EventController {

    @Autowired
    EventService service;
    
    @GetMapping()
    public ResponseEntity<List<EventDTO>> findAllOrFindByName(
            @RequestParam(value="search", defaultValue="") String name,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value="orderBy", defaultValue="dateUTC") String orderBy,
            @RequestParam(value="direction", defaultValue="ASC") String direction){
        Page<EventDTO> eventDTOS = service.findAllOrFindByName(name, page, size, orderBy, direction);
        return ResponseEntity.ok().body(eventDTOS.getContent());
    }
}
