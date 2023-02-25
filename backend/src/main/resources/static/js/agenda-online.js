
let selectedDate = new Date();
$('#dataSelecionada').text(formataDataParaDDMMYYYY(new Date()));

$('#datePicker').datepicker({
    showOtherMonths: true,
    selectOtherMonths: true,
    changeMonth: true,
    changeYear: true,
    dateFormat: 'yy-mm-dd',

    onSelect: function (dateText, inst) {
        let date = new Date(dateText);
        $('#dataSelecionada').text(formataDataParaDDMMYYYY(date));
    },
})

document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        locale: 'pt-BR',
        initialView: 'timeGridDay',
        editable: false, // impedir que o evento seja arrastado
        selectable: true,
        businessHours: false,
        businessHours: {
            daysOfWeek: [1, 2, 3, 4, 5],
            startTime: '7:30',
            endTime: '16:30',
        },
        headerToolbar: {
            left: 'dayGridMonth,timeGridWeek,timeGridDay,listYear',
            center:'title',
            right: 'prev next'
        },
        allDaySlot:false,
        dayMaxEvents: true, // allow "more" link when too many events
        slotDuration: '00:15:00', slotMinTime: '07:00', slotMaxTime: '17:15', slotLabelInterval: '00:15',
        slotLabelFormat: [{ hour: '2-digit', minute: '2-digit' },],
    });
    calendar.render();
});

function formataDataParaDDMMYYYY(date){
    return ("0" + date.getDate()).slice(-2) + '/' + ("0" + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear();
}

function formataDataParaYYYYMMDD(data){
    return data.getFullYear() + "-" + ("0" + (data.getMonth() + 1)).slice(-2) + "-" + ("0" + data.getDate()).slice(-2);
}

function diaAnterior() {
    selectedDate.setDate(selectedDate.getDate() - 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
};

function diaProximo() {
    selectedDate.setDate(selectedDate.getDate() + 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
};