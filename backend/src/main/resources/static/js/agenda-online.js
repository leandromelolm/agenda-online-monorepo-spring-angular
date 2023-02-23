
let daySelect = new Date();
let formatTodayDate= formataDataParaDDMMYYYY(daySelect)
$('#DataSelecionada').text(formatTodayDate);


$('#datePicker').datepicker({
    showOtherMonths: true,
    selectOtherMonths: true,
    changeMonth: true,
    changeYear: true,
    dateFormat: 'yy-mm-dd',

    onSelect: function (dateText, inst) {
        let date = new Date(dateText);
        date.setDate(date.getDate() + 1);
        daySelect = date;
        $('#DataSelecionada').text(formataDataParaDDMMYYYY(date));
    },
})

function formataDataParaDDMMYYYY(date){
    return ("0" + date.getDate()).slice(-2) + '/' + ("0" + (date.getMonth() + 1)).slice(-2) + '/' + date.getFullYear();
}

function formataDataParaYYYYMMDD(data){
    return data.getFullYear() + "-" + ("0" + (data.getMonth() + 1)).slice(-2) + "-" + ("0" + data.getDate()).slice(-2);
}

function diaAnterior() {
    daySelect.setDate(daySelect.getDate() - 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(daySelect));
    $('#DataSelecionada').text(formataDataParaDDMMYYYY(daySelect));
};

function diaProximo() {
    daySelect.setDate(daySelect.getDate() + 1)
    $("#datePicker").datepicker("setDate", formataDataParaYYYYMMDD(daySelect));
    $('#DataSelecionada').text(formataDataParaDDMMYYYY(daySelect));
};