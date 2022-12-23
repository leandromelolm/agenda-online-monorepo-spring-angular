package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professional implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String CPF;
    private String matricula;
    private String especialidade;
    private String email;
    private String descricao;
    private String status;
    private LocalDate dataAlteracaoStatus;
    private LocalDate dataCadastro;
    @JsonIgnore
    @OneToOne(mappedBy = "professional")
    private Agenda agenda;

    public Professional(Long id, String nome, String CPF, String matricula, String especialidade, String email, String descricao, String status, LocalDate dataAlteracaoStatus, LocalDate dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.CPF = CPF;
        this.matricula = matricula;
        this.especialidade = especialidade;
        this.email = email;
        this.descricao = descricao;
        this.status = status;
        this.dataAlteracaoStatus = dataAlteracaoStatus;
        this.dataCadastro = dataCadastro;
    }
}
