package com.lm.myagenda.services;

import com.lm.myagenda.dto.PersonSummaryDTO;
import com.lm.myagenda.dto.ProfessionalDTO;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.models.Professional;
import com.lm.myagenda.repositories.ProfessionalRepository;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {

    @Autowired
    ProfessionalRepository repository;

    public List<Professional> findAll() {
        return repository.findAll();
    }

    public Professional findById(Long id) {
        Optional<Professional> professionalOptional = repository.findById(id);
        return professionalOptional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Page<ProfessionalDTO> findByNameOrCpfOrMatricula(
            String search, Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable =  PageRequest.of(page, size, Sort.Direction.valueOf(direction),orderBy);
        if(search.isBlank()){
            Page<Professional> p = repository.findAllPaged(PageRequest.of(page,size));
            return p.map(x -> new ProfessionalDTO(x));
        }
        if(isNumber(search)){
            Page<Professional> p = repository.findByCpfOrMatricula(search, pageable);
            return p.map(x -> new ProfessionalDTO(x));
        }
        Page<Professional> p = repository.findByNomeContainingIgnoreCase(search, pageable);
        return p.map(x -> new ProfessionalDTO(x));
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
