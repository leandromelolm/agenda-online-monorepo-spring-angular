package com.lm.myagenda.dto;

import com.lm.myagenda.models.Event;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {

    private UUID id;
    private String groupId;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Instant dateUTC;
    @Column(name = "time_start")
    private String start;
    @Column(name = "time_end")
    private String end;
    private String url;
    private String backgroundColor;
    private String color;
    private boolean overlap;
    private String display;
    private String descricao;
    private Long attendanceId;
    private String personCPF;
    private String personPhone;
    private String personBirthDate;

    public EventDTO(Event e) {
        this.id = e.getId();
        this.groupId = e.getGroupId();
        this.title = e.getTitle();
        this.dateUTC = e.getDateUTC();
        this.start = e.getStart();
        this.end = e.getEnd();
        this.url = e.getUrl();
        this.backgroundColor = e.getBackgroundColor();
        this.color = e.getColor();
        this.overlap = e.isOverlap();
        this.display = e.getDisplay();
        this.descricao = e.getDescricao();
        this.attendanceId = e.getAttendanceId();
        this.personCPF = e.getPersonCPF();
        this.personPhone = e.getPersonPhone();
        this.personBirthDate = e.getPersonBirthDate();
    }        
}