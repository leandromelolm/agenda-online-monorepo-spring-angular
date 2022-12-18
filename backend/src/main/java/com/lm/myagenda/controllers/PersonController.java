package com.lm.myagenda.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lm.myagenda.models.Person;
import com.lm.myagenda.services.PersonService;

@RestController
@RequestMapping(value="/api")
public class PersonController {

    @Autowired
    PersonService ps;
    
    @RequestMapping(value="/person/all", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> findAll(){
        List<Person> persons = ps.findAll();
        return ResponseEntity.ok().body(persons);
    }
}
