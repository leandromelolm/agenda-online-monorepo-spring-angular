package com.lm.myagenda.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lm.myagenda.models.Attendance;
import com.lm.myagenda.models.Professional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String status;
    private Instant dateInUTC;
    private String horaInicio;
    private String horaFim;
    private String observacao;
    private String nomePessoaAtendida;
    private String nomeAgenda;
    private int funcionariosAtendendo;
    private Set<String> funcionariosNomes;
    private BigDecimal orderTotalPrice;
    private List<ItemDTO> orderItens;

    @JsonIgnore
    ItemDTO itemDTO = new ItemDTO();

    public AttendanceDTO(Attendance entity){
        id = entity.getId();
        status = entity.getStatus();
        dateInUTC = entity.getDateInUTC();
        horaInicio = entity.getStartTime();
        horaFim = entity.getEndTime();
        observacao = entity.getObservacao();
        nomePessoaAtendida = entity.getPerson().getName();
        nomeAgenda = entity.getAgenda().getNameAgenda();
        funcionariosAtendendo = entity.getProfessionais().size();
        funcionariosNomes = employeeNameList(entity.getProfessionais());
        orderTotalPrice = (entity.getOrder() == null ) ? null : entity.getOrder().getTotalPrice();
        orderItens = (entity.getOrder() == null) ? null : itemDTO.itemDtoList(entity.getOrder().getItens());
    }

    public Set<String> employeeNameList(Set<Professional> professionalList){
        Set<String> strList = new HashSet<>();
        professionalList.forEach(x -> strList.add(x.getNome()));
        return strList;
    }

    public List<String> employeeNameList2(List<Professional> professionalList){
        List<String> stringList = new ArrayList<>();
        for (Professional p: professionalList) {
            System.out.println(p.getNome());
            stringList.add(p.getNome());
        }
        return stringList;
    }
}
