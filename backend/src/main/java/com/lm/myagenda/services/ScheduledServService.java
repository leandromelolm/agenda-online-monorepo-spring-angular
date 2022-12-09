package com.lm.myagenda.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lm.myagenda.models.scheduledService;
import com.lm.myagenda.repositories.ScheduledServiceRepository;

@Service
public class ScheduledServService {

    @Autowired
    ScheduledServiceRepository ssr;

    public List<scheduledService> findAll() {
        return ssr.findAll();
    }
    
}
