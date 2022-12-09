package com.lm.myagenda.services;

import java.text.ParseException;
import java.time.Instant;
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
import com.lm.myagenda.repositories.EventRepository;
import com.lm.myagenda.repositories.ScheduledServiceRepository;

@Service
public class DBService {

    @Autowired
    EventRepository er;

    @Autowired
    ScheduledServiceRepository ssr;

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

        scheduledService servicoAgendado1 = new scheduledService(null, null, "Atendimento pendente de confirmação", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)), null, instantNow.toString());
        scheduledService sa2 = new scheduledService(null, null, "Atendimento confirmado", instantNow.plus(2,ChronoUnit.DAYS), dtfPatternLocalZone.format(instant), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)), null, instantNow.toString());
        scheduledService sa3 = new scheduledService(null, null, "Atendimento suspenso", instant.plus(5, ChronoUnit.DAYS), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(899,ChronoUnit.SECONDS)), null, instantNow.toString());  //899 segundos = 14min:59seg      
        scheduledService sa4 = new scheduledService(null, null, "Atendimento confirmado", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(29, ChronoUnit.MINUTES)), null, instantNow.toString());
        
        ssr.saveAllAndFlush(Arrays.asList(servicoAgendado1,sa2,sa3,sa4));
        
        List<Event>eventos = new ArrayList<>();

        Event event1 = new Event();
        event1.setTitle("event1 test");
        event1.setDateUTC(instantNow.plus(2,ChronoUnit.DAYS));              
        event1.setStart(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)));
        event1.setEnd(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)));        
        event1.setScheduledServiceId(servicoAgendado1.getId());
        eventos.add(event1);                
        
        Event event2 = new Event();
        event2.setTitle("event2 test");
        event2.setDateUTC(sa2.getDateInUTC());
        event2.setStart(sa2.getHoraInicio());
        event2.setEnd(sa2.getHoraFim());
        event2.setDisplay("block");
        event2.setScheduledServiceId(sa2.getId());
        eventos.add(event2);                  
        
        Event event3 = new Event(null, null, "Joanna Darrrk Madureira Almeida", sa3.getDateInUTC(), sa3.getHoraInicio(), sa3.getHoraFim(), null, null, null, false, "block", null, sa3.getId()); 
        eventos.add(event3);
        
        Event event4 = new Event(null, null, "usuario event4", sa4.getDateInUTC(), sa4.getHoraInicio(), sa4.getHoraFim(), null, null, null, false, "block", "descrição test", sa4.getId(), "01234567890", "11988880000", "1992-10-12");
        eventos.add(event4);

        er.saveAll(eventos);

    }
    
}
