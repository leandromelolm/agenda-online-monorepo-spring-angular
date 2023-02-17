package com.lm.myagenda.services;

import com.lm.myagenda.models.Agenda;
import com.lm.myagenda.repositories.AgendaRepository;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    @Autowired
    AgendaRepository repository;

    public Page<Agenda> findAll(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    public Agenda findById(Long id) {
        Optional<Agenda> agendaOptional = repository.findById(id);
        return agendaOptional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));

    }
}
