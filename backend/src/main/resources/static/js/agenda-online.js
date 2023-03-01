
let selectedDate = new Date();
$('#dataSelecionada').text(formataDataParaDDMMYYYY(new Date()));

let infoViewType = 'dayGridMonth';
document.getElementById("tipoGradeSelecionada").innerHTML = "<div>Grade em exibição: <b>Mês</b></div>";

const url = "http://localhost:8080/myagenda/";

callFullCalendar(selectedDate);

$('#datePicker').datepicker({
    showOtherMonths: true,
    selectOtherMonths: true,
    changeMonth: true,
    changeYear: true,
    dateFormat: 'yy-mm-dd',

    onSelect: function (dateText, inst) {
        if(infoViewType == "dayGridMonth"){
            infoViewType = "timeGridDay";
        }
        selectedDate = new Date(dateText);
        selectedDate.setDate(selectedDate.getDate() + 1);
        $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
        callFullCalendar(selectedDate);
    },
})

function callFullCalendar(date) {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialDate: date,
        locale: 'pt-BR',
        initialView: infoViewType,
        editable: false, // impedir que o evento seja arrastado
        selectable: true,
        businessHours: false,
        businessHours: {
            daysOfWeek: [1, 2, 3, 4, 5],
            startTime: '7:30',
            endTime: '16:30',
        },
        headerToolbar: {
            left: '', // left: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth,listYear',
            center:'title',
            right: 'prev next'
        },
        allDaySlot:false,
        dayMaxEvents: true, // allow "more" link when too many events
        slotDuration: '00:15:00', slotMinTime: '07:00', slotMaxTime: '17:15', slotLabelInterval: '00:15',
        slotLabelFormat: [{ hour: '2-digit', minute: '2-digit' },],

        events: {
            url: url+'event'+'?size=200',
        },

        dateClick: function(info){
            if(info.view.type =='dayGridMonth'){
                infoViewType = 'timeGridDay';
                selectedDate = info.date;
                calendar.changeView('timeGridDay', info.dateStr);
                $("#datePicker").datepicker("setDate", info.dateStr);
                $('#dataSelecionada').text(formataDataParaDDMMYYYY(info.date));
            }
            if (info.view.type == 'timeGridDay' || info.view.type == 'timeGridWeek') {
                if(info.view.type == 'timeGridWeek'){
                    infoViewType = 'timeGridWeek';
                }
                if(info.view.type == 'timeGridDay'){
                    infoViewType = 'timeGridDay';
                }
                openModalThatCreateEvent(info.dateStr);
            }
        },

    });
    console.log("calendar.render()")
    calendar.render();
};

function formataDataParaDDMMYYYY(date){
    return ("0" + date.getDate()).slice(-2) + '/' + ("0" + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear();
}

function formataDataParaYYYYMMDD(data){
    return data.getFullYear() + "-" + ("0" + (data.getMonth() + 1)).slice(-2) + "-" + ("0" + data.getDate()).slice(-2);
}

function datePrev() {
    selectedDate.setDate(selectedDate.getDate() - 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
    callFullCalendar(formataDataParaYYYYMMDD(selectedDate));
};

function dateNext() {
    selectedDate.setDate(selectedDate.getDate() + 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
    callFullCalendar(formataDataParaYYYYMMDD(selectedDate));
};

$('body').on('click', '.fc-prev-button', function() {
    if(infoViewType == "timeGridDay"){
        selectedDate.setDate(selectedDate.getDate() - 1)
    }
    if(infoViewType == "timeGridWeek"){
        selectedDate.setDate(selectedDate.getDate() - 7)
    }
    if(infoViewType == "dayGridMonth"){
        selectedDate.setMonth(selectedDate.getMonth() - 1)
    }
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
});

$('body').on('click', '.fc-next-button', function() {
    if(infoViewType == "timeGridDay"){
        selectedDate.setDate(selectedDate.getDate() + 1)
    }
    if(infoViewType == "timeGridWeek"){
        selectedDate.setDate(selectedDate.getDate() + 7)
    }
    if(infoViewType == "dayGridMonth"){
        selectedDate.setMonth(selectedDate.getMonth() + 1)
    }
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
});

function diaAtual(){
    selectedDate = new Date();
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(new Date()));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(new Date()));
    callFullCalendar(new Date());
}

function viewtimeGridMonth(){
    infoViewType = 'dayGridMonth';
    document.getElementById("tipoGradeSelecionada").innerHTML = "<div>Grade selecionada: <b>Mês</b></div>";
    callFullCalendar(selectedDate);
}

function viewtimeGridWeek(){
    document.getElementById("tipoGradeSelecionada").innerHTML = "<div>Grade selecionada: <b>Semana</b></div>";
    infoViewType = 'timeGridWeek';
    callFullCalendar(selectedDate);
}

function viewtimeGridDay(){
    document.getElementById("tipoGradeSelecionada").innerHTML = "<div>Grade selecionada: <b>Dia</b></div>";
    infoViewType = 'timeGridDay';
    callFullCalendar(selectedDate);
}

function openModalThatCreateEvent(date){
    let dateClicked = new Date(date);
    let hourStart = new Date(dateClicked);
    let hourEnd = new Date(dateClicked.setMinutes(dateClicked.getMinutes() + 14)); //data com 14 minutos a mais

    let dt = dateClicked.getFullYear().toString() + "-";
    dt += (dateClicked.getMonth() + 1).toString().padStart(2, '0') + "-";
    dt += dateClicked.getDate().toString().padStart(2, '0');
    document.getElementById("data").value = dateClicked.toISOString();

    let horaInicio = hourStart.getHours().toString().padStart(2, '0') + ":";
    horaInicio += hourStart.getMinutes().toString().padStart(2, '0') + ":";
    horaInicio += hourStart.getSeconds().toString().padStart(2, '0');
    document.getElementById("start").value = horaInicio;

    let horaFim = hourEnd.getHours().toString().padStart(2, '0') + ":";
    horaFim += hourEnd.getMinutes().toString().padStart(2, '0') + ":";
    horaFim += hourEnd.getSeconds().toString().padStart(2, '0');
    document.getElementById("end").value = horaFim;

    $('#title').html("");
    $('#cpf').html("");
    $('#birthdate').html("");
    $('#hour').html('<b>'+horaInicio+'</b> - '+ '<b>'+horaFim+'</b>');
    document.getElementById("newBoardColor").value = "#3788D8";
    document.getElementById("descricao").value = "";

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    $('#modal-create-event #dataSelecionadaModal').text(dateClicked.toLocaleString(undefined, options));
    $('#modal-create-event').modal('show');
}

let btnFindPerson = document.querySelector("#btnFindPerson");

btnFindPerson.addEventListener("click", function(event){
    event.preventDefault();
    let n = $('#nameBusca').val();
    nome = n.replaceAll(".","").replaceAll("-","");
    if (nome != null && nome.trim() != ''){
        $.ajax({
            method : "GET",
            url : url+"person",
            data : "search=" + nome,
            success : function(response) {
                $('#tabelaresultados > tbody > tr').remove();
                for (var i = 0; i < response.content.length; i++){
                    $('#tabelaresultados > tbody').append(
                    '<tr id="'+response.content[i].id+
                    '"><td><button type="button" onclick="preencherFormCadastroAgenda('+response.content[i].id+')"'+
                    ' class="btn btn-primary" data-bs-toggle="collapse" data-bs-target=".multi-collapse"'+
                    ' aria-expanded="false" aria-controls="pesquisaUsuarioCollapse" >Selecionar</button></td>'+
                    '</td><td>'+response.content[i].name+
                    '</td><td>'+response.content[i].cpf+
                    '</td><td>'+response.content[i].cns+
                    '</td><td>'+response.content[i].id+'</td></tr>');
                }
            }
        }).fail(function(xhr, status, errorThrown) {
            alert("Erro ao tentar pesquisar no servidor: " + xhr.responseText);
        });
    }else{
         alert("preencha o campo para pesquisa");
    }
});

let inputNameBusca = document.getElementById("nameBusca");
inputNameBusca.addEventListener("keypress", function(event){
    if(event.key === "Enter"){
        event.preventDefault();
        document.getElementById("btnFindPerson").click();
    }
})

function preencherFormCadastroAgenda(id) {
    $.ajax({
        method : "GET",
        url : url+"person/" + id,
        success : function(response) {
            $("#id").val(response.id);
            $("#title").html(response.name);
            $('#cpf').html(response.cpf);
            $('#birthdate').html(response.birthdate);

            $('#modalPesquisarUser').modal('hide');
        }
    }).fail(function(xhr, status, errorThrown) {
        alert("Erro ao buscar usuario por id: " + xhr.responseText);
    });
}

$(document).ready(function () {
    $("#addEvent").on("submit", function (event) {
        let dados = {
            title: $('#title').html(),
            dateUTC: $('#data').val(),
            start: $('#start').val(),
            end: $('#end').val(),
            backgroundColor: document.getElementById("newBoardColor").value,
            personCPF: $('#cpf').html(),
            personBirthDate: $('#birthdate').html(),
            descricao: $('#descricao').val()
        }
        console.log(dados);
        event.preventDefault();
        $.ajax({
            type: "POST",
            url: url+"event",
            data: JSON.stringify(dados),
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json',
            processData: false,
            success: function (event) {
                $('#modal-create-event').modal('hide');
                $('#msgSucess').html(
                  '<div class="alert alert-success">'+
                    '<strong>Salvo com Sucesso! </strong> Agendamento feito para: <b>'+
                     event.title +'</b> Data: <b>'+event.start.substr(0,10)+'</b>'+
                     ' Hora: <b>'+event.start.substr(11)+'</b>'+
                  '</div>')
                callFullCalendar(event.dateUTC);
            }
        }).fail(function(xhr, status, errorThrown) {
            alert("Erro ao salvar: " + xhr.responseText);
        });
     });
});

let tipoPesquisa = "nome";
let inputLabelSearch = document.getElementById("labelInputPesquisa");

function nomeSelecionado(){
  tipoPesquisa = "nome"
  document.getElementById("nameBusca").value = "";
  $('#labelInputPesquisa').html("<label>Informe o Nome:</label>"); // JQuery
}

function cpfSelecionado(){
  tipoPesquisa = "cpf";
  document.getElementById("nameBusca").value = "";
  inputLabelSearch.innerText = "Informe o CPF:" ;
}

function cnsSelecionado(){
  tipoPesquisa = "cns";
  document.getElementById("nameBusca").value = "";
  inputLabelSearch.innerText = "Informe o CNS:" ;
}

function mascaraCPFouCNS(i) {
  if(tipoPesquisa == "cpf"){
    var v = i.value;
    if (isNaN(v[v.length - 1])) { // permitir apenas caracteres numéricos
        i.value = v.substring(0, v.length - 1);
        return;
    }
    i.setAttribute("maxlength", "14");
    if (v.length == 3 || v.length == 7) i.value += ".";
    if (v.length == 11) i.value += "-";
  }
  if(tipoPesquisa == "cns"){
    var v = i.value;
    if (isNaN(v[v.length - 1])) {
        i.value = v.substring(0, v.length - 1);
        return;
    }
    i.setAttribute("maxlength", "18");
    if (v.length == 3 || v.length == 8 || v.length == 13) i.value += ".";
  }
}