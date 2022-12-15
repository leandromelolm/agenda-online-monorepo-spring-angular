package com.lm.myagenda.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
public class ServiceItemPK implements Serializable {
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "attendace_id")
    private Attendance attendance;
    @ManyToOne
    @JoinColumn(name = "procedure_id")
    private Procedure procedure;
}
