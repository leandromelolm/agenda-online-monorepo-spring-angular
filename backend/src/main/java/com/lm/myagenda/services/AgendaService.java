package com.lm.myagenda.services;

import com.lm.myagenda.models.Agenda;
import com.lm.myagenda.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaService {

    @Autowired
    AgendaRepository agr;

    public List<Agenda> findAll() {
        return agr.findAll();
    }
    
}
