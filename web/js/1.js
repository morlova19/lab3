$(document).ready(function(){
    var headers = {};
    $(this).find('th.noSort').each(function () {
        headers[$(this).index()] = { sorter: false };
    });
    $("#tasks").tablesorter({
        headers: headers
    });

    var tbody = $("#tasks").find("td");
    if(tbody.length==0){
        $('#tasks').hide();
        $('label#message').show();
    }
    $("#tasks").find("td.cell-status").dblclick(function () {


            var row = $.trim($(this).parent().find("td:nth-child(1)").text());
            var OriginalContent = $(this).text();
        if(OriginalContent=='NEW')
        {
            $(this).addClass("cellEditing");
            $(this).html('<select><option selected>NEW</option> <option>IN PROGRESS</option><option>COMPLETED</option></select>');
            $(this).children().first().focus();
            $(this).children().first().change(function () {  {

                var URL = 'updatestatus' ;
                var newContent = $(this).val();
                var data = 'N';
                if(newContent=='IN PROGRESS')
                {
                    data='P';
                }
                else if(newContent=='COMPLETED'){
                    data='A';
                }
                $.post(URL,{
                    id: row,
                    status: data
                });
                $(this).parent().text(newContent);
                $(this).parent().removeClass("cellEditing");
            }
            });
            $(this).children().first().blur(function(){
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
        }
        else if(OriginalContent=='IN PROGRESS')
        {
            $(this).addClass("cellEditing");
            $(this).html('<select><option>NEW</option> <option selected>IN PROGRESS</option><option>COMPLETED</option></select>');
            $(this).children().first().focus();
            $(this).children().first().change(function () {  {

                var URL = 'updatestatus' ;
                var newContent = $(this).val();
                var data = 'N';
                if(newContent=='IN PROGRESS')
                {
                    data='P';
                }
                else if(newContent=='COMPLETED'){
                    data='A';
                }
                $.post(URL,{
                    id: row,
                    status: data
                });
                $(this).parent().text(newContent);
                $(this).parent().removeClass("cellEditing");

            }
            });
            $(this).children().first().blur(function(){
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
        }else if(OriginalContent=="CANCELLED")
        {
            $(this).addClass("cellEditing");
            $(this).html('<select><option>NEW</option><option>PERFORMING</option><option selected>CANCELLED</option></select>');
            $(this).children().first().focus();
            $(this).children().first().keypress(function (e) { if (e.which == 13) {

            }
            });
            $(this).children().first().blur(function(){
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
        }



    });
    $("#tasks").find("td.cell-date").dblclick(function () {
        var isCreator = $(this).parent().find("td:last-child").find(".delete-button").length;

        if(isCreator==0)
        {
            return false;
        }
        else
        {
        var row = $.trim($(this).parent().find("td:nth-child(1)").text());
        var OriginalContent = $(this).text();
        $(this).addClass("cellEditing");
        $(this).html('<input type="text" class="date-cell" value="'+OriginalContent+'" />');
        $('.date-cell').inputmask('99.99.9999 99:99');
        $(this).children().first().focus();
        $(this).children().first().keypress(function (e) { if (e.which == 13) {

            var URL = 'updatedate' ;
            var newContent = $(this).val();

            $.post(URL,{
                id: row,
                date: newContent
            });
            $(this).parent().text(newContent);
            $(this).parent().removeClass("cellEditing");

        }
        });
        $(this).children().first().blur(function(){
            $(this).parent().text(OriginalContent);
            $(this).parent().removeClass("cellEditing");
        });
            return true;
        }
    });


    $('.date-cell').inputmask('99.99.9999 99:99');

    $('.hide-child').click(function(){
        if( $(this).parent().children('ul').is(":visible"))
        {
            $(this).val('+');
            $(this).parent().children('ul').hide();
        }
        else {
            $(this).val('-');
            $(this).parent().children('ul').show();
        }

    });
    $('.edit-exec').click(function(){

    });
    $('#status').change(function(){
       var rows = $('#tasks tbody').find('tr');
        var cur_status = $(this).val().toUpperCase();
        rows.each(function(){
           var status = $.trim($(this).find('td:nth-child(4)').text());
            if(cur_status=='ALL')
            {
                $(this).show();
            }
            else {
                if (status == cur_status) {
                    $(this).show();
                }
                else {
                    $(this).hide();
                }
            }
        });
    });
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