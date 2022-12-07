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

import ch.qos.logback.core.net.SyslogOutputStream;

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
        Instant instant2= Instant.now();
        Instant instant = Instant.parse(formatDateStringPlusDay(1)+"T10:45:00-03:00");   
        DateTimeFormatter dtf = DateTimeFormatter.ISO_INSTANT; 
        DateTimeFormatter dtfPatternLocalZone = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.systemDefault()); 
        System.out.println(dtf.format(instant2)); // 2022-12-07T14:50:48.522974564Z     

        List<Event>eventos = new ArrayList<>();

        Event event = new Event();
        event.setTitle("event1 test");
        event.setDate(instantNow.plus(2,ChronoUnit.DAYS));              
        event.setStart(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)));
        event.setEnd(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)));
        eventos.add(event);                
        
        Event event2 = new Event();
        event2.setTitle("event2 test");
        event2.setDate(instant);
        event2.setStart(dtfPatternLocalZone.format(instant));
        event2.setEnd(dtfPatternLocalZone.format(instant.plus(14,ChronoUnit.MINUTES)));
        eventos.add(event2);           
        event2.setDisplay("block");
        //(UUID id, String groupId, String title, String start, String end, Instant date, String url, String backgroundColor, String color, boolean overlap, String display, String descricao, Long servicoId)
        Event event3 = new Event(null, null, "Joanna Darrk Madureira Almeida", dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(899,ChronoUnit.SECONDS)), instant.plus(5, ChronoUnit.DAYS), null, null, null, false, "block", null, null); //899 segundos = 14min:59seg
        eventos.add(event3);

        er.saveAll(eventos);

    }
    
}
