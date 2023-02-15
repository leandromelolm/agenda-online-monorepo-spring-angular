package com.lm.myagenda.services;

import com.lm.myagenda.models.Professional;
import com.lm.myagenda.repositories.ProfessionalRepository;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
}
