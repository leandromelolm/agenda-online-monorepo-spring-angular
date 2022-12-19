package com.lm.myagenda.controllers;

import com.lm.myagenda.dto.AttendanceDTO;
import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Page<AttendanceDTO>> pagedFindAll(Pageable pageable){
        Page<AttendanceDTO> attendanceList = attendanceService.pagedFindAll(pageable);
        return ResponseEntity.ok().body(attendanceList);
    }
}
