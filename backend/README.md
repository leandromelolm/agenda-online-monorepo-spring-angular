
## MyAgenda - Backend

#### Agenda-online
The route `http://localhost:8080/myagenda/agenda-online.html` 
opens html page agenda-online made in html and javascript with import from fullcalendar, Datepicker JQuery and Bootstrap.
___
#### Used Tecnology
- Java 17
- Spring Framework
- Spring Boot 3.0.4
- Spring Security
- Lombok
- ModelMapper
- H2 (database to test)

___
#### Person Endpoints

All person. List paged (person summary: name, cpf, cns and birthdate) [GET]
```
http://localhost:8080/myagenda/person
```

Person search (person summary) [GET]
```
http://localhost:8080/myagenda/person?search={name||cpf||cns}
```

All person with address. List paged [GET]
```
http://localhost:8080/myagenda/person/persons/address
```

Person with address find by id [GET]
```
http://localhost:8080/myagenda/person/{id}
```

Person Delete by id [DELETE]
http://localhost:8080/myagenda/person/{id}

Person Create
 [POST] http://localhost:8080/myagenda/person
```json

{    
    "name": "personNew create",
    "socialName": null,
    "cpf": "01234567890",
    "cns": "123123412341233",
    "emailAddress": "personnewcreate@email.com",
    "gender": "masculino",
    "birthdate": "1992-12-26",
    "ine": "0000001234",
    "area": "area",
    "note": "anotação",
    "urlImage": "url",
    "logradouro": "Avenida Paulista",
    "numero": "300",
    "complemento": "Complemento",
    "bairro": "Bairro",
    "cidade": "São paulo",
    "cep": "11222111",
    "observacao": "observacao",
    "tipo": "Residencial",
    "estado": "SP",
    "pais": "Brasil",
    "ddd": "11",
    "description": "Novo usuario Criado",
    "phoneType": "pessoal",
    "number": "971111112"       
}
    
```

Person Update
[PUT] http://localhost:8080/myagenda/person/{personId} <br>
Obs: Mesmo passando o CPF no json, ele não é alterado.
```json lines
{    
    "name": "person test update",
    "socialName": "TestSocialNameUpdate",
    "cpf": "00000000000",
    "cns": "123123412341233",
    "emailAddress": "persontestupdate@email.com",
    "gender": "masculino",
    "birthdate": "1992-12-26",
    "ine": "0000001234",
    "area": "area",
    "note": "anotação",
    "urlImage": "url"    
}
```
Person Address Update 
[PUT] http://localhost:8080/myagenda/person/{personId}/address/{addressId}
```json   
{    
    "logradouro": "rua atualizada",
    "numero": "2",
    "complemento": "Complemento atualizado",
    "bairro": "São Francisco",
    "cidade": "Boa vista",
    "cep": "11222111",
    "observacao": "observacao atualizado",
    "tipo": "Atualizado",
    "estado": "RO",
    "pais": "Brasil"
}
```
___
#### Event Endpoints

Event Create [POST] http://localhost:8080/myagenda/event
```json
{
    "groupId": null,
    "title": "LAURA AZEVEDO SEVERINA TEST",
    "dateUTC": "2023-02-14T07:00:00Z",
    "start": "2023-02-14T10:00:00",
    "end": "2023-02-14T10:14:00",
    "url": null,
    "backgroundColor": null,
    "color": null,
    "overlap": false,
    "display": "block",
    "descricao": "descrição4",        
    "personCPF": "44232636021",
    "personPhone": "44944444444",
    "personBirthDate": "1992-04-30"
}
```
