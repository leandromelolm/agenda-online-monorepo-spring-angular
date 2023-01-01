package com.lm.myagenda.dto;

import com.lm.myagenda.models.Address;
import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.models.Phone;
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
public class PersonDTO implements Serializable {
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
    private List<AttendanceDTO> attendances = new ArrayList<>();
    private List<AddressDTO> addresses = new ArrayList<>();
    private List<Phone> phones = new ArrayList<>();

    public PersonDTO(Person p) {
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
        this.phones = p.getTelefones();
    }

    public PersonDTO(Person p, List<Attendance> attendances){
        this(p);
        attendances.forEach(x -> this.attendances.add(new AttendanceDTO(x)));
    }
}
