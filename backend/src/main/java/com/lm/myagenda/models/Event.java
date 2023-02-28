package com.lm.myagenda.models;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //Getter e Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;    

    private String groupId;

    private String title; 

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

    public Event(UUID id, String groupId, String title,Instant dateUTC, String start, String end, String url,
            String backgroundColor, String color, boolean overlap,String display, String descricao,Long attendanceId) {
        this.id = id;
        this.groupId = groupId;
        this.title = title;
        this.dateUTC = dateUTC;
        this.start = start;
        this.end = end;       
        this.url = url;
        this.backgroundColor = backgroundColor;
        this.color = color;
        this.overlap = overlap;
        this.display = display;
        this.descricao = descricao;
        this.attendanceId = attendanceId;        
    }        
}

// https://www.baeldung.com/spring-data-jpa-query-by-date
// https://github.com/eugenp/tutorials/blob/master/persistence-modules/spring-data-jpa-query-3/src/main/resources/import_entities.sql