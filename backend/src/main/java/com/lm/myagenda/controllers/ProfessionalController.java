package com.lm.myagenda.controllers;

import com.lm.myagenda.models.Professional;
import com.lm.myagenda.services.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class ProfessionalController {

    @Autowired
    ProfessionalService pfs;
    
    @RequestMapping(value="/professional/all", method = RequestMethod.GET)
    public ResponseEntity<List<Professional>> test(){
        List<Professional> professionalList = pfs.findAll();
        return ResponseEntity.ok().body(professionalList);
    }
}
