package com.lm.myagenda.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonNewDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String socialName;
    private String cpf;
    private String cns;
    private String emailAddress;
    private String gender;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;
    private String ine;
    private String area;
    private String note;
    private String urlImage;
    private Instant registerDate;
    //Address
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
    private String cep;
    private String observacao;
    private String tipo;
    //Phone
    private String ddd;
    private String Number;
    private String description;
    private String phoneType;

}