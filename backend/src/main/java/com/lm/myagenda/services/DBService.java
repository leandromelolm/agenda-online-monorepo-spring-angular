package com.lm.myagenda.services;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lm.myagenda.models.Event;
import com.lm.myagenda.repositories.EventRepository;

@Service
public class DBService {

    @Autowired
    EventRepository er;

    public String formatDateStringPlusDay(int i){
        String datayyyymmdd = LocalDateTime.from(new Date().toInstant().atZone(ZoneId.of("GMT-3"))).plusDays(i).toString().substring(0, 10);
		System.out.println("datayyyymmdd: "+datayyyymmdd); //format yyyy-MM-DD
        return datayyyymmdd;
    }

    public void instantiateTestDatabase() throws ParseException{
        
        long now = System.currentTimeMillis();
        now = now/1000;
        Instant instantNow = Instant.ofEpochSecond(now);
        Instant instantNow2= Instant.now();
        Instant instant = Instant.parse(formatDateStringPlusDay(1)+"T10:45:00-03:00");   
        DateTimeFormatter dtf = DateTimeFormatter.ISO_INSTANT; 
        DateTimeFormatter dtfPatternLocalZone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.systemDefault()); 
        
        System.out.println(dtf.format(instantNow));  //2022-12-07T15:28:40Z
        System.out.println(dtf.format(instantNow2)); //2022-12-07T14:50:48.522974564Z    

        List<Event>eventos = new ArrayList<>();

        Event event = new Event();
        event.setTitle("event1 test");
        event.setDateUTC(instantNow.plus(2,ChronoUnit.DAYS));              
        event.setStart(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)));
        event.setEnd(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)));
        eventos.add(event);                
        
        Event event2 = new Event();
        event2.setTitle("event2 test");
        event2.setDateUTC(instant);
        event2.setStart(dtfPatternLocalZone.format(instant));
        event2.setEnd(dtfPatternLocalZone.format(instant.plus(14,ChronoUnit.MINUTES)));
        eventos.add(event2);           
        event2.setDisplay("block");
        
        Event event3 = new Event(null, null, "Joanna Darrk Madureira Almeida", instant.plus(5, ChronoUnit.DAYS), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(899,ChronoUnit.SECONDS)), null, null, null, false, "block", null, null); //899 segundos = 14min:59seg
        eventos.add(event3);

        Event event4 = new Event(null, null, "usuario event4", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(29, ChronoUnit.MINUTES)), null, null, null, false, "block", "descrição test", 8000L, "01234567890", "11988880000", "1992-10-12");
        eventos.add(event4);

        er.saveAll(eventos);

    }
    
}
