$(document).ready(function(){
    var headers = {};
    $(this).find('th.noSort').each(function(i,el){
        headers[$(this).index()] = { sorter: false };
    });
    $("#tasks").tablesorter({
        headers: headers
    });

    var tbody = $("#tasks td");
    if(tbody.length==0){
        $('#tasks').hide();
        $('label#message').show();
    }



    $("#tasks td.cell-date").dblclick(function () {
        var row = $.trim($(this).parent().find("td:nth-child(1)").text());
        var OriginalContent = $(this).text();
        $(this).addClass("cellEditing");
        $(this).html('<input type="text" class="date-cell" value="'+OriginalContent+'" />');
        $('.date-cell').inputmask('99.99.9999 99:99');
        $(this).children().first().focus();
        $(this).children().first().keypress(function (e) { if (e.which == 13) {

          /*  var fullContent = $(this).val();
            $(this).parent().html(fullContent);
            $(this).parent().removeClass("cellEditing");*/
            var URL = 'updatedate' ;
            var newContent = $(this).val();

            $.post(URL,{
                id: row,
                date: newContent
            });
            $(this).parent().text(newContent);
            $(this).parent().removeClass("cellEditing");

            /*var newContent = $(this).val();
             $(this).parent().text(newContent);
             $(this).parent().removeClass("cellEditing"); }*/
        }
        });
        $(this).children().first().blur(function(){
            $(this).parent().text(OriginalContent);
            $(this).parent().removeClass("cellEditing");
        });
    });

    $('.date-cell').inputmask('99.99.9999 99:99');

   /* setCurrentDate();

    function getIndex(text)
    {
        var index = $('#tasks tr th').filter(
        function(){
            return $(this).text().toUpperCase() == text.toUpperCase();
        }).index();

        return index;
    }
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
    function parseDate(date){

        var q = date.split(" ");

        var d = q[0].split(".");

        var t = q[1].split(":");
        var ddd = new Date(d[2],d[1]-1,d[0],t[0],t[1],0,0);
        return ddd.getTime();
    }*/

});