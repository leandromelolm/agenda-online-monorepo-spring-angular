package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
public class Professional implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "O campo NOME não pode está em branco")
    @Size(min=5, max=50, message="O campo NOME deve ter entre 5 e 50 caracteres")
    private String nome;
    @Column(unique=true)
    private String cpf;
    @Column(unique=true)
    private String matricula;
    private String especialidade;
    @Column(unique=true)
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
