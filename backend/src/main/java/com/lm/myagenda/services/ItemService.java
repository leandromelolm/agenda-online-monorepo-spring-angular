package com.lm.myagenda.services;

import com.lm.myagenda.models.Item;
import com.lm.myagenda.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    ItemRepository ItemRepository;

    public List<Item> findAll() {
        return ItemRepository.findAll();
    }
    
}
