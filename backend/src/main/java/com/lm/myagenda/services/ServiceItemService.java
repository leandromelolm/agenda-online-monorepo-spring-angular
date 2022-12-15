package com.lm.myagenda.services;

import com.lm.myagenda.models.ServiceItem;
import com.lm.myagenda.repositories.ServiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceItemService {

    @Autowired
    ServiceItemRepository serviceItemRepository;

    public List<ServiceItem> findAll() {
        return serviceItemRepository.findAll();
    }
    
}
