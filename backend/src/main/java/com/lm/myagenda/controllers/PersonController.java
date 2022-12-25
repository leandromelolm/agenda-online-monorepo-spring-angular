package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.PersonDTO;
import com.lm.myagenda.dto.PersonNewDTO;
import com.lm.myagenda.models.Address;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/person")
public class PersonController {

    @Autowired
    PersonService personService;
    
    @RequestMapping(value="/all", method = RequestMethod.GET)
    public ResponseEntity<List<Person>> findAll(){
        List<Person> persons = personService.findAll();
        return ResponseEntity.ok().body(persons);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PersonDTO>> searchByNamePaged(
            @RequestParam(value="name", defaultValue="") String searchedName,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value="orderBy", defaultValue="name") String orderBy,
            @RequestParam(value="direction", defaultValue="DESC") String direction){
        Page<PersonDTO> personList = personService.searchByName( searchedName, page, size, direction, orderBy);
        return ResponseEntity.ok().body(personList);
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
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        Person updatedPerson = personService.fromDtoToEntity(updatedPersonDTO);
        personService.updatePerson(id, updatedPerson, personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson);
    }

    @PutMapping("/{personId}/address/{addressId}")
    public ResponseEntity<Object> updateAddress(
            @PathVariable(value = "personId") Long personId,
            @PathVariable(value = "addressId") Long addressId,
            @RequestBody PersonNewDTO updatedPersonDTO){
        Optional<Person> personOptional = personService.findById(personId);
        Optional<Address> addressOptional = personService.findByAddressId(addressId);
        if (!personOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        if(!addressOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Address not found");
        }
        if(!addressOptional.get().getPerson().getId().equals(personOptional.get().getId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    "Address id does not match person id (id do endereço não corresponde com o id da pessoa)");
        }
        Person updatedPerson = personService.fromDtoToEntity(updatedPersonDTO);
        personService.updateAddress(personId, addressId, updatedPerson, personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(updatedPerson.getEnderecos());
    }

    @PutMapping("/{personId}/addressbyindex/{addressIndex}")
    public ResponseEntity<Object> updateAddressByIndex(
            @PathVariable(value = "personId") Long personId,
            @PathVariable(value = "addressIndex") Integer addressIndex,
            @RequestBody PersonNewDTO updatedPersonDTO){
        Optional<Person> personOptional = personService.findById(personId);
        if (personOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found");
        }
        //verifica se existe o indice na lista endereços da pessoa
        addressIndex--;
        if(addressIndex >= personOptional.get().getEnderecos().size() || addressIndex < 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Address not found. The value after addressbyindex/"+
                            "must be greater than 0 and less than or equal to "+
                            personOptional.get().getEnderecos().size() + ".");
        }
        Person updatedPerson = personService.fromDtoToEntity(updatedPersonDTO);
        personService.updateAddressByIndex(personId, addressIndex, updatedPerson, personOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(" Address updated with success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        Optional<Person> personOptional = personService.findById(id);
        if (!personOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found.");
        }
        personService.delete(personOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body("person "+personOptional.get().getName()+" deleted successfully.");
    }
}
