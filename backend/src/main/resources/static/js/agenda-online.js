
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
        selectedDate = new Date(dateText);
        selectedDate.setDate(selectedDate.getDate() + 1)
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
    console.log(dateClicked.toISOString())

    let horaInicio = hourStart.getHours().toString().padStart(2, '0') + ":";
    horaInicio += hourStart.getMinutes().toString().padStart(2, '0') + ":";
    horaInicio += hourStart.getSeconds().toString().padStart(2, '0');
    document.getElementById("start").value = horaInicio;

    let horaFim = hourEnd.getHours().toString().padStart(2, '0') + ":";
    horaFim += hourEnd.getMinutes().toString().padStart(2, '0') + ":";
    horaFim += hourEnd.getSeconds().toString().padStart(2, '0');
    document.getElementById("end").value = horaFim;

    document.getElementById("title").value = "";
    document.getElementById("newBoardColor").value = "#3788D8";

    const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
    $('#modal-create-event #dataSelecionadaModal').text(dateClicked.toLocaleString(undefined, options));
    $('#modal-create-event').modal('show');
}

$(document).ready(function () {
    $("#addEvent").on("submit", function (event) {
        var dados = {
            title: $('#title').val(),
            dateUTC: $('#data').val(),
            start: $('#start').val(),
            end: $('#end').val(),
            backgroundColor: document.getElementById("newBoardColor").value,
            personCPF: "14031195036"
        }
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