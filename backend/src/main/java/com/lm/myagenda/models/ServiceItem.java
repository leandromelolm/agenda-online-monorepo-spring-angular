package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private ServiceItemPK id = new ServiceItemPK();
    private Long atendimentoId;
    private String procedimento;

    
    public ServiceItem(Attendance attendance, Procedure procedure){
        id.setAttendance(attendance);
        id.setProcedure(procedure);
        this.atendimentoId = attendance.getId();
        this.procedimento = procedure.getName();
    }
}
