package com.lm.myagenda.services;

import com.lm.myagenda.dto.*;
import com.lm.myagenda.models.Address;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.models.Phone;
import com.lm.myagenda.repositories.AddressRepository;
import com.lm.myagenda.repositories.PersonRepository;
import com.lm.myagenda.repositories.PhoneRepository;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    ModelMapper modelMapper;

    private final static Instant dateRegister = Instant.ofEpochSecond(System.currentTimeMillis()/1000);

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<PersonSummaryDTO> findByNameOrCpfOrCns(String findString, Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable =  PageRequest.of(page, size, Direction.valueOf(direction),orderBy);
        if(findString.isBlank()){
            Page<Person> persons = personRepository.findAllPerson(PageRequest.of(page,size));
            return persons.map(x -> new PersonSummaryDTO(x));
        }
        if(isNumber(findString)){
            Page<Person> persons = personRepository.findByCpfOrCns(findString, pageable);
            return persons.map(x -> new PersonSummaryDTO(x));
        }
        Page<Person> persons = personRepository.findByNameContaining(findString, pageable);
        return persons.map(x -> new PersonSummaryDTO(x));
    }

    public Page<PersonSummaryDTO> findAllSummary(Integer page, Integer limitSize){
        Page<Person> persons = personRepository.findAll(PageRequest.of(page,limitSize));
        return persons.map(x -> new PersonSummaryDTO(x));
    }

    @Transactional(readOnly = true)
    public List<PersonDTO> findAllWithAddress(Integer limitSize){
        Page<Person> persons = personRepository.findAllWithAddress(PageRequest.of(0,limitSize)); // Optimized Query
        return  getPersonAddressDtos(Collections.unmodifiableList(persons.getContent()));
    }

    @Transactional(readOnly = true)
    public Page<PersonAddressDTO> findAllPage(PageRequest pageRequest) {
        Page<Person> page = personRepository.findAll(pageRequest);
        // A linha de código seguinte força o JPA a instanciar os objetos em memória fazendo cache dos objetos,
        // com isso não é feita outras consultas no bd. Solução para resolver o problema de N+1 consultas.
        personRepository.findPersonsAndAddress(page.stream().collect(Collectors.toList()));
        return page.map(x -> new PersonAddressDTO(x));
    }

    @Transactional(readOnly = true)
    public Person findById(Long id) {
       Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    @Transactional(readOnly = true)
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
    public void updateAddress(Long idAddress, Address addressNew, Person currentPerson){
        addressNew.setId(idAddress);
        addressNew.setPerson(currentPerson);
        addressRepository.save(addressNew);
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

    public Address fromDtoToEntityUsingModelMapper(AddressDTO addressDTO){
        return modelMapper.map(addressDTO, Address.class);
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

    private List<PersonDTO> getPersonAddressDtos(List<Person> personList) {
        if(personList.size() == 0) return  null;

        List<PersonDTO> personDTOList = new ArrayList<>();
        personList.forEach(p -> {
            PersonDTO dtoPerson = new PersonDTO();
            BeanUtils.copyProperties(p, dtoPerson);

            AddressDTO addressDTO = new AddressDTO();
            BeanUtils.copyProperties(p.getEnderecos().get(0), addressDTO); // pegando apenas o endereço do indice 0
            dtoPerson.getAddresses().add(addressDTO);
            personDTOList.add(dtoPerson);
        });
        return personDTOList;
    }

    public boolean isNumber(String s){
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
