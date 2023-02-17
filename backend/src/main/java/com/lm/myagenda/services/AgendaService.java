package com.lm.myagenda.services;

import com.lm.myagenda.dto.AgendaDTO;
import com.lm.myagenda.models.Agenda;
import com.lm.myagenda.models.Professional;
import com.lm.myagenda.repositories.AgendaRepository;
import com.lm.myagenda.services.exceptions.DataIntegratyViolationException;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

    public Page<AgendaDTO> findAll(Integer page, Integer size) {
        Page<Agenda> agendaPage = repository.findAll(PageRequest.of(page,size));
        return agendaPage.map(x -> new AgendaDTO(x));
    }

    public Agenda findById(Long id) {
        Optional<Agenda> agendaOptional = repository.findById(id);
        return agendaOptional.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public Agenda create(AgendaDTO obj) {
        return repository.save(modelMapper.map(obj, Agenda.class));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
