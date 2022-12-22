package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.PersonDTO;
import com.lm.myagenda.dto.PersonNewDTO;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/api/person")
public class PersonController {

    @Autowired
    PersonService personService;
    
    @RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> findAll(){
        List<Person> persons = personService.findAll();
        return ResponseEntity.ok().body(persons);
    }

    @GetMapping
    public ResponseEntity<Page<PersonDTO>> searchByNamePaged(
            @RequestParam(value="search", defaultValue="") String searchedName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="DESC") String direction){
        Page<PersonDTO> personList = personService.searchByName( searchedName, page, size, direction, orderBy);
        return ResponseEntity.ok().body(personList);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody PersonNewDTO personNewDTO){
        Person person = personService.fromDtoToEntity(personNewDTO);
        person = personService.insert(person);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
