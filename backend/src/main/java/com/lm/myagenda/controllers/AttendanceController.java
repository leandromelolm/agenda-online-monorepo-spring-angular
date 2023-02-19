package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.AttendanceDTO;
import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService service;

    @GetMapping("/all")
    public ResponseEntity<List<AttendanceDTO>> findAll(){
        List<Attendance> attendanceList = service.findAll();
        List<AttendanceDTO> AttendanceDTOList = attendanceList.stream().map(
                x -> new AttendanceDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(AttendanceDTOList);
    }

    @GetMapping("/attendances")
    public ResponseEntity<Page<AttendanceDTO>> findAllAttendance(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "15") Integer size){
        Page<AttendanceDTO> attendanceList = service.pagedFindAll(page, size);
        return ResponseEntity.ok().body(attendanceList);
    }

    @GetMapping("/{id}/{page}/{size}")
    public ResponseEntity<Page<AttendanceDTO>>findAttendanceByIdAgenda(
            @PathVariable Long id,
            @PathVariable Integer page,
            @PathVariable Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceDTO> listDto = service.findAllAttendaceOfAgenda(id, pageable);
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/agenda")
    public ResponseEntity<Page<AttendanceDTO>>findAttendanceByIdAgendaWithRequestParam(
            @RequestParam(value = "id", defaultValue = "0") Long idAgenda,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "25") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceDTO> listDto = service.findAllAttendaceOfAgenda(idAgenda, pageable);
        return ResponseEntity.ok().body(listDto);
    }
}
