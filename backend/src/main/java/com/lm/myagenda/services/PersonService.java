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

    private final static Instant dateRegister = Instant.ofEpochSecond(System.currentTimeMillis()/1000);

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    public Optional<Address> findByAddressId(Long id){
        return addressRepository.findById(id);
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
        person.setRegisterDate(dateRegister);
        person = personRepository.save(person);
        addressRepository.saveAll(person.getEnderecos());
        phoneRepository.saveAll(person.getTelefones());
        return person;
    }

    @Transactional
    public void updatePerson(Long id, Person updatedPerson, Person currentPerson){
        updatedPerson.setId(id);
        /* CPF, Data de Registro, Endereços e Telefones não são alterados ao atualizar a pessoa*/
        updatedPerson.setCpf(currentPerson.getCpf());
        updatedPerson.setRegisterDate(currentPerson.getRegisterDate());
        updatedPerson.setEnderecos(currentPerson.getEnderecos());
        updatedPerson.setTelefones(currentPerson.getTelefones());
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void updateAddress(Long idPerson, Long idAddress, Person updatedPerson, Person currentPerson){
        updatedPerson.setId(idPerson);
        updatedPerson.getEnderecos().get(0).setId(idAddress);
        addressRepository.save(updatedPerson.getEnderecos().get(0));
    }

    @Transactional
    public void updateAddressByIndex(Long id, int i, Person updatedPerson, Person currentPerson){
        updatedPerson.setId(id);
        updatedPerson.getEnderecos().get(0).setId(currentPerson.getEnderecos().get(i).getId());
        addressRepository.save(updatedPerson.getEnderecos().get(0));
    }

    @Transactional
    public void delete(Long id) {
        personRepository.deleteById(id);
    }

    public Person fromDtoToEntity(PersonNewDTO p){
        Person person = new Person(null, p.getName(), p.getSocialName(),
                p.getCpf(), p.getCns(), p.getEmailAddress(), p.getGender(),
                p.getBirthdate(), p.getIne(), p.getArea(), p.getNote(),
                p.getUrlImage());
        Address address = new Address(null, p.getLogradouro(), p.getNumero(),
                p.getComplemento(), p.getBairro(), p.getCidade(), p.getEstado(), p.getPais(),
                p.getCep(), p.getObservacao(), p.getTipo(), person);
        Phone phone = new Phone(null, p.getDdd(), p.getNumber(), p.getDescription(), p.getPhoneType(), person);
        person.getEnderecos().add(address);
        person.getTelefones().add(phone);
        return person;
    }
}
