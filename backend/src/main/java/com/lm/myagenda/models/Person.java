package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String socialName;
    private String cpf;
    private String cns;
    private String emailAddress;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathdate;
    private String ine; // Identificado Nacional de Equipe
    private String area;
    private String note;
    private String urlImage;
    private Instant registerDate;
//    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Attendance> atendimentosAgendados = new ArrayList<>();
    @OneToMany(mappedBy = "person", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Address> enderecos = new ArrayList<>(); //addresses
    @OneToMany(mappedBy = "person",  cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<Phone> telefones = new ArrayList<>();

    public Person(Long id, String name, String socialName, String cpf, String cns, String emailAddress, String gender, LocalDate birthdate,
            String ine, String area, String note, String urlImage) {
        this.id = id;
        this.name = (name == null) ? null : name.toUpperCase().trim();
        this.socialName = (socialName == null) ? null : socialName.trim();
        this.cpf = cpf;
        this.cns = cns;
        this.emailAddress = (emailAddress == null) ? null : emailAddress.toLowerCase().trim();
        this.gender = gender;
        this.birthdate = birthdate;
        this.ine = ine;
        this.area = area;
        this.note = note;
        this.urlImage = urlImage;
    }

    public Person(String name, String cpf, String cns, LocalDate birthdate){
        this.name = name;
        this.cpf = cpf;
        this.cns = cns;
        this.birthdate = birthdate;
    }
}
