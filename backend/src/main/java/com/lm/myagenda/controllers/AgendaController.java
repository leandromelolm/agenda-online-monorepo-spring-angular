package com.lm.myagenda.controllers;

import com.lm.myagenda.models.Agenda;
import com.lm.myagenda.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class AgendaController {

    @Autowired
    AgendaService ags;
    
    @RequestMapping(value="/agenda/all", method = RequestMethod.GET)
    public ResponseEntity<List<Agenda>> test(){
        List<Agenda> agendasList = ags.findAll();
        return ResponseEntity.ok().body(agendasList);
    }
}
