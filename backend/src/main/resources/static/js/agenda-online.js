
var picker = $('#datePicker').datepicker({
    showOtherMonths: true,
    selectOtherMonths: true,
    changeMonth: true,
    changeYear: true,
    dateFormat: 'yy-mm-dd',

    onSelect: function (dateText, inst) {
        var date = new Date(dateText);
        date.setDate(date.getDate() + 1);
    },

})