package com.lm.myagenda.services;

import com.lm.myagenda.dto.AddressDTO;
import com.lm.myagenda.dto.PersonNewDTO;
import com.lm.myagenda.dto.PersonSummaryDTO;
import com.lm.myagenda.dto.PersonWithAddressDTO;
import com.lm.myagenda.models.Address;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.models.Phone;
import com.lm.myagenda.repositories.AddressRepository;
import com.lm.myagenda.repositories.PersonRepository;
import com.lm.myagenda.repositories.PhoneRepository;
import com.lm.myagenda.services.exceptions.DataIntegratyViolationException;
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
    public Person findById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        return person.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
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

    private void findByEmail(Person obj){
        Optional<Person> personOptional = personRepository.findByEmailAddress(obj.getEmailAddress());
        if(personOptional.isPresent() && !personOptional.get().getId().equals(obj.getId())){
            throw new DataIntegratyViolationException("Email já cadastrado");
        }
    }

    private void findByCpf(Person obj){
        Optional<Person> personOptional = personRepository.findByCpf(obj.getCpf());
        if(personOptional.isPresent() && !personOptional.get().getId().equals(obj.getId())){
            throw new DataIntegratyViolationException("CPF já cadastrado");
        }
    }

    private void findByCns(Person obj){
        Optional<Person> personOptional = personRepository.findByCns(obj.getCns());
        if(personOptional.isPresent() && !personOptional.get().getId().equals(obj.getId())){
            throw new DataIntegratyViolationException("CNS já cadastrado");
        }
    }
    public boolean existsByCpfAndCns(String cpf, String cns) {
        return personRepository.existsByCpfAndCns(cpf, cns);
    }

    @Transactional(readOnly = true)
    public Optional<Address> findByAddressId(Long id){
        return addressRepository.findById(id);
    }

    @Transactional
    public Person insert(Person person){
        findByCpf(person);
        findByCns(person);
        findByEmail(person);
        person.setId(null);
        person.setRegisterDate(dateRegister);
        person = personRepository.save(person);
        addressRepository.saveAll(person.getAddresses());
        phoneRepository.saveAll(person.getPhones());
        return person;
    }

    @Transactional
    public void updatePerson(Long id, Person updatedPerson, Person currentPerson){
        findByCns(updatedPerson);
        findByEmail(updatedPerson);
        updatedPerson.setId(id);
        /* CPF, Data de Registro, Endereços e Telefones não são alterados ao atualizar a pessoa*/
        updatedPerson.setCpf(currentPerson.getCpf());
        updatedPerson.setRegisterDate(currentPerson.getRegisterDate());
        updatedPerson.setAddresses(currentPerson.getAddresses());
        updatedPerson.setPhones(currentPerson.getPhones());
        personRepository.save(updatedPerson);
    }

    @Transactional
    public void updateAddress(Long idAddress, Address addressNew, Person currentPerson){
        addressNew.setId(idAddress);
        addressNew.setPerson(currentPerson);
        addressRepository.save(addressNew);
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
        person.getAddresses().add(address);
        person.getPhones().add(phone);
        return person;
    }

    public boolean isNumber(String s){
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    @Transactional(readOnly = true) //test
    public Page<PersonWithAddressDTO> findAllPersonsWithAddress(PageRequest pageRequest) {
        Page<Person> page = personRepository.findAll(pageRequest);
        // A linha de código seguinte força o JPA a instanciar os objetos em memória fazendo cache dos objetos,
        // com isso não é feita outras consultas no bd. Solução para resolver o problema de N+1 consultas.
        personRepository.findAllPersonsWithAddress(page.stream().collect(Collectors.toList()));
        return page.map(x -> new PersonWithAddressDTO(x));
    }
}
