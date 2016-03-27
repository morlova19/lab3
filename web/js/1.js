$(document).ready(function() {

    var headers = {};
    $(this).find('th.noSort').each(function () {
        headers[$(this).index()] = {sorter: false};
    });
    $("#tasks").tablesorter({
        headers: headers
    });

    var tbody = $("#tasks").find("td");
    if (tbody.length == 0) {
        $('#tasks').hide();
        $('label#message').show();
    }
    $("#tasks").find("td.cell-status").dblclick(function () {


        var row = $.trim($(this).parent().find("td:nth-child(1)").text());
        var OriginalContent = $(this).text();
        if (OriginalContent == 'NEW') {
            $(this).addClass("cellEditing");
            $(this).html('<select><option selected>NEW</option> <option>IN PROGRESS</option><option>COMPLETED</option></select>');
            $(this).children().first().focus();
            $(this).children().first().change(function () {
                {

                    var URL = 'updatestatus';
                    var newContent = $(this).val();
                    var data = 'N';
                    if (newContent == 'IN PROGRESS') {
                        data = 'P';
                    }
                    else if (newContent == 'COMPLETED') {
                        data = 'A';
                    }
                    $.post(URL, {
                        id: row,
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
        else if (OriginalContent == 'IN PROGRESS') {
            $(this).addClass("cellEditing");
            $(this).html('<select><option>NEW</option> <option selected>IN PROGRESS</option><option>COMPLETED</option></select>');
            $(this).children().first().focus();
            $(this).children().first().change(function () {
                {

                    var URL = 'updatestatus';
                    var newContent = $(this).val();
                    var data = 'N';
                    if (newContent == 'IN PROGRESS') {
                        data = 'P';
                    }
                    else if (newContent == 'COMPLETED') {
                        data = 'A';
                    }
                    $.post(URL, {
                        id: row,
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
        } else if (OriginalContent == "CANCELLED") {
            $(this).addClass("cellEditing");
            $(this).html('<select><option>NEW</option><option>PERFORMING</option><option selected>CANCELLED</option></select>');
            $(this).children().first().focus();
            $(this).children().first().keypress(function (e) {
                if (e.which == 13) {

                }
            });
            $(this).children().first().blur(function () {
                $(this).parent().text(OriginalContent);
                $(this).parent().removeClass("cellEditing");
            });
        }


    });

    $("#tasks").find("td.cell-date").dblclick(function () {
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
        var _class = $(this).attr('class');
        var cells = row.find('td').not(':last-child').not(':first-child');

        if (_class == 'edit-exec') {

            $(this).removeClass('edit-exec');
            $(this).addClass('done');
            row.find("td:last-child").find(".delete-button").hide();
            row.find("td:last-child").find(".copy-button").hide();
            cells.each(function (index) {
                var OriginalContent = $.trim($(this).text());
                if (index == 0) {
                    var isCreator = $(this).parent().find("td:last-child").find(".delete-button").length;
                    if (isCreator == 0) {
                        return false;
                    }
                    else {
                        $(this).addClass("cellEditing");
                        $(this).html('<input id="name" type="text" name="name" value="' + OriginalContent + '" />');
                        $(this).children().first().focus();
                        return true;
                    }
                } else if (index == 1) {

                }
                else if (index == 2) {

                } else if (index == 3) {
                    var isCreator = $(this).parent().find("td:last-child").find(".delete-button").length;
                    if (isCreator == 0) {
                        return false;
                    }
                    else {
                        $(this).addClass("cellEditing");
                        $(this).html('<select><option selected>Me</option> <c:forEach var="e" items="${emp.emps_map.values()}"><option>${e.name}</option></c:forEach></select>');
                        $(this).children().first().focus();
                        return true;
                    }
                }
            });
        }
        else if (_class == 'done') {

            var name;
            cells.each(function (index) {
                if (index == 0) {

                    var URL = 'updatename';
                    var newContent = $.trim($(this).find('input').val());
                    if (newContent.length != 0) {
                        $(this).removeClass('done');
                        $(this).addClass('edit-exec');
                        row.find("td:last-child").find(".delete-button").show()
                        row.find("td:last-child").find(".copy-button").show();
                        $(this).removeClass("cellEditing");
                        $.post(URL, {
                            id: id,
                            name: newContent
                        }, function (data) {
                            location.reload();
                        });
                    }
                    else {
                        alert('Please enter correct name')
                    }

                } else if (index == 1) {

                }
                else if (index == 2) {

                } else if (index == 3) {

                }

            });


            //$('#update-from-table-form').submit();
        }
        /*  var row = $(this).parent().parent();
         var id = $.trim(row.find('td:first-child').text());
         var cells = row.find('td').not(':last-child').not(':first-child');
         var _class = $(this).attr('class');
         if (_class == 'edit-exec') {
         $(this).removeClass('edit-exec');
         $(this).addClass('done');
         console.log(456)
         cells.each(function(index){
         if(index==0)
         {
         $(this).dblclick(function(){
         handler($(this))
         });
         }else if(index==1)
         {
         $(this).dblclick(function(){
         handler($(this))
         });
         }
         $(this).text($.trim($(this).text()));
         });
         }
         else  if (_class == 'done') {
         $(this).removeClass('done');
         $(this).addClass('edit-exec');

         cells.each(function(index){
         console.log(123)
         if(index==0)
         {
         var URL = 'updatename' ;
         var newContent = $.trim($(this).text());
         var string = '<a href="#" target="_blank">123</a>';

         console.log($(this));
         // $(this).html('<a href="#">' + $(this).html() + '</a>')
         $(this).html('<select><option>' + $(this).html() + '</option></select>')


         $.post(URL,{
         id: id,
         name: newContent
         });

         }else if(index==1)
         {

         }
         $(this).text($.trim($(this).text()));
         });


         }*/

    });
    function handler(cell) {
        var isCreator = cell.parent().find("td:last-child").find(".delete-button").length;
        if (isCreator == 0) {
            return false;
        }
        else {
            var OriginalContent = cell.text();
            cell.addClass("cellEditing");
            cell.html('<input type="text" class="date-cell" value="' + OriginalContent + '" />');
            cell.children().first().focus();

            cell.children().first().keypress(function (e) {
                if (e.which == 13) {
                    var newContent = $(this).val();
                    cell.text(newContent);
                    cell.removeClass("cellEditing");

                }
            });
            cell.children().first().blur(function () {
                cell.text(OriginalContent);
                cell.removeClass("cellEditing");
            });
            return true;
        }
    }

    $('select.status').change(function () {
        var rows = $('#tasks').find('tbody').find('tr');
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
        var tbody = $("#tasks").find("td").is(':visible');
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
     }
     function parseDate(date){

     var q = date.split(" ");

     var d = q[0].split(".");

     var t = q[1].split(":");
     var ddd = new Date(d[2],d[1]-1,d[0],t[0],t[1],0,0);
     return ddd.getTime();
     }*/
});