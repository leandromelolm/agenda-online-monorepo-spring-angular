package com.lm.myagenda.services;

import com.lm.myagenda.dto.PersonDTO;
import com.lm.myagenda.dto.PersonNewDTO;
import com.lm.myagenda.models.Address;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.models.Phone;
import com.lm.myagenda.repositories.AddressRepository;
import com.lm.myagenda.repositories.PersonRepository;
import com.lm.myagenda.repositories.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> searchByName(String searchedName, Integer page, Integer size, String direction, String orderBy) {
        Pageable pageable =  PageRequest.of(page, size, Direction.valueOf(direction),orderBy);
        Page<Person> pagePerson = personRepository.findByNameContainingIgnoreCase(searchedName, pageable);
        return pagePerson.map(x -> new PersonDTO(x, x.getAtendimentosAgendados()));
    }

    @Transactional
    public Person insert(Person person){
        person.setId(null);
        person = personRepository.save(person);
        addressRepository.saveAll(person.getEnderecos());
        phoneRepository.saveAll(person.getTelefones());
        return person;
    }

    public Person fromDtoToEntity(PersonNewDTO p){
        Person person = new Person(null, p.getName(), p.getSocialName(),
                p.getCpf(), p.getCns(), p.getEmailAddress(), p.getGender(),
                p.getBirthdate(), p.getIne(), p.getArea(), p.getNote(),
                p.getUrlImage(), Instant.ofEpochSecond(System.currentTimeMillis()/1000));
        Address address = new Address(null, p.getLogradouro(), p.getNumero(),
                p.getComplemento(), p.getBairro(), p.getCidade(), p.getEstado(), p.getPais(),
                p.getCep(), p.getObservacao(), p.getTipo(), person);
        Phone phone = new Phone(null, p.getDdd(), p.getNumber(), p.getDescription(), p.getPhoneType(), person);
        person.getEnderecos().add(address);
        person.getTelefones().add(phone);
        return person;
    }

    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }
}
