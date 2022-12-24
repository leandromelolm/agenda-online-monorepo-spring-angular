package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameAgenda;
    private String description;
    private String status;
    private String groupAgenda;
    private String agendaOwnerName;
    private Long agendaOwnerId;
    private String agendaOwnerMat;
    @JsonIgnore
    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL)
    private List<Attendance> attendances = new ArrayList<>();

    public Agenda(Long id, String nameAgenda, String description, String status, String groupAgenda, Professional professional) {
        this.id = id;
        this.nameAgenda = nameAgenda;
        this.description = description;
        this.status = status;
        this.groupAgenda = groupAgenda;
        this.agendaOwnerName = professional.getNome();
        this.agendaOwnerId = professional.getId();
        this.agendaOwnerMat = professional.getMatricula();
    }
}
