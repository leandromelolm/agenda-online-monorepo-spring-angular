package com.lm.myagenda.services;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import com.lm.myagenda.models.*;
import com.lm.myagenda.models.Order;
import com.lm.myagenda.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DBService {

    @Autowired
    EventRepository er;

    @Autowired
    AttendanceRepository atr;

    @Autowired
    PersonRepository pr;

    @Autowired
    AddressRepository addr;

    @Autowired
    PhoneRepository phr;

    @Autowired
    ProfessionalRepository pfr;

    @Autowired
    AgendaRepository agr;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

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

        //Item de serviço
        Item item1 = new Item(null, "Enfermagem", "vacinação Gripe", "vacina tipo", new BigDecimal("1.96"), true);
        Item item2 = new Item(null, "Coleta sanguinea", "Coleta colesterol Total", "coleta", new BigDecimal("1.10"), true);
        Item item3 = new Item(null, "Enfermagem", "Aferição", "Procedimento normal", new BigDecimal("1.05"), false);
        Item item4 = new Item(null, "Geral", "Acolhimento", "primeiro atendimento", new BigDecimal("0.01"), true);
        Item item5 = new Item(null, "Coleta sanguinea", "Coleta Glicemia", "descrição coleta sanguinea", new BigDecimal("0.52"), true);
        Item item6 = new Item(null, "Geral", "Consulta", "Consulta com especialista", new BigDecimal("0.30"), true);
        itemRepository.saveAll(Arrays.asList(item1,item2,item3,item4,item5,item6));

        //Professional
        Professional employee1 = new Professional(null, "nomeEmpregado1","01234567890","mat1101","Tec de enfermagem", "enf@email.com","descricao","statusATIVO",null, LocalDate.now().minusDays(10));
        Professional emp2 = new Professional(null, "nomeEmpregado2","01234567892","mat2022","tec de enfermagem", "enf2@email.com","descricao","statusATIVO",null, LocalDate.now().minusDays(500));
        Professional emp3 = new Professional(null, "nomeEmpregado3","01234567893","mat3330","tec de enfermagem", "enf3@email.com","descricao","statusATIVO",null, LocalDate.now().minusYears(10));
        Professional emp4 = new Professional(null, "nomeEmpregado4","01234567894","mat4144","tec de enfermagem", "enf4@email.com","descricao","statusATIVO",null, LocalDate.now().minusMonths(12));
        Professional emp5 = new Professional(null, "nomeEmpregado5","01234567895","mat5505","tec de enfermagem", "enf5@email.com","descricao","statusATIVO",null, LocalDate.parse("2013-09-28"));
        pfr.saveAll(Arrays.asList(employee1,emp2,emp3,emp4,emp5));

        //Person
        Person p1 = new Person(null, "jose severino da silva filho junior", "01234567891", "123123412341231", "jose@email.com", "masculino", birthdate1, "0000001234", "area","anotação", "url", instantNow);
//        pr.saveAndFlush(p1);
        Person p2 = new Person(null, "Sheri Almeida Kramer", "01234567892", "123123412341232", "skeri@email.com", "masculino", birthdate2, "0000001234", "area","anotação", "url", instantNow);
        Person p3 = new Person(null, "Cosmo Gomes Almeida", "01234567893", "123123412341233", "cosmo@email.com", "masculino", birthdate3, "0000001234", "area","anotação", "url", instantNow);
        Person p4 = new Person(null, "maria severina", "01234567894", "123123412341234", "maria@email.com", "feminino", birthdate4, "0000001234", "area","anotação", "url", instantNow);

        //Address
        Address end1 = new Address(null, "avenida principal teste","10","Complemento","Cidade Universitária","Recife","PE","Brasil", "11222111", "observacao", "Residencial", p1);
//        p1.getEnderecos().addAll(Arrays.asList(end1));
        Address end2 = new Address(null, "rua segundaria teste","2","Complemento","Cidade Universitária","Joao Pessoa","PB","Brasil", "11222111", "observacao", "Residencial", p2);
        Address end3 = new Address(null, "travessa terceira teste","300","Complemento","Bairro","Jaboatao","PE","Brasil", "11222111", "observacao", "Residencial", p3);
        Address end4 = new Address(null, "via 4 teste","4000","Complemento","bairro","Caruau","PE","Brasil", "11222111", "observacao", "Residência Principal", p4);
        Address end5 = new Address(null, "avenida 5","50","Complemento","Cidade Universitária","Recife","PE","Brasil", "11222111", "observacao", "Residencial", p4);

        //Phone
        Phone tel1 = new Phone(null,"11","999999999","telefone pessoal","pessoal",p1);
        Phone tel3 = new Phone(null,"90","900000000","telefone apenas ws","pessoal",p3);

        pr.saveAllAndFlush(Arrays.asList(p1,p2,p3,p4));
        addr.saveAll(Arrays.asList(end1,end2,end3,end4,end5));
        phr.saveAll(Arrays.asList(tel1, tel3));

        //Agenda
        Agenda agenda1 = new Agenda(null,"AgendaAAA","agenda do profissional 1","ativa","grupoAgenda",emp2);
        agr.save(agenda1);
        Agenda agenda2 = new Agenda(null,"BBB","agenda do profissional 2","ativa","grupoAgenda",emp3);
        agr.save(agenda2);
        Agenda agenda3 = new Agenda(null,"Dentista-DrCC",emp4.getNome(),"ativa","Dentista",emp4);
        agr.saveAndFlush(agenda3);
        Agenda agenda4 = new Agenda(null,"Médico-DrDDD", emp5.getNome(),"ativa","Médico",emp5);
        agr.save(agenda4);

        //Atendimentos
        Attendance atendimento1 = new Attendance(null, "descricao", "Atendimento pendente de confirmação", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)), "observacao", instantNow.toString(), p1);
        atendimento1.getServicedBy().addAll(Arrays.asList(employee1,emp2));
        atendimento1.setAgenda(agenda1);
        Attendance a2 = new Attendance(null, null, "Atendimento confirmado", instantNow.plus(2,ChronoUnit.DAYS), dtfPatternLocalZone.format(instant), dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)), null, instantNow.toString(), p2);
        a2.getServicedBy().addAll(Arrays.asList(emp3));
        a2.setAgenda(agenda2);
        Attendance a3 = new Attendance(null, null, "Atendimento suspenso", instant.plus(5, ChronoUnit.DAYS), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(899,ChronoUnit.SECONDS)), null, instantNow.toString(), p3);  //899 segundos = 14min:59seg
        a3.getServicedBy().addAll(Arrays.asList(emp4,emp5));
        a3.setAgenda(agenda2);
        Attendance a4 = new Attendance(null, null, "Atendimento confirmado", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(29, ChronoUnit.MINUTES)), null, instantNow.toString(), p4);
        a4.getServicedBy().addAll(Arrays.asList(emp4,emp2,emp5));
        a4.setAgenda(agenda2);
        Attendance a5 = new Attendance(null, null, "Atendimento confirmado", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(29, ChronoUnit.MINUTES)), null, instantNow.toString(), p2);
        a5.getServicedBy().addAll(Arrays.asList(emp4));
        a5.setAgenda(agenda3);
        Attendance a6 = new Attendance(null, null, "Atendimento confirmado", instant.plus(5,ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(15, ChronoUnit.MINUTES)), dtfPatternLocalZone.format(instant.plus(5, ChronoUnit.DAYS).plus(29, ChronoUnit.MINUTES)), null, instantNow.toString(), p3);
        a6.getServicedBy().addAll(Arrays.asList(emp4));
        a6.setAgenda(agenda3);

        atr.saveAllAndFlush(Arrays.asList(atendimento1,a2,a3,a4,a5,a6));

        //Order - Ordem de servico (lista de itens no atendimento)
        Order order1 = new Order(null,atendimento1,Arrays.asList(item1));
        Order order2 = new Order(null,a2,Arrays.asList(item2,item3));
        Order order3 = new Order(null,a3,Arrays.asList(item1,item2));
        Order order4 = new Order(null,a4,Arrays.asList(item3,item5,item4));
        Order order5 = new Order(null,null,Arrays.asList(item3));
        Order order6 = new Order(null,a5,Arrays.asList(item6));
        orderRepository.save(order1);
        orderRepository.saveAll(Arrays.asList(order2,order3,order4,order5,order6));

        //Events
        List<Event>eventos = new ArrayList<>();

        Event event1 = new Event();
        event1.setTitle(p1.getName()+" cpf:"+p1.getCpf());
        event1.setDateUTC(instantNow.plus(2,ChronoUnit.DAYS));              
        event1.setStart(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS)));
        event1.setEnd(dtfPatternLocalZone.format(instantNow.plus(2,ChronoUnit.DAYS).plus(14,ChronoUnit.MINUTES).plus(59, ChronoUnit.SECONDS)));        
        event1.setAttendanceId(atendimento1.getId());
        event1.setPersonBirthDate(p1.getBirthdate().toString());
        event1.setPersonCPF(p1.getCpf());
        event1.setPersonPhone("11911111111");
        eventos.add(event1);                
        
        Event event2 = new Event();
        event2.setTitle(p2.getName()+" cpf:"+p2.getCpf());
        event2.setDateUTC(a2.getDateInUTC());
        event2.setStart(a2.getHoraInicio());
        event2.setEnd(a2.getHoraFim());
        event2.setDisplay("block");
        event2.setAttendanceId(a2.getId());
        event2.setPersonBirthDate(p2.getBirthdate().toString());
        event2.setPersonCPF(p2.getCpf());
        event2.setPersonPhone("22922222222");
        eventos.add(event2);                  
        
        Event event3 = new Event(null, null, p3.getName(), a3.getDateInUTC(), a3.getHoraInicio(), a3.getHoraFim(), null, null, null, false, "block", null, a3.getId(),p3.getCpf(),"33933333333", p3.getBirthdate().toString());
        eventos.add(event3);        
        Event event4 = new Event(null, null, p4.getName(), a4.getDateInUTC(), a4.getHoraInicio(), a4.getHoraFim(), null, null, null, false, "block", "descrição test", a4.getId(), p4.getCpf(), "44944444444", p4.getBirthdate().toString());
        eventos.add(event4);

        er.saveAll(eventos);

    }
    
}
