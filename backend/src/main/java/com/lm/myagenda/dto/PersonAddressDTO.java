package com.lm.myagenda.dto;

import com.lm.myagenda.models.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PersonAddressDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
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
    private List<AddressDTO> addresses = new ArrayList<>();

    public PersonAddressDTO(Person p) {
        this.id = p.getId();
        this.name = p.getName();
        this.cpf = p.getCpf();
        this.cns = p.getCns();
        this.emailAddress = p.getEmailAddress();
        this.gender = p.getGender();
        this.birthdate = p.getBirthdate();
        this.ine = p.getIne();
        this.area = p.getArea();
        this.note = p.getNote();
        this.urlImage = p.getUrlImage();
        this.registerDate = p.getRegisterDate();
        this.addresses = p.getEnderecos().stream().map(x -> new AddressDTO(x)).collect(Collectors.toList());
    }
}
