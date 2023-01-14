package com.lm.myagenda.services;

import com.lm.myagenda.dto.*;
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
        if(personOptional.isPresent()){
            throw new DataIntegratyViolationException("Email já cadastrado");
        }
    }

    private void findByCpf(Person obj){
        Optional<Person> personOptional = personRepository.findByCpf(obj.getCpf());
        if(personOptional.isPresent()){
            throw new DataIntegratyViolationException("CPF já cadastrado");
        }
    }

    private void findByCns(Person obj){
        Optional<Person> personOptional = personRepository.findByCns(obj.getCns());
        if(personOptional.isPresent()){
            throw new DataIntegratyViolationException("CNS já cadastrado");
        }
    }


    // Com problema N+1
    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<PersonSummaryDTO> findAllSummary(Integer page, Integer limitSize){
        Page<Person> persons = personRepository.findAll(PageRequest.of(page,limitSize));
        return persons.map(x -> new PersonSummaryDTO(x));
    }

    @Transactional(readOnly = true)
    public List<PersonWithAddressDTO> findAllWithAddress(Integer limitSize){
        Page<Person> persons = personRepository.findAllWithAddress(PageRequest.of(0,limitSize)); // Optimized Query
        return  getPersonAddressDtos(Collections.unmodifiableList(persons.getContent()));
    }

    @Transactional(readOnly = true)
    public Page<PersonWithAddressDTO> findPersonsAndAddress(PageRequest pageRequest) {
        Page<Person> page = personRepository.findAll(pageRequest);
        // A linha de código seguinte força o JPA a instanciar os objetos em memória fazendo cache dos objetos,
        // com isso não é feita outras consultas no bd. Solução para resolver o problema de N+1 consultas.
        personRepository.findPersonsAndAddress(page.stream().collect(Collectors.toList()));
        return page.map(x -> new PersonWithAddressDTO(x));
    }

    @Transactional(readOnly = true)
    public Page<PersonDTO> searchByName(String searchedName, Integer page, Integer size, String direction, String orderBy) {
        Pageable pageable =  PageRequest.of(page, size, Direction.valueOf(direction),orderBy);
        Page<Person> pagePerson = personRepository.findByNameContainingIgnoreCase(searchedName, pageable);
        return pagePerson.map(x -> new PersonDTO(x, x.getAttendances()));
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
    public void updateAddressByIndex(Long id, int i, Person updatedPerson, Person currentPerson){
        updatedPerson.setId(id);
        updatedPerson.getAddresses().get(0).setId(currentPerson.getAddresses().get(i).getId());
        addressRepository.save(updatedPerson.getAddresses().get(0));
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

    private List<PersonWithAddressDTO> getPersonAddressDtos(List<Person> personList) {
        if(personList.size() == 0) return  null;

        List<PersonWithAddressDTO> personDTOList = new ArrayList<>();
        personList.forEach(p -> {
            PersonWithAddressDTO dtoPerson = new PersonWithAddressDTO();
            BeanUtils.copyProperties(p, dtoPerson);

            AddressDTO addressDTO = new AddressDTO();
            BeanUtils.copyProperties(p.getAddresses().get(0), addressDTO); // pegando apenas o endereço do indice 0
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
