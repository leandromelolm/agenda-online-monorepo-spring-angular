package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Professional implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private String matricula;
    private String especialidade;
    private String email;
    private String descricao;
    private String status;
    private Instant dataAlteracaoStatus;
    private Instant dataCadastro;
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_id", referencedColumnName = "id")
    @ToString.Exclude
    private Agenda agenda;

    public Professional(Long id, String nome, String cpf, String matricula, String especialidade, String email, String descricao, String status, Instant dataAlteracaoStatus, Instant dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.especialidade = especialidade;
        this.email = email;
        this.descricao = descricao;
        this.status = status;
        this.dataAlteracaoStatus = dataAlteracaoStatus;
        this.dataCadastro = dataCadastro;
    }

    public Professional(Long id, String nome, String cpf, String matricula, String especialidade){
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.matricula = matricula;
        this.especialidade = especialidade;
    }
}
