$(document).ready(function() {
    $('.date-cell').inputmask('99.99.9999 99:99');
    var $tasks = $("#tasks");
    var tbody = $tasks.find("td");
    if (tbody.length == 0) {
        $tasks.hide();
        $('label#message').show();
    }
    var dates = $tasks.find('tbody tr td:nth-child(3)');
    var $select_status = $('select.status');
    if($select_status.length !=0) {
        var selected_status = $select_status.val().toUpperCase();

        var rows = $tasks.find('tbody').find('tr').find('td:nth-child(4)');

        filter_by_status(selected_status);
    }

    function check_dates() {
        dates.each(function () {
            var delta = parseDate($.trim($(this).text())) - $.now();
            var status = $(this).parent().find('td:nth-child(4)').text();
            if (delta <= 0 && status!='CANCELLED' && status!='ACCOMPLISHED') {
                $(this).css('color', 'red');
            }
            else {
                $(this).css('color', 'black');
            }
        });
    }

    check_dates();

    var headers = {};
    $tasks.find('th.noSort').each(function () {
        headers[$(this).index()] = {sorter: false};
    });
    $tasks.tablesorter({
        headers: headers
    });



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
                        filter_by_status(selected_status)
                    }
                });
            $(this).children().first().blur(function () {
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
        }

    });
    $('#complete-form').submit(function(){
        var new_count = $tasks.find('tr td:nth-child(4)').filter(':contains("NEW")').length;

        var perf_count =  $tasks.find('tr td:nth-child(4)').filter(':contains("IN PROGRESS")').length;

        if(new_count==0 && perf_count==0)
        {
            return true;
        }
        else {
            return confirm("Not all subtasks are completed. If you confirm, all subtasks will be completed. Are you sure?")
        }

    });
    var $select = $('select#ex_id');
    $select.change(function()
    {
        $('#update-exec-form').submit();
    });

    $tasks.find("td.cell-date").dblclick(function () {
        var isCreator = $(this).parent().find("td:last-child").find(".delete-button").length;
        var status = $.trim($(this).parent().find("td:nth-child(3)").text());

        if (isCreator == 0 || status=='ACCOMPLISHED') {
            return false;
        }
        else {

            var id = $.trim($(this).parent().find("td:nth-child(1)").text());
            var OriginalContent = $(this).text();
            $(this).addClass("cellEditing");
            $(this).html('<input type="text" class="date-cell" value="' + OriginalContent + '" />');

            $('.date-cell').inputmask('99.99.9999 99:99');

            $(this).children().first().focus();
            $(this).children().first().keypress(function (e) {
                if (e.which == 13) {

                    var URL = 'updatedate';
                    var newContent = $(this).val();

                    var task_date = $('#tdate');
                    if (task_date.length == 0) {

                    if (validDate(newContent) == true) {
                        $.post(URL, {
                            id: id,
                            date: newContent
                        });
                        $(this).css('border', '1px solid #ccc');
                        $(this).parent().text(newContent);
                        $(this).parent().removeClass("cellEditing");
                    }
                    else {
                        alert('Please enter correct date');
                        $(this).css('border', '1px solid red');
                     }
                    }
                    else {
                        if (validSubtaskDate(newContent) == true) {
                            $.post(URL, {
                                id: id,
                                date: newContent
                            });
                            $(this).css('border', '1px solid #ccc');
                            $(this).parent().text(newContent);
                            $(this).parent().removeClass("cellEditing");
                        }
                        else {
                            alert('Please enter correct date');
                            $(this).css('border', '1px solid red');
                        }
                    }
                }
            });
            $(this).children().first().blur(function () {
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
            return true;
        }
    });




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
    $select_status.change(function () {
        filter_by_status($(this).val().toUpperCase());
    });
    function filter_by_status(status) {
        rows.each(function () {
            var cur_status = $.trim($(this).text());
            if (status == 'ALL') {
                $tasks.show();
                $(this).parent().show();
            }
            else {
                $tasks.show();
                if (status == cur_status) {
                    $(this).parent().show();
                }
                else {
                    $(this).parent().hide();
                }
            }

        });
        var tbody = $tasks.find("td").is(':visible');
        if (tbody == 0) {
            $tasks.hide();
            $('label.msg-no-status').show();
        }
        else {
            $tasks.show();
            $('label.msg-no-status').hide();
        }
    }

     function parseDate(date){

     var q = date.split(" ");

     var d = q[0].split(".");

     var t = q[1].split(":");
     var ddd = new Date(d[2],d[1]-1,d[0],t[0],t[1],0,0);
     return ddd.getTime();
     }
    function validDate(date) {
        if (date.length == 0) {
            return false;
        }
        var q = date.split(" ");
        var d = q[0].split(".");
        var t = q[1].split(":");

        var ddd = new Date(d[2], d[1] - 1, d[0], t[0], t[1], 0, 0);

        if(t[0]>23 || t[1]>59 || d[0]>31 || d[1] > 13)
        {
            return false;
        }
        var delta = ddd.getTime() - Date.now();
        if (delta <= 0) {

            return false;
        }
        else if (delta > 0) {

            return true;
        }
    }
    function validSubtaskDate(date) {
        if (date.length == 0) {
            return false;
        }
        var q = date.split(" ");
        var d = q[0].split(".");
        var t = q[1].split(":");

        if(t[0]>23 || t[1]>59 || d[0]>31 || d[1] > 13)
        {
            return false;
        }

        var stask_date = new Date(d[2], d[1] - 1, d[0], t[0], t[1], 0, 0);

        var tdate = $('#tdate').val();
        var q1 = tdate.split(" ");
        var d1 = q1[0].split(".");
        var t1 = q1[1].split(":");

        var task_date = new Date(d1[2], d1[1] - 1, d1[0], t1[0], t1[1], 0, 0);
        var delta = task_date.getTime() - stask_date.getTime();
        var delta1 = stask_date.getTime() - Date.now();

        if (delta <= 0 && delta1 <= 0) {

            return false;
        }
        else if (delta > 0 && delta1 > 0) {

            return true;
        }
    }

});