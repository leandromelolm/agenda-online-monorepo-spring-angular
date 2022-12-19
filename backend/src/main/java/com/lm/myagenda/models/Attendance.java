package com.lm.myagenda.models;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @JoinTable(
            name = "professional_attendance",
            joinColumns = @JoinColumn(name = "attendance_id"),
            inverseJoinColumns = @JoinColumn(name = "professional_id")
    )
    private List<Professional> servicedBy = new ArrayList<>();
    @JsonIgnore
    @OneToOne(mappedBy = "attendance",fetch = FetchType.EAGER)
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

//    @JsonIgnore
//    @OneToMany(mappedBy = "attendance",fetch = FetchType.EAGER)
//    @Cascade(CascadeType.ALL)
//    private List<Order> procedimentos;