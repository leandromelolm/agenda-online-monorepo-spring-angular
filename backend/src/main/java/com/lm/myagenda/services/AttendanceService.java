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
    AttendanceRepository atr;

    public List<Attendance> findAll() {
        return  atr.findAll();
    }

    @Transactional(readOnly = true)
    public Page<AttendanceDTO> pagedFindAll(Pageable pageable, int pageNumber, int pageSize) {
        pageable =  PageRequest.of(pageNumber, pageSize, Sort.by("startTime"));
        Page<Attendance> page = atr.findAll(pageable);
        return page.map(x -> new AttendanceDTO(x));
    }

    @Transactional(readOnly = true)
    public Page<AttendanceDTO> findAllAttendaceOfAgenda(Long idAgenda, Pageable pageable) {
        Page<Attendance> page = atr.findAllByIdAgenda(idAgenda, pageable);
        return page.map(x -> new AttendanceDTO(x));
    }
}
