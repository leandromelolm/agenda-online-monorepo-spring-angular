package com.lm.myagenda.services;

import com.lm.myagenda.dto.PersonDTO;
import com.lm.myagenda.dto.PersonNewDTO;
import com.lm.myagenda.dto.PersonSummaryDTO;
import com.lm.myagenda.dto.PersonWithAddressDTO;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.repositories.PersonRepository;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private ModelMapper modelMapper;

    private Person person;

    private PersonDTO personDTO;
    private PersonSummaryDTO personSummaryDTO;
    private PersonWithAddressDTO personWithAddressDTO;
    private PersonNewDTO personNewDTO;

    private Optional<Person> personOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startPerson();
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
    void findByNameOrCpfOrCns() {
    }

    @Test
    void findByAddressId() {
    }

    @Test
    void insert() {
    }

    @Test
    void updatePerson() {
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
    void findAllPersonsWithAddress() {
    }

    private void startPerson(){
        person = new Person
                (ID, NAME, SOCIAL_NAME, CPF, CNS, EMAIL, GENDER, BIRTHDATE, INE, AREA, NOTE, URL_IMAGE);
        personSummaryDTO = new PersonSummaryDTO(person);
        personWithAddressDTO = new PersonWithAddressDTO(person);
        personOptional = Optional.of
                (new Person(ID, NAME, SOCIAL_NAME, CPF, CNS, EMAIL, GENDER, BIRTHDATE, INE, AREA, NOTE, URL_IMAGE));
    }
}