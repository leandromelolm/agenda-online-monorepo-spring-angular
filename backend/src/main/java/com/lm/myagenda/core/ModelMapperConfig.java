package com.lm.myagenda.core;

import com.lm.myagenda.dto.AddressDTO;
import com.lm.myagenda.dto.AttendanceDTO;
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
                .addMapping(Person::getAddresses, PersonDTO::setEnderecos)
//                .<List<AddressDTO>>addMapping(source -> source.getAddresses(),(dest, value) -> dest.setEnderecos(value));
                .addMapping(src -> src.getAttendances(),
                        (dest, value) -> dest.setAtendimentos((List<AttendanceDTO>) value));
        return modelMapper;
    }
}
