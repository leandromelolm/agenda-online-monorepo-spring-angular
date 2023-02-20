function savePerson() {

    var name = $("#name").val();
    var socialName = $("#socialName").val();
    var cpf = $("#cpf").val();
    var cns = $("#cns").val();
    var emailAddress = $("#emailAddress").val();
    var gender = $("#gender").val();
    var birthdate = $("#birthdate").val();
    var logradouro = $("#logradouro").val();
    var numero = $("#numero").val();
    var complemento = $("#complemento").val();
    var bairro = $("#bairro").val();
    var cidade = $("#cidade").val();
    var cep = $("#cep").val();
    var observacao = $("#observacao").val();
    var tipo = $("#tipo").val();
    var estado = $("#estado").val();
    var pais = $("#pais").val();
    var phoneType = $("#phoneType").val();
    var ddd = $("#ddd").val();
    var number = $("#number").val();
    var description = $("#description").val();

    if (name == null || name != null && name.trim() == ''){
        $("#nome").focus();
        alert('Informe o nome');
        return;
    }

    if (cpf == null || cpf != null && cpf.trim() == ''){
        $("#cpf").focus();
        alert('Informe o cpf');
        return;
    }

    if (cns == null || cns != null && cns.trim() == ''){
        $("#cns").focus();
        alert('Informe o cns');
        return;
    }

    $.ajax({
        method : "POST",
        url : "person",
        data : JSON.stringify({
            name : name,
            socialName : socialName,
            cpf : cpf,
            cns : cns,
            emailAddress : emailAddress,
            gender : gender,
            birthdate : birthdate,
            logradouro : logradouro,
            numero : numero,
            complemento : complemento,
            bairro : bairro,
            cidade : cidade,
            cep : cep,
            observacao : observacao,
            tipo : tipo,
            estado : estado,
            pais : pais,
            phoneType : phoneType,
            ddd : ddd,
            number : number,
            description : description
        }),
        contentType : "application/json; charset=utf-8",
        success : function(response) {
            alert("Salvo com sucesso!");
        }
    }).fail(function(xhr, status, errorThrown) {
        alert("Erro ao salvar: " + xhr.responseText);
    });
}

function pesquisarPerson(){
  var nome = $('#nameBusca').val();
  if (nome != null && nome.trim() != ''){
    $.ajax({
        method : "GET",
//        url : "/myagenda/person",
        url : "person",
        data : "search=" + nome,
        success : function(response) {

            $('#tabelaresultados > tbody > tr').remove();

            for (var i = 0; i < response.content.length; i++){
                $('#tabelaresultados > tbody').append(
                '<tr id="'+response.content[i].id+
                '"><td>'+response.content[i].id+
                '</td><td>'+response.content[i].name+
                '</td><td>'+response.content[i].cpf+
                '</td><td>'+response.content[i].cns+
                '</td><td>'+response.content[i].emailAddress+
                '</td><td>'+response.content[i].birthdate+
                '</td></tr>');
            }
        }
      }).fail(function(xhr, status, errorThrown) {
            alert("Erro ao buscar usuario: " + xhr.responseText);
      });
  }else{
  alert("digite algum nome, cpf ou cns no campo")
  }
}