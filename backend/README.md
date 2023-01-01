
## MyAgenda - Backend

#### PERSON GET ENDPOINT
###### person summary with name, cpf, cns, birthdate
###### http://localhost:8080/api/person?search= [all paged]
###### http://localhost:8080/api/person?search= {name} or {cpf} or {cns}
###### http://localhost:8080/api/person/all/summary

###### person with address, phone and attendance
###### http://localhost:8080/api/person/search [all]
###### http://localhost:8080/api/person/search?name=gomes [search by name]

###### person with address
###### http://localhost:8080/api/person/ {personId} [find by id]
###### http://localhost:8080/api/person/persons/address
###### http://localhost:8080/api/person/all/address

#### PERSON DELETE ENDPOINT
###### http://localhost:8080/api/person/ {personId}

### JSON EXAMPLES

#### PERSON INSERT
###### POST http://localhost:8080/api/person

    {    
        "name": "personNew test1",
        "socialName": null,
        "cpf": "01234567890",
        "cns": "123123412341233",
        "emailAddress": "person1@email.com",
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

#### PERSON UPDATE
######  PUT http://localhost:8080/api/person/{personId}
    // Mesmo passando o CPF no json, ele não é alterado.

    {    
        "name": "NAMETest testUPDATE",
        "socialName": "TestSocialNameUpdate",
        "cpf": "00000000000",
        "cns": "123123412341233",
        "emailAddress": "test4@email.com",
        "gender": "masculino",
        "birthdate": "1992-12-26",
        "ine": "0000001234",
        "area": "area",
        "note": "anotação",
        "urlImage": "url"    
    }

#### PERSON ADDRESS UPDATE
######  PUT http://localhost:8080/person/{personId}/address/{addressId}
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
