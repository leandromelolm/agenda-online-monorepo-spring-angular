package com.lm.myagenda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lm.myagenda.models.Person;
import com.lm.myagenda.repositories.PersonRepository;

@Service
public class PersonService {

    @Autowired
    PersonRepository pr;

    public List<Person> findAll() {
        return pr.findAll();
    }
    
}
