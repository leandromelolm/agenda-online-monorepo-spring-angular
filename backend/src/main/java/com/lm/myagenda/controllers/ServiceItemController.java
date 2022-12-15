package com.lm.myagenda.controllers;

import com.lm.myagenda.models.ServiceItem;
import com.lm.myagenda.services.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/api")
public class ServiceItemController {

    @Autowired
    ServiceItemService serviceItemService;
    
    @RequestMapping(value="/serviceitem/all", method = RequestMethod.GET)
    public ResponseEntity<List<ServiceItem>> test(){
        List<ServiceItem> itensList = serviceItemService.findAll();
        return ResponseEntity.ok().body(itensList);
    }
}
