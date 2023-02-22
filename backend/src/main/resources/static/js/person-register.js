function savePerson() {

    var id = $("#id").val();
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

    if(id == null || !id.trim() == ''){
        editarPerson(id, name, socialName, cpf, cns, emailAddress, gender, birthdate);
        return;
    }

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

let currentPage = 0;    // pageable.pageNumber, number
let totalOfPages;       // totalPages
let linePerPage = 0;    // pageable.pageSize, size
let totalOfElementos;   // totalElements

function buttonSearchPerson(){
    let nome = $('#nameBusca').val();
    currentPage = 0;
    if (nome != null && nome.trim() != ''){
        pesquisarPerson();
    }else{
        alert("digite algum nome, cpf ou cns no campo");
        findAll();
    }
}

function pesquisarPerson(){
    let nome = $('#nameBusca').val();
    $.ajax({
        method : "GET",
        url : "person",
        data : "search=" + nome + "&page=" + currentPage + "&size=" + linePerPage,
        success : function(response) {

            preencherTabela(response);

            $('#numeracao').text('Página ' + (response.pageable.pageNumber + 1) + ' de ' + response.totalPages);
            currentPage = response.pageable.pageNumber;
            totalOfPages = response.totalPages;
            totalOfElementos = response.totalElements;
            linePerPage = response.pageable.pageSize;
            ajustarBotoesPaginacao();
        }
      }).fail(function(xhr, status, errorThrown) {
            alert("Erro ao buscar usuario: " + xhr.responseText);
      });
}

function findAll(){
    $.ajax({
        method : "GET",
//        url : "/myagenda/person?search=&page=0&size=10",
        url : "person",
        data : "page=0&size=5",
        success : function(response) {
            preencherTabela(response);
//            document.getElementById("numeracao").innerHTML ="Página "+(response.size + 1)+' de '+response.totalPages;
            $('#numeracao').text('Página ' + (response.pageable.pageNumber + 1) + ' de ' + response.totalPages);
            currentPage = response.pageable.pageNumber;
            totalOfPages = response.totalPages;
            totalOfElementos = response.totalElements;
            linePerPage = response.size;
            ajustarBotoesPaginacao();
            $('#nameBusca').val('');
        }
    }).fail(function(xhr, status, errorThrown) {
        alert("Erro ao buscar usuario: " + xhr.responseText);
    });
}

function ajustarBotoesPaginacao() {
    $('#proximo').prop('disabled', totalOfElementos <= linePerPage || currentPage >= totalOfPages - 1);
    $('#anterior').prop('disabled', totalOfElementos <= linePerPage || currentPage == 0);
}

$(function() {
    $('#proximo').click(function() {
        if (currentPage < totalOfPages) {
            currentPage++;
            pesquisarPerson();
            ajustarBotoesPaginacao();
        }
    });
    $('#anterior').click(function() {
        if (currentPage > 0) {
            currentPage--;
            pesquisarPerson();
            ajustarBotoesPaginacao();
        }
    });
});

function preencherFormParaEdicao(id) {
    $.ajax({
        method : "GET",
        url : "person/"+id,
        success : function(response) {

            $("#id").val(response.id);
            $("#name").val(response.name);
            $("#socialName").val(response.socialName);
            $("#cpf").val(response.cpf);
            $("#cns").val(response.cns);
            $("#emailAddress").val(response.emailAddress);
            $("#gender").val(response.gender);
            $("#birthdate").val(response.birthdate);

            $('#modalPesquisarPerson').modal('hide');

            ocultarDiv();
        }
    }).fail(function(xhr, status, errorThrown) {
        alert("Erro ao buscar usuario por id: " + xhr.responseText);
    });
}

function editarPerson(id, name, socialName, cpf, cns, emailAddress, gender, birthdate){
    let data = {
        "id" : id,
        "name" : name,
        "socialName" : socialName,
        "cpf" : cpf,
        "cns" : cns,
        "emailAddress" : emailAddress,
        "gender" : gender,
        "birthdate" : birthdate
    }

    $.ajax({
        method : "PUT",
        url : "person/"+id,
        data : JSON.stringify(data),
        contentType : "application/json; charset=utf-8",
        success : function(response) {
            alert("Salvo com sucesso!");
            cleanForm();
            exibirDiv();
        }
    }).fail(function(xhr, status, errorThrown) {
        alert("Erro ao salvar: " + xhr.responseText);
    });
}

function deletePerson(){
	var id = $('#id').val();
	if(id != null && id.trim() != ''){
	    deletePersonById(id);
	    document.getElementById('formCadastroPerson').reset();
	}
}

function deletePersonById(id){
	if(confirm('Deseja realmente deletar as informações do id '+ id +'?')) {
	    $.ajax({
			method : "DELETE",
			url : "person/"+id,
			success : function(response) {
				$('#'+ id).remove();
				alert(response);
				exibirDiv();
			}
		}).fail(function(xhr, status, errorThrown) {
			alert("Erro ao deletar usuario por id: " + xhr.responseText);
		});
	}
}

function ocultarDiv(){
    let el = document.querySelector('#fieldsAddressAndPhone');
    el.style.display = 'none';
}

function exibirDiv(){
    let el = document.getElementById('fieldsAddressAndPhone');
    el.style.display = 'block';
}

function cleanForm(){
    document.getElementById('formCadastroPerson').reset();
    exibirDiv();
}

function preencherTabela(response){
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
        '</td><td><button type="button" onclick="preencherFormParaEdicao('+response.content[i].id+')"'+
        'class="btn btn-primary" data-bs-toggle="collapse" data-bs-target=".multi-collapse"'+
        'aria-expanded="false">Selecionar</button></td>'+
        '<td><button type="button" class="btn btn-danger"'+
        'onclick="deletePersonById('+response.content[i].id+')">Delete</button></td></tr>');
    }
}