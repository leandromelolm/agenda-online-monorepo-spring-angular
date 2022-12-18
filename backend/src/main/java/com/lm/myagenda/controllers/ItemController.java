package com.lm.myagenda.controllers;

import com.lm.myagenda.models.Item;
import com.lm.myagenda.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class ItemController {

    @Autowired
    ItemService itemService;
    
    @RequestMapping(value="/item/all", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> findAll(){
        List<Item> itensList = itemService.findAll();
        return ResponseEntity.ok().body(itensList);
    }
}
