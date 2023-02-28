package com.lm.myagenda.services;

import com.lm.myagenda.dto.EventDTO;
import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.models.Event;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.repositories.AttendanceRepository;
import com.lm.myagenda.repositories.EventRepository;
import com.lm.myagenda.repositories.PersonRepository;
import com.lm.myagenda.services.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    EventRepository repository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    ModelMapper mapper;

    public Page<EventDTO> findAllOrFindByName(
            String search, Boolean pastDate, Integer page, Integer size, String orderBy, String direction) {
        Pageable pageable =  PageRequest.of(page, size, Sort.Direction.valueOf(direction),orderBy);
        Page<Event> events;
        if(search.isBlank() && pastDate){
            events = repository.findAllPaged(pageable);
        }
        if(search.isBlank() && !pastDate){
            events = repository.findAllPagedNoPastDate(Instant.now(),pageable);
        }
        else{
            events = repository.findByName(search.toUpperCase(), pageable);
        }
        return events.map(x -> new EventDTO(x));
    }

    public Event findById(UUID id){
        Optional<Event> eventOpt = repository.findById(id);
        return eventOpt.orElseThrow(() -> new ObjectNotFoundException("Evento não encontrado"));
    }

    public Event create(EventDTO obj) {
        Optional<Person> person = personRepository.findByCpf(obj.getPersonCPF());
        if(person.isEmpty()){
            throw new ObjectNotFoundException("CPF não encontrado");
        }
        Instant dateRegister = Instant.ofEpochSecond(System.currentTimeMillis()/1000);
        Attendance attendance = new Attendance(
                null, obj.getDescricao(),"Ativo",
                obj.getDateUTC(),
                obj.getDateUTC().toString().substring(0,10)+ "T"+ obj.getStart(),
                obj.getDateUTC().toString().substring(0,10)+ "T"+ obj.getEnd(),
                "",dateRegister.toString(), person.get());
        attendanceRepository.saveAndFlush(attendance);
        obj.setAttendanceId(attendance.getId());
        obj.setStart(obj.getDateUTC().toString().substring(0,10)+ "T"+ obj.getStart());
        obj.setEnd(obj.getDateUTC().toString().substring(0,10)+ "T"+ obj.getEnd());
        return repository.save(mapper.map(obj, Event.class));
    }

    public void delete(UUID id) {
        Event event = findById(id);
        attendanceRepository.deleteById(event.getAttendanceId());
        repository.deleteById(event.getId());
    }
}