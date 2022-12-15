package com.lm.myagenda.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = true)
    private Attendance attendance;

    @ManyToMany
    @Cascade(CascadeType.MERGE)
    private List<Item> itens = new ArrayList<>();

    private BigDecimal totalPrice;

    public Order(Long id, Attendance attendance, List<Item> itens) {
        this.id = id;
        this.attendance = attendance;
        this.itens = itens;
        this.totalPrice = getValorTotal();
    }
    @JsonIgnore
    public BigDecimal getValorTotal() {
        BigDecimal sum = new BigDecimal(0);
        for (Item ip : itens) {
            sum = sum.add(ip.getPrice());
        }
        return sum;
    }
}


//    @ManyToOne(optional = true)
//    private Attendance attendance;