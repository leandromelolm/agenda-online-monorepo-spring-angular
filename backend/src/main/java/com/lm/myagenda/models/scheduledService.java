package com.lm.myagenda.models;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class scheduledService implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
	@GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String descricao;
    private String status;
    private Instant dateInUTC;
    private String horaInicio;
    private String horaFim;
    private String observacao;
    private String dataRegistro;  
}
