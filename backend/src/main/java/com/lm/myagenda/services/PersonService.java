package com.lm.myagenda.services;

import com.lm.myagenda.dto.PersonDTO;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> searchByName(String searchedName, Integer page, Integer size, String direction, String orderBy) {
        Pageable pageable =  PageRequest.of(page, size, Direction.valueOf(direction),orderBy);
        Page<Person> pagePerson = personRepository.findByNameContainingIgnoreCase(searchedName, pageable);
        return pagePerson.map(x -> new PersonDTO(x, x.getAtendimentosAgendados()));
    }
}
