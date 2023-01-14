package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.AddressDTO;
import com.lm.myagenda.dto.PersonNewDTO;
import com.lm.myagenda.dto.PersonSummaryDTO;
import com.lm.myagenda.dto.PersonWithAddressDTO;
import com.lm.myagenda.models.Address;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.services.PersonService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PersonWithAddressDTO> getOnePerson(@PathVariable("id") Long id){
        Person person = personService.findById(id);
        PersonWithAddressDTO personDTO = modelMapper.map(person, PersonWithAddressDTO.class);
        return ResponseEntity.ok().body(personDTO);
    }

    @GetMapping()
    public ResponseEntity<Page<PersonSummaryDTO>> searchByNameOrCpfOrCns(
            @RequestParam(value="search", defaultValue="") String searchNameOrCpfOrCns,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="DESC") String direction){
        Page<PersonSummaryDTO> personSummaryList =
                personService.findByNameOrCpfOrCns(searchNameOrCpfOrCns, page, size, orderBy, direction);
        return ResponseEntity.ok().body(personSummaryList);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody PersonNewDTO personNewDTO){
        Person person = personService.fromDtoToEntity(personNewDTO);
        person = personService.insert(person);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(person.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Long id,
            @RequestBody PersonNewDTO updatedPersonDTO){
        Person person = personService.findById(id);
        Person updatedPerson = personService.fromDtoToEntity(updatedPersonDTO);
        personService.updatePerson(id, updatedPerson, person);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }

    @PutMapping("/{personId}/address/{addressId}")
    public ResponseEntity<Object> updateAddress(
            @PathVariable(value = "personId") Long personId,
            @PathVariable(value = "addressId") Long addressId,
            @RequestBody AddressDTO addressNewDTO){
        Person person = personService.findById(personId);
        Optional<Address> addressOptional = personService.findByAddressId(addressId);
        if(!addressOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Address not found");
        }
        if(!addressOptional.get().getPerson().getId().equals(person.getId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Address id does not match person id (id do endereço não corresponde com o id da pessoa)");
        }
        Address address = personService.fromDtoToEntityUsingModelMapper(addressNewDTO);
        personService.updateAddress(addressId, address, person);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Person person = personService.findById(id);
        personService.delete(person.getId());
        return ResponseEntity.status(HttpStatus.OK).body("person "+person.getName()+" deleted successfully.");
    }

    @GetMapping(value="/persons/address") //test
    public ResponseEntity<Page<PersonWithAddressDTO>> findAllPersonsWithAddress(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PersonWithAddressDTO> personList = personService.findAllPersonsWithAddress(pageRequest);
        return ResponseEntity.ok().body(personList);
    }
}
