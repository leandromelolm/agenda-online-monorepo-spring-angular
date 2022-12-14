package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameAgenda;
    private String description;
    private String status;
    private String groupAgenda;
    @JsonIgnore
    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL)
    private List<Attendance> attendances = new ArrayList<>();
    @OneToOne(cascade = {CascadeType.DETACH})
    @JoinColumn(name = "professional_id", referencedColumnName = "id")
    private Professional professional;

    public Agenda(Long id, String nameAgenda, String description, String status, String groupAgenda, Professional professional) {
        this.id = id;
        this.nameAgenda = nameAgenda;
        this.description = description;
        this.status = status;
        this.groupAgenda = groupAgenda;
        this.professional = professional;
    }
}
