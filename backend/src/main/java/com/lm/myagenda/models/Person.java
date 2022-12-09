package com.lm.myagenda.models;

import java.io.Serializable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
	@GeneratedValue(strategy= GenerationType.AUTO)     
    private Long id;

    private String name;
    private String cpf;
    private String cns;
    private String emailAddress;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;    
    private String ine; // Identificado Nacional de Equipe
    private String area;
    private String note;
    private String urlImage;
    private Instant registerDate;
    @JsonIgnore
    @OneToMany(mappedBy = "person")    
    private List<Attendance> atendimentosAgendados = new ArrayList<>();

    public Person(Long id, String name, String cpf, String cns, String emailAddress, String gender, LocalDate birthdate,
            String ine, String area, String note, String urlImage, Instant registerDate) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.cns = cns;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.birthdate = birthdate;
        this.ine = ine;
        this.area = area;
        this.note = note;
        this.urlImage = urlImage;
        this.registerDate = registerDate;
    }
}
