package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

//Registro de Atendimento
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String descricao;
    private String status;
    private Instant dateInUTC;
    private String startTime;
    private String endTime;
    private String observacao;
    private String dataRegistro;    
    @ManyToOne
    @JoinColumn(name="person_id")
    private Person person;
    @ManyToOne
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;
    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Professional> professionais = new HashSet<>();
    @JsonIgnore
    @OneToOne(mappedBy = "attendance",fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Order order;

    public Attendance(Long id, String descricao, String status, Instant dateInUTC, String horaInicio, String horaFim, String observacao, String dataRegistro, Person person) {
        this.id = id;
        this.descricao = descricao;
        this.status = status;
        this.dateInUTC = dateInUTC;
        this.startTime = horaInicio;
        this.endTime = horaFim;
        this.observacao = observacao;
        this.dataRegistro = dataRegistro;
        this.person = person;
    }
}