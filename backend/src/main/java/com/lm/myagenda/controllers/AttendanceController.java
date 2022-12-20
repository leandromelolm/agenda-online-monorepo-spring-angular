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
@RequestMapping(value="/api")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @RequestMapping(value="/admin/attendance/all", method = RequestMethod.GET)
    public ResponseEntity<List<Attendance>> findAllAdmin(){
        return ResponseEntity.ok(attendanceService.findAll());
    }

    @GetMapping("/attendance/all")
    public ResponseEntity<List<AttendanceDTO>> findAll(){
        List<Attendance> attendanceList = attendanceService.findAll();
        List<AttendanceDTO> AttendanceDTOList = attendanceList.stream().map(x -> new AttendanceDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok().body(AttendanceDTOList);
    }

    @GetMapping("/attendances")
    public ResponseEntity<Page<AttendanceDTO>> pagedFindAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "15") Integer size,
            Pageable pageable){
        Page<AttendanceDTO> attendanceList = attendanceService.pagedFindAll(pageable, page, size);
        return ResponseEntity.ok().body(attendanceList);
    }

    @GetMapping("/attendances/{id}")
    public ResponseEntity<Page<AttendanceDTO>>pagedFindIdAgenda(@PathVariable Long id, Pageable pageable){
        Page<AttendanceDTO> listDto = attendanceService.findAllAttendaceOfAgenda(id, pageable);
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping("/attendances/agenda")
    public ResponseEntity<Page<AttendanceDTO>>pagedFindIdAgendaWithRequestParam(
            @RequestParam(value = "id", defaultValue = "1") Long idAgenda,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "25") Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<AttendanceDTO> listDto = attendanceService.findAllAttendaceOfAgenda(idAgenda, pageable);
        return ResponseEntity.ok().body(listDto);
    }
}
