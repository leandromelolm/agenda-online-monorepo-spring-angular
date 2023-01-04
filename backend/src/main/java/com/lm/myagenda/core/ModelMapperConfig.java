package com.lm.myagenda.core;

import com.lm.myagenda.dto.AddressDTO;
import com.lm.myagenda.dto.PersonDTO;
import com.lm.myagenda.models.Person;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Person.class, PersonDTO.class)
                .<List<AddressDTO>>addMapping(x -> x.getEnderecos(), (dest, value) -> dest.setAddresses(value));

        return modelMapper;
    }
}
