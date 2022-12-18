package com.lm.myagenda.dto;

import com.lm.myagenda.models.Item;
import com.lm.myagenda.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private List<ItemDTO> itens = new ArrayList<>();
    private BigDecimal totalPrice;
    private int quantItem;
    private String personNameAttended;

    public OrderDTO(Order entity){
        id = entity.getId();
        itens = itemDtoList(entity.getItens());
        totalPrice = entity.getTotalPrice();
        quantItem = entity.getQuantItem();
        personNameAttended = (entity.getAttendance() == null) ? null : entity.getAttendance().getPerson().getName();
    }

    public List<ItemDTO> itemDtoList(List<Item> itemList){
        List<ItemDTO> itemDtoList = new ArrayList<>();
        itemList.forEach(x -> itemDtoList.add(new ItemDTO(x)));
        return itemDtoList;
    }
}