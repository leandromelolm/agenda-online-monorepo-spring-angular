
let selectedDate = new Date();
$('#dataSelecionada').text(formataDataParaDDMMYYYY(new Date()));

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
            left: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth',
            center:'title',
            right: 'prev next'
        },
        allDaySlot:false,
        dayMaxEvents: true, // allow "more" link when too many events
        slotDuration: '00:15:00', slotMinTime: '07:00', slotMaxTime: '17:15', slotLabelInterval: '00:15',
        slotLabelFormat: [{ hour: '2-digit', minute: '2-digit' },],
    });
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
    selectedDate.setDate(selectedDate.getDate() - 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
});

$('body').on('click', '.fc-next-button', function() {
    selectedDate.setDate(selectedDate.getDate() + 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(selectedDate));
    $('#dataSelecionada').text(formataDataParaDDMMYYYY(selectedDate));
});