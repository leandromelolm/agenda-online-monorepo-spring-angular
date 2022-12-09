package com.lm.myagenda.services;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lm.myagenda.models.scheduledService;
import com.lm.myagenda.models.Event;
import com.lm.myagenda.models.Person;
import com.lm.myagenda.repositories.EventRepository;
import com.lm.myagenda.repositories.PersonRepository;
import com.lm.myagenda.repositories.ScheduledServiceRepository;

@Service
public class DBService {

    @Autowired
    EventRepository er;

    @Autowired
    ScheduledServiceRepository ssr;

    @Autowired
    PersonRepository pr;

    public String currentDayPlusDays(int i){
        String datayyyymmdd = LocalDateTime.from(new Date().toInstant().atZone(ZoneId.of("GMT-3"))).plusDays(i).toString().substring(0, 10);
		System.out.println("data yyyy-mm-dd: "+datayyyymmdd); //data yyyy-mm-dd: 2022-12-10
        return datayyyymmdd;
    }
    
    public void instantiateTestDatabase() throws ParseException{
        
        DateTimeFormatter dtf = DateTimeFormatter.ISO_INSTANT;
        DateTimeFormatter dtfPatternLocalZone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.systemDefault());
        
        long now = System.currentTimeMillis()/1000;
        Instant instantNow = Instant.ofEpochSecond(now);
        Instant instantNow2= Instant.now();
        System.out.println(dtf.format(instantNow));  //2022-12-09T10:42:08Z
        System.out.println(dtf.format(instantNow2)); //2022-12-09T10:42:08.394563045Z

        Instant instant = Instant.parse(currentDayPlusDays(0)+"T10:45:00-03:00");

        String data = "1992-01-01";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate1 = LocalDate.parse(data,formatter);
        LocalDate birthdate2 = birthdate1.plusDays(80);
        LocalDate birthdate3 = birthdate1.plusDays(360);
        LocalDate birthdate4 = birthdate1.plusDays(120);
        
        Person p1 = new Person(null, "jose severino da silva filho junior", "01234567891", "123123412341231", "jose@email.com", "masculino", birthdate1, "0000001234", "area","anotação", "http://www.google.com", instantNow);
        pr.save(p1);
        Person p2 = new Person(null, "Sheri Almeida Kramer", "01234567892", "123123412341232", "skeri@email.com", "masculino", birthdate2, "0000001234", "area","anotação", "http://www.google.com", instantNow);
        pr.saveAndFlush(p2);
        Person p3 = new Person(null, "Cosmo Gomes Almeida", "01234567893", "123123412341233", "cosmo@email.com", "masculino", birthdate3, "0000001234", "area","anotação", "http://www.google.com", instantNow);
        pr.saveAndFlush(p3);
        Person p4 = new Person(null, "maria severina", "01234567894", "123123412341234", "maria@email.com", "feminino", birthdate4, "0000001234", "area","anotação", "http://www.google.com", instantNow);
        pr.saveAndFlush(p4);

        scheduledService servicoAgendado1 = new scheduledService(null, null, "Atendimento pendente de confirmação", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)), null, instantNow.toString());
        scheduledService sa2 = new scheduledService(null, null, "Atendimento confirmado", instantNow.plus(2,ChronoUnit.DAYS), dtfPatternLocalZone.format(instant), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)), null, instantNow.toString());
        scheduledService sa3 = new scheduledService(null, null, "Atendimento suspenso", instant.plus(5, ChronoUnit.DAYS), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(899,ChronoUnit.SECONDS)), null, instantNow.toString());  //899 segundos = 14min:59seg      
        scheduledService sa4 = new scheduledService(null, null, "Atendimento confirmado", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(29, ChronoUnit.MINUTES)), null, instantNow.toString());
        
        ssr.saveAllAndFlush(Arrays.asList(servicoAgendado1,sa2,sa3,sa4));
        
        List<Event>eventos = new ArrayList<>();

        Event event1 = new Event();
        event1.setTitle(p1.getName()+" cpf:"+p1.getCpf());
        event1.setDateUTC(instantNow.plus(2,ChronoUnit.DAYS));              
        event1.setStart(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)));
        event1.setEnd(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)));        
        event1.setScheduledServiceId(servicoAgendado1.getId());
        event1.setPersonBirthDate(p1.getBirthdate().toString());
        event1.setPersonCPF(p1.getCpf());
        event1.setPersonPhone("11911111111");
        eventos.add(event1);                
        
        Event event2 = new Event();
        event2.setTitle(p2.getName()+" cpf:"+p2.getCpf());
        event2.setDateUTC(sa2.getDateInUTC());
        event2.setStart(sa2.getHoraInicio());
        event2.setEnd(sa2.getHoraFim());
        event2.setDisplay("block");
        event2.setScheduledServiceId(sa2.getId());
        event2.setPersonBirthDate(p2.getBirthdate().toString());
        event2.setPersonCPF(p2.getCpf());
        event2.setPersonPhone("22922222222");
        eventos.add(event2);                  
        
        Event event3 = new Event(null, null, p3.getName(), sa3.getDateInUTC(), sa3.getHoraInicio(), sa3.getHoraFim(), null, null, null, false, "block", null, sa3.getId(),p3.getCpf(),"33933333333", p3.getBirthdate().toString()); 
        eventos.add(event3);        
        Event event4 = new Event(null, null, p4.getName(), sa4.getDateInUTC(), sa4.getHoraInicio(), sa4.getHoraFim(), null, null, null, false, "block", "descrição test", sa4.getId(), p4.getCpf(), "44944444444", p4.getBirthdate().toString());
        eventos.add(event4);

        er.saveAll(eventos);

    }
    
}