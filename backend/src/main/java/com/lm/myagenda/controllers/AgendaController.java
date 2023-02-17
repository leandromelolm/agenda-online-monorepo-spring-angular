package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.AgendaDTO;
import com.lm.myagenda.dto.ProfessionalDTO;
import com.lm.myagenda.models.Agenda;
import com.lm.myagenda.models.Professional;
import com.lm.myagenda.services.AgendaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/agenda")
public class AgendaController {

    @Autowired
    AgendaService service;

    @Autowired
    ModelMapper modelMapper;
    
    @RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<Page<AgendaDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size){
        Page<AgendaDTO> agendasList = service.findAll(page, size);
        return ResponseEntity.ok().body(agendasList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendaDTO> getOneAgenda(@PathVariable("id") Long id){
        Agenda agenda = service.findById(id);
        AgendaDTO agendaDTO = modelMapper.map(agenda, AgendaDTO.class);
        return ResponseEntity.ok().body(agendaDTO);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody AgendaDTO obj){
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(service.create(obj).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
