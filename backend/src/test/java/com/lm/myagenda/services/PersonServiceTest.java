package com.lm.myagenda.services;

import com.lm.myagenda.dto.*;
import com.lm.myagenda.models.Address;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.models.Phone;
import com.lm.myagenda.repositories.AddressRepository;
import com.lm.myagenda.repositories.PersonRepository;
import com.lm.myagenda.repositories.PhoneRepository;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class PersonServiceTest {

    public static final String OBJETO_NAO_ENCONTRADO = "Objeto não encontrado";
    public static final long ID = 10L;
    public static final String NAME = "personName10";
    public static final String EMAIL = "personname10@email.com";
    public static final String CPF = "1234567890";
    public static final String CNS = "123123412341234";
    public static final String GENDER = "Masculino";
    public static final String SOCIAL_NAME = "personSocialName10";
    public static final LocalDate BIRTHDATE = LocalDate.parse("1992-01-01");
    public static final String INE = "00001234";
    public static final String AREA = "area";
    public static final String NOTE = "anotação";
    public static final String URL_IMAGE = "url";
    public static final long ID_ADDRESS = 11L;
    public static final String LOGRADOURO = "avenida principal teste";
    public static final String NUMERO = "10";
    public static final String COMPLEMENTO = "Complemento";
    public static final String BAIRRO = "Cidade Universitária";
    public static final String CIDADE = "Recife";
    public static final String ESTADO = "PE";
    public static final String PAIS = "Brasil";
    public static final String CEP = "11222111";
    public static final String OBS = "observacao";
    public static final String TIPO = "Residencial";
    public static final int INDEX = 0;

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private PhoneRepository phoneRepository;

    @Mock
    private ModelMapper modelMapper;

    private Person person;
    private Person person1;

    private PersonDTO personDTO;
    private PersonSummaryDTO personSummaryDTO;
    private PersonWithAddressDTO personWithAddressDTO;
    private PersonNewDTO personNewDTO;

    private Optional<Person> personOptional;

    private Address address;
    private AddressDTO addressDTO;
    private Phone phone;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPerson();
        startAddress();
        startPhone();
    }

    @Test
    void whenFindByIdThenReturnAnPersonInstance() {
        when(personRepository.findById(anyLong())).thenReturn(personOptional);

        Person response = personService.findById(ID);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME.toUpperCase(), response.getName());
        assertEquals(EMAIL, response.getEmailAddress());
        assertEquals(CPF, response.getCpf());
        assertEquals(CNS, response.getCns());
        assertEquals(GENDER, response.getGender());
        assertEquals(SOCIAL_NAME, response.getSocialName());
        assertEquals(BIRTHDATE, response.getBirthdate());
        assertEquals(INE, response.getIne());
        assertEquals(AREA, response.getArea());
        assertEquals(NOTE, response.getNote());
        assertEquals(URL_IMAGE, response.getUrlImage());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException() {

        when(personRepository.findById(anyLong())).thenThrow(new ObjectNotFoundException(OBJETO_NAO_ENCONTRADO));

        try{
            personService.findById(ID);
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(OBJETO_NAO_ENCONTRADO, ex.getMessage());
        }
    }

    @Test
    void whenFindByNameOrCpfOrCns_SearchBlank_ThenReturnAListOfPersonsPaged() {
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        personList.add(person1);
        Page<Person> personPage = new PageImpl<>(personList);
        PageRequest pageRequest = PageRequest.of(0,5);
        when(personRepository.findAllPerson(pageRequest)).thenReturn(personPage);

        Page<PersonSummaryDTO> response =
                personService.findByNameOrCpfOrCns("",0,5,"name","DESC");

        assertNotNull(response);
        assertEquals(PageImpl.class, response.getClass());
        assertEquals(PersonSummaryDTO.class, response.getContent().get(0).getClass());
        assertEquals(NAME.toUpperCase(), response.getContent().get(0).getName());
        assertEquals("Jose João".toUpperCase(), response.getContent().get(1).getName());
        assertEquals(2, response.getContent().size());
    }
    @Test
    void whenFindByNameOrCpfOrCns_SearchByNumber_ThenReturnFindedPersonPaged() {
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        Page<Person> personPage = new PageImpl<>(personList);
        Pageable pageable =  PageRequest.of(0, 10, Sort.Direction.valueOf("DESC"),"name");
        when(personRepository.findByCpfOrCns(CPF,pageable)).thenReturn(personPage);

        Page<PersonSummaryDTO> response =
                personService.findByNameOrCpfOrCns(CPF,0,10,"name","DESC");

        assertNotNull(response);
        assertEquals(PageImpl.class, response.getClass());
        assertEquals(PersonSummaryDTO.class, response.getContent().get(0).getClass());
        assertEquals(NAME.toUpperCase(), response.getContent().get(0).getName());
        assertEquals(1, response.getContent().size());
    }

    @Test
    void whenFindByNameOrCpfOrCns_SearchByName_ThenReturnFindedPersonPaged() {
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        Page<Person> personPage = new PageImpl<>(personList);
        Pageable pageable =  PageRequest.of(0, 10, Sort.Direction.valueOf("DESC"),"name");
        when(personRepository.findByNameContaining(NAME,pageable)).thenReturn(personPage);

        Page<PersonSummaryDTO> response =
                personService.findByNameOrCpfOrCns(NAME,0,10,"name","DESC");

        assertNotNull(response);
        assertEquals(PageImpl.class, response.getClass());
        assertEquals(PersonSummaryDTO.class, response.getContent().get(0).getClass());
        assertEquals(NAME.toUpperCase(), response.getContent().get(0).getName());
    }

    @Test
    void findByAddressId() {
    }

    @Test
    void whenInsertTheReturnSucess() {
        when(personRepository.save(any())).thenReturn(person);
        when(addressRepository.saveAll(any())).thenReturn(person.getAddresses());
        when(phoneRepository.saveAll(any())).thenReturn(person.getPhones());

        Person response = personService.insert(person);

        assertNotNull(response);
        assertEquals(Person.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME.toUpperCase(), response.getName());
        assertEquals(CPF, response.getCpf());
        assertEquals(person.getAddresses(), response.getAddresses());
        assertEquals(person.getPhones(), response.getPhones());
        assertEquals(phone, response.getPhones().get(INDEX));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(personRepository.save(any())).thenReturn(person);

        Person response = personService.updatePerson(ID, person, person);

        assertEquals(Person.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME.toUpperCase(), response.getName());
        assertEquals(EMAIL, response.getEmailAddress());
        assertEquals(CPF, response.getCpf());
    }

    @Test
    void updateAddress() {
    }

    @Test
    void delete() {
    }

    @Test
    void isNumber() {
    }

    @Test
    void fromDtoToEntity(){
    }

    @Test
    void whenfindAllPersonsWithAddressThenReturnAListOfPersonsWithAddressPaged(){
        List<Person> personList = new ArrayList<>();
        personList.add(person);
        Page<Person> personPage = new PageImpl<>(personList);
        PageRequest pageRequest = PageRequest.of(0,1);

        when(this.personRepository.findAllPersonsWithAddress(personList)).thenReturn(personList);
        when(this.personRepository.findAll(pageRequest)).thenReturn(personPage);

        Page<PersonWithAddressDTO> response = personService.findAllPersonsWithAddress(pageRequest);

        assertNotNull(response);
        assertEquals(PageImpl.class, response.getClass());
        assertEquals(PersonWithAddressDTO.class, response.getContent().get(INDEX).getClass());
        assertEquals(AddressDTO.class, response.getContent().get(INDEX).getAddresses().get(INDEX).getClass());
        assertEquals(NAME.toUpperCase(), response.getContent().get(INDEX).getName());
        assertEquals(CPF, response.getContent().get(INDEX).getCpf());
        assertEquals(EMAIL, response.getContent().get(INDEX).getEmailAddress());
        assertEquals(LOGRADOURO, response.getContent().get(INDEX).getAddresses().get(INDEX).getLogradouro());
        assertEquals(BAIRRO, response.getContent().get(INDEX).getAddresses().get(INDEX).getBairro());
        assertEquals(CIDADE, response.getContent().get(INDEX).getAddresses().get(INDEX).getCidade());
        assertEquals(CEP, response.getContent().get(INDEX).getAddresses().get(INDEX).getCep());
    }

    private void startPerson(){
        person = new Person
                (ID, NAME, SOCIAL_NAME, CPF, CNS, EMAIL, GENDER, BIRTHDATE, INE, AREA, NOTE, URL_IMAGE);
        person1 = new Person
                (ID+1, "Jose João", SOCIAL_NAME, CPF, CNS, EMAIL, GENDER, BIRTHDATE, INE, AREA, NOTE, URL_IMAGE);
        personSummaryDTO = new PersonSummaryDTO(person);
        personWithAddressDTO = new PersonWithAddressDTO(person);
        personOptional = Optional.of
                (new Person(ID, NAME, SOCIAL_NAME, CPF, CNS, EMAIL, GENDER, BIRTHDATE, INE, AREA, NOTE, URL_IMAGE));
    }

    private void startAddress(){
        address = new Address
                (ID_ADDRESS, LOGRADOURO, NUMERO, COMPLEMENTO, BAIRRO, CIDADE, ESTADO, PAIS, CEP, OBS, TIPO, person);
        addressDTO = new AddressDTO(address);
        person.getAddresses().add(address);
    }

    private void startPhone(){
        phone = new Phone(null,"11","999999999","telefone pessoal","pessoal", person);
        person.getPhones().add(phone);
    }
}