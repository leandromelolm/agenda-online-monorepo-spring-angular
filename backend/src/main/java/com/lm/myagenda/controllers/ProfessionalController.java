package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.ProfessionalDTO;
import com.lm.myagenda.models.Professional;
import com.lm.myagenda.services.ProfessionalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/professional")
public class ProfessionalController {

    @Autowired
    ProfessionalService service;

    @Autowired
    ModelMapper modelMapper;

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<List<Professional>> findAll(){
        List<Professional> professionalList = service.findAll();
        return ResponseEntity.ok().body(professionalList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalDTO> getOneProfessional(@PathVariable("id") Long id){
        Professional professional = service.findById(id);
        ProfessionalDTO professionalDTO = modelMapper.map(professional, ProfessionalDTO.class);
        return ResponseEntity.ok().body(professionalDTO);
    }

}
