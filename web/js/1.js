$(document).ready(function() {

    var $tasks = $("#tasks");

    var rows = $tasks.find('tbody tr td:nth-child(3)');
    rows.each(function(){
        var delta = parseDate($.trim($(this).text())) - $.now();
        if(delta<=0)
        {
            $(this).css('color','red');
        }
        console.log(delta)
    });

    var headers = {};
    $tasks.find('th.noSort').each(function () {
        headers[$(this).index()] = {sorter: false};
    });
    $tasks.tablesorter({
        headers: headers
    });

    var tbody = $tasks.find("td");
    if (tbody.length == 0) {
        $tasks.hide();
        $('label#message').show();
    }
    $tasks.find("td.cell-status").dblclick(function () {
        var id = $.trim($(this).parent().find("td:nth-child(1)").text());
        var isExecutor = ($.trim($(this).parent().find("td:nth-child(5)").text()) == 'Me');
        var isCreator = ($.trim($(this).parent().find("td:last-child").find('button.delete-button').length) != 0);
        var OriginalContent = $(this).text();
        var html = "<select>";
        var new_option='<option>NEW</option>';
        var cancel_option='<option>CANCELLED</option>';
        var perform_option='<option>IN PROGRESS</option>';
        var complete_option='<option>ACCOMPLISHED</option>';
        var select_option='<option selected>Select status</option>';
        if(OriginalContent=='ACCOMPLISHED')
        {
            return false;
        }
        else {
            if(isCreator==true)
            {
                if(isExecutor==true)
                {
                    if(OriginalContent=='CANCELLED')
                    {
                        html += select_option+new_option +'</select>'
                    }
                    else {
                        html += select_option+new_option+perform_option+complete_option+cancel_option+'</select>'
                    }
                }
                else{
                    if(OriginalContent=='CANCELLED')
                    {
                        html += select_option+new_option +'</select>'
                    }
                    else {
                        html += select_option+cancel_option +'</select>'
                    }
                }
            }
            else{
                if(isExecutor==true)
                {
                    html += select_option+new_option+perform_option+complete_option+'</select>'
                }
                else{
                    return false;
                }
            }
                $(this).addClass("cellEditing");
                $(this).html(html);
                $(this).children().first().focus();
                $(this).children().first().change(function () {
                    {
                        var URL = 'updatestatus';
                        var newContent = $(this).val();
                        var data = 'N';
                        if (newContent == 'IN PROGRESS') {
                            data = 'P';
                        }
                        else if (newContent == 'ACCOMPLISHED') {
                            data = 'A';
                        }
                        else if (newContent == 'NEW') {
                            data = 'N';
                        }
                        else if (newContent == 'CANCELLED') {
                            data = 'C';
                        }
                        $.post(URL, {
                            id: id,
                            status: data
                        });
                        $(this).parent().text(newContent);
                        $(this).parent().removeClass("cellEditing");
                    }
                });
            $(this).children().first().blur(function () {
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
        }

    });
    var $select = $('select#ex_id');
    $select.change(function()
    {
        $('#update-exec-form').submit();
    });
    $tasks.find("td.cell-date").dblclick(function () {
        var isCreator = $(this).parent().find("td:last-child").find(".delete-button").length;

        if (isCreator == 0) {
            return false;
        }
        else {
            var row = $.trim($(this).parent().find("td:nth-child(1)").text());
            var OriginalContent = $(this).text();
            $(this).addClass("cellEditing");
            $(this).html('<input type="text" class="date-cell" value="' + OriginalContent + '" />');
            $('.date-cell').inputmask('99.99.9999 99:99');
            $(this).children().first().focus();
            $(this).children().first().keypress(function (e) {
                if (e.which == 13) {

                    var URL = 'updatedate';
                    var newContent = $(this).val();

                    $.post(URL, {
                        id: row,
                        date: newContent
                    });
                    $(this).parent().text(newContent);
                    $(this).parent().removeClass("cellEditing");

                }
            });
            $(this).children().first().blur(function () {
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
            return true;
        }
    });


    $('.date-cell').inputmask('99.99.9999 99:99');

    $('.hide-child').click(function () {
        if ($(this).parent().children('ul').is(":visible")) {
            $(this).val('+');
            $(this).parent().children('ul').hide();
        }
        else {
            $(this).val('-');
            $(this).parent().children('ul').show();
        }
    });

    $('.edit-exec').click(function () {

        var row = $(this).parent().parent();
        var id = $(this).val();
        var cell = row.find('td:nth-child(5)');
        var isCreator = (row.find("td:last-child").find(".delete-button").length)!=0;
        if(isCreator==false)
        {
            return false;
        }
        else{
            $(this).addClass("cellEditing");
            cell.load('select.jsp?taskid=' + id);
        }
    });
    $('select.status').change(function () {
        var rows = $tasks.find('tbody').find('tr');
        var cur_status = $(this).val().toUpperCase();
        console.log(rows);
        rows.each(function () {
            var status = $.trim($(this).find('td:nth-child(4)').text());
            console.log(status);
            if (cur_status == 'ALL') {
                $('#tasks').show();
                $(this).show();
            }
            else {
                $('#tasks').show();
                if (status == cur_status) {
                    $(this).show();
                }
                else {
                    $(this).hide();
                }
            }
        });
        var tbody = $tasks.find("td").is(':visible');
        if (tbody == 0) {
            $('#tasks').hide();
            $('label.msg-no-status').show();
        }
        else {
            $('#tasks').show();
            $('label.msg-no-status').hide();
        }
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
     }*/
     function parseDate(date){

     var q = date.split(" ");

     var d = q[0].split(".");

     var t = q[1].split(":");
     var ddd = new Date(d[2],d[1]-1,d[0],t[0],t[1],0,0);
     return ddd.getTime();
     }
});