package com.lm.myagenda.services;

import com.lm.myagenda.dto.AttendanceDTO;
import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.repositories.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository repository;

    public List<Attendance> findAll() {
        return  repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<AttendanceDTO> pagedFindAll(int pageNumber, int pageSize) {
        Pageable pageable =  PageRequest.of(pageNumber, pageSize, Sort.by("startTime"));
        Page<Attendance> page = repository.findAll(pageable);
        return page.map(x -> new AttendanceDTO(x));
    }

    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findAllAttendaceOfAgenda(Long idAgenda, Pageable pageable) {
        Page<Attendance> page = repository.findAllByIdAgenda(idAgenda, pageable);
        return page.map(x -> new AttendanceDTO(x));
    }
}
