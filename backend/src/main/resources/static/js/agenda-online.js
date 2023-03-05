
let selectedDate = new Date();
$('#dataSelecionada').text(formataDataParaDDMMYYYY(new Date()));

// test
const date = new Date()
let ymDate = new Date(date.setMonth(date.getMonth())).toISOString().substring(7,0);
let ymFutureDate = new Date(date.setMonth(date.getMonth() + 1)).toISOString().substring(7,0);
//console.log(ymDate + " " + ymFutureDate);

let infoViewType = 'dayGridMonth';
document.getElementById("tipoGradeSelecionada").innerHTML = "<div>Grade em exibição: <b>Mês</b></div>";

const url = "http://localhost:8080/myagenda/";

let ultimoEventoSelecionado;

const allowedDates = [];
const allowedDatesTime = [];
const allowedMonths = [];

const urlFetch = "./datasPermitidas.json";
fetch(urlFetch).then(response => response.json()).then(itens => {
    itens.map((elemento) => {
//        console.log(elemento);
        if (elemento.tipo === 'day') return allowedDates.push(elemento.start);
        if (elemento.tipo === 'dayandtime') return allowedDatesTime.push(elemento.start);
        if (elemento.tipo === 'month') return allowedMonths.push(elemento.start);
    });
    console.log("allowedDates: "+allowedDates)
    console.log("allowedDatesTime: "+allowedDatesTime)
    allowedMonths.push(ymDate); // test
    console.log("allowedMonths: "+allowedMonths)
})

const blockedHours = [];
blockedHours[0] = "07";
blockedHours[1] = "12";
blockedHours[2] = "17";

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
        changeBackgroundColorBlockedHours();
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

        eventSources: [
            url+'event'+'?size=200',
           "./datasPermitidas.json"
        ],

        // test
        events: [
            {
                "id": 215,
                "groupId": "data-permitida-test",
                "start": ymDate,
                "end": ymFutureDate,
                "backgroundColor": null,
                "color": "",
                "display": "background",
                "overlap": false,
                "tipo": "month"
            }
        ],

        dateClick: function(info){
            if(info.view.type =='dayGridMonth'){
                infoViewType = 'timeGridDay';
                selectedDate = info.date; //info.date = Wed Nov 30 2023 07:45:00 GMT-0300 (Horário Padrão de Brasília)
                calendar.changeView('timeGridDay', info.dateStr); // info.dateStr = 2023-11-30T07:45:00-03:00
                changeBackgroundColorBlockedHours();
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
                for (let index = 0; index < blockedHours.length; index++) {
                    if(info.dateStr.substring(11,13) == blockedHours[index]){
                        return alert("Não é possível agendar nesse intervalo de hora. Entre as "+
                         blockedHours[index]+":00 e "+ blockedHours[index]+":59");
                    }
                }
                if(checkPermissionToCreateEvent(info.dateStr)){ // verificar permissão de criar evento na data selecionada
                    return alert("Não está permitido agendar essa data!");
                }
                openModalThatCreateEvent(info.dateStr);
            }
        },
        eventClick: function(infoEvent) {
            deleteEvent(infoEvent);
        },

    });
    console.log("calendar.render()")
    calendar.render();
};

function checkPermissionToCreateEvent(dateStr){
    const foundMonth =  allowedMonths.find(e => e.includes(dateStr.substr(0,7)));
    if(foundMonth == undefined){
        const foundDay =  allowedDates.find(e => e.includes(dateStr.substr(0,10)));
        if(foundDay == undefined){
            const foundTime =  allowedDatesTime.find(e => e.includes(dateStr.substr(0,19)))
            if(foundTime == undefined){
                console.log("Não é permitido criar evento nessa data!")
                return true;
            }
        }
    }
}

function formataDataParaDDMMYYYY(date){
    return ("0" + date.getDate()).slice(-2) + '/' + ("0" + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear();
}

function formataDataParaYYYYMMDD(data){
    return data.getFullYear() + "-" + ("0" + (data.getMonth() + 1)).slice(-2) + "-" + ("0" + data.getDate()).slice(-2);
}

function datePrev() {
    selectedDate.setDate(selectedDate.getDate() - 1);
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
    callFullCalendar(formataDataParaYYYYMMDD(selectedDate));
};

function dateNext() {
    selectedDate.setDate(selectedDate.getDate() + 1);
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
    callFullCalendar(formataDataParaYYYYMMDD(selectedDate));
};

$('body').on('click', '.fc-prev-button', function() {
    if(infoViewType == "timeGridDay"){
        selectedDate.setDate(selectedDate.getDate() - 1);
    }
    if(infoViewType == "timeGridWeek"){
        selectedDate.setDate(selectedDate.getDate() - 7);
    }
    if(infoViewType == "dayGridMonth"){
        selectedDate.setMonth(selectedDate.getMonth() - 1);
    }
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
});

$('body').on('click', '.fc-next-button', function() {
    if(infoViewType == "timeGridDay"){
        selectedDate.setDate(selectedDate.getDate() + 1);
    }
    if(infoViewType == "timeGridWeek"){
        selectedDate.setDate(selectedDate.getDate() + 7);
    }
    if(infoViewType == "dayGridMonth"){
        selectedDate.setMonth(selectedDate.getMonth() + 1);
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
    changeBackgroundColorBlockedHours();
}

function viewtimeGridDay(){
    document.getElementById("tipoGradeSelecionada").innerHTML = "<div>Grade selecionada: <b>Dia</b></div>";
    infoViewType = 'timeGridDay';
    callFullCalendar(selectedDate);
    changeBackgroundColorBlockedHours();
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
  tipoPesquisa = "nome";
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

function changeBackgroundColorBlockedHours(){
    $('.fc .fc-timegrid-slot').each( function(){ // pecorre as linhas da grade diaria
        var timeSlot = $(this).text();
        for (i = 0; i < blockedHours.length; i++) {
            if(timeSlot.substring(0,2) == blockedHours[i]){
                $(this).closest('tr').css('background-color', '#E9E9E9'); //CINZA #993399
            }
        }
    });
}

function deleteEvent(infoEvent){
    console.log(infoEvent);
    infoEvent.el.style.borderColor = '#b7ff12';
    infoEvent.el.style.backgroundColor = '#274360';
    infoEvent.jsEvent.preventDefault();
    let excluir = confirm(
        'Clique em OK para APAGAR o evento: ' +infoEvent.event.title +
        ' CPF: '+infoEvent.event.extendedProps.personCPF +
        ' Data: '+infoEvent.event.start);
    if(excluir){
        infoEvent.event.remove();
        $.ajax({
            method: "DELETE",
            url: url+'event/'+infoEvent.event.id,
            success: function() {
                console.log();
                alert("Deletado com sucesso! " + infoEvent.event.title);
            }
        })
    }
    if(ultimoEventoSelecionado !== undefined){
        ultimoEventoSelecionado.el.style.backgroundColor = '#007FFF';
    }
    ultimoEventoSelecionado = infoEvent;
}


//// Teste de permissão para criar evento
//const allowedMonths = []; // meses com permissão para criar evento
//allowedMonths[0] = "2023-03";
//allowedMonths[1] = "2023-11";
//allowedMonths[2] = "2023-05";
//const allowedDates = []; // datas com permissão para criar evento
//allowedDates[0] = "2023-12-30";
//allowedDates[1] = "2023-12-21";
//allowedDates[2] = "2023-12-15";
//allowedDates[3] = "2023-12-16";
//allowedDates[4] = "2023-12-10";
//allowedDates[5] = "2023-07-02";
//allowedDates[6] = "2023-12-28";
//const allowedDatesTime = []; // data com horário permitido criar evento
//allowedDatesTime[2] = "2023-06-29T09:00:00";
//allowedDatesTime[0] = "2023-06-29T09:45:00";
//allowedDatesTime[1] = "2023-06-29T11:00:00";