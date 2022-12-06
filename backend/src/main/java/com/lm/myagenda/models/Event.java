package com.lm.myagenda.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Event implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;    

    private String groupId;

    private String title; 
    
    @Column(name = "time_start")
    private Calendar start;
   
    @Column(name = "time_end")
    private Calendar end;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Calendar date;

    private String url;

    private String backgroundColor;

    private String color;

    private boolean overlap;

    private String display;

    private String descricao;

    private Long servicoId;
    
    public Event(){}

    public Event(UUID id, String groupId, String title, Calendar start, Calendar end, Calendar date, String url,
            String backgroundColor, String color, boolean overlap, String display, String descricao, Long servicoId) {
        this.id = id;
        this.groupId = groupId;
        this.title = title;
        this.start = start;
        this.end = end;
        this.date = date;
        this.url = url;
        this.backgroundColor = backgroundColor;
        this.color = color;
        this.overlap = overlap;
        this.display = display;
        this.descricao = descricao;
        this.servicoId = servicoId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isOverlap() {
        return overlap;
    }

    public void setOverlap(boolean overlap) {
        this.overlap = overlap;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getServicoId() {
        return servicoId;
    }

    public void setServicoId(Long servicoId) {
        this.servicoId = servicoId;
    }
        
}

// https://www.baeldung.com/spring-data-jpa-query-by-date
// https://github.com/eugenp/tutorials/blob/master/persistence-modules/spring-data-jpa-query-3/src/main/resources/import_entities.sql