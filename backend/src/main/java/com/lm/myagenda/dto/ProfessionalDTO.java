package com.lm.myagenda.dto;

import com.lm.myagenda.models.Professional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String matricula;
    private String especialidade;
    private String email;
    private String descricao;
    private String status;
    private LocalDate dataAlteracaoStatus;
    private LocalDate dataCadastro;


    public ProfessionalDTO(Professional entity){
        this.id = entity.getId();
        this.nome = entity.getNome();
        this.cpf = entity.getCpf();
        this.matricula = entity.getMatricula();
        this.especialidade = entity.getEspecialidade();
        this.email = entity.getEmail();
        this.descricao = entity.getDescricao();
        this.status = entity.getStatus();
        this.dataAlteracaoStatus = entity.getDataAlteracaoStatus();
        this.dataCadastro = entity.getDataCadastro();
    }

    public Set<ProfessionalDTO> converterEntityToDto(Set<Professional> professional){
        Set<ProfessionalDTO> professionalDtoList = new HashSet<>();
        professional.forEach(x -> professionalDtoList.add(new ProfessionalDTO(x)));
        return professionalDtoList;
    }
}