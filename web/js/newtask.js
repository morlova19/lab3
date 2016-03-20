$(document).ready(function(){

    $('.date-cell').inputmask('99.99.9999 99:99');

    setCurrentDate();


    function setCurrentDate(){
        var now = new Date();
        var day = now.getDate();

        var month = now.getMonth() + 1;

        if (month < 10) month = "0" + month;
        if (day < 10) day = "0" + day;

        var year = now.getFullYear();
        var hours = now.getHours();
        var minutes = now.getMinutes();

        if (hours < 10) hours = "0" + hours;
        if (minutes < 10) minutes = "0" + minutes;

        var str = day + "." + month + "." + year+" "+ hours+":"+minutes;
        $('#tdate').val(str);
    }

});
