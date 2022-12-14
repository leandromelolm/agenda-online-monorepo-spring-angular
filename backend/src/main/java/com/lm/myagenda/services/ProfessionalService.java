package com.lm.myagenda.services;

import com.lm.myagenda.models.Professional;
import com.lm.myagenda.repositories.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalService {

    @Autowired
    ProfessionalRepository pfr;

    public List<Professional> findAll() {
        return pfr.findAll();
    }
    
}
