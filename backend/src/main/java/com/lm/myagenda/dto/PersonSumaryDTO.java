package com.lm.myagenda.dto;

import com.lm.myagenda.models.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class PersonSumaryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String cpf;
    private String cns;
    private String emailAddress;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    public PersonSumaryDTO(Person p) {
        this.id = p.getId();
        this.name = p.getName();
        this.cpf = p.getCpf();
        this.cns = p.getCns();
        this.emailAddress = p.getEmailAddress();
        this.birthdate = p.getBirthdate();
    }
}
