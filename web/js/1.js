$(document).ready(function(){
    var tbody = $("#tasks td");
    if(tbody.length==0){
        $('#tasks').hide();
        $('ul#sort-delete-list').hide();
        $('label#message').show();
    }
    var element_th = $('table#tasks tr th :checkbox');
    var element_td = $('table#tasks tr td :checkbox');
    $('.select-all').on('click', function() {
       element_td.prop('checked', $(this).is(':checked'));
    });
    element_td.on('click', function() {
        if(!$(this).is(':checked') && element_th.is(':checked'))
        {
            element_th.prop('checked', false);
        }
        var count = $('table#tasks tr td :checkbox:not(:checked)').length;
        if(count ==0){
            element_th.prop('checked', true);
        }
    });
    $('select#sort').change(function() {
        $("#select-param").remove();
        var text = $( "#sort option:selected").text().toUpperCase();
        var index = getIndex(text);
        if(text == 'ID'){
            sortTableByInt(index);
        }
        else if(text == 'DATE')
        {
            sortTableByDate(index);
        }
        else {
            sortTable(index);
        }
    });

    function sortTable(n){
        var rows = $('#tasks tbody  tr').get();
        rows.sort(function(a, b) {

            var A = $(a).children('td').eq(n).text().toUpperCase();
            var B = $(b).children('td').eq(n).text().toUpperCase();
            if(A < B) {
                return -1;
            }
            if(A > B) {
                return 1;
            }
            return 0;
        });

        $.each(rows, function(index, row) {
            $('#tasks').children('tbody').append(row);
        });
    }
    function sortTableByInt(n){
        var rows = $('#tasks tbody  tr').get();
        rows.sort(function(a, b) {

            var str1 = $(a).children('td').eq(n).text();
            var str2 = $(b).children('td').eq(n).text();

            var A = parseInt(str1);
            var B = parseInt(str2);
            if(A < B) {
                return -1;
            }
            if(A > B) {
                return 1;
            }
            return 0;
        });

        $.each(rows, function(index, row) {
            $('#tasks').children('tbody').append(row);
        });
    }
    function sortTableByDate(n){
        var rows = $('#tasks tbody  tr').get();
        rows.sort(function(a, b) {

            var str1 = $.trim($(a).children('td').eq(n).text());
            var str2 = $.trim($(b).children('td').eq(n).text());

            var A = 0;
            var B = 0;
            if(str1.length != 0)
            {
                A = parseDate(str1);
            }
            if(str2.length != 0)
            {
                B = parseDate(str2);
            }

            if(A < B) {
                return -1;
            }
            if(A > B) {
                return 1;
            }
            return 0;
        });

        $.each(rows, function(index, row) {
            $('#tasks').children('tbody').append(row);
        });
    }

    $('.date-cell').inputmask('99.99.9999 99:99');

    setCurrentDate();

    function getIndex(text)
    {
        var index = $('#tasks tr th').filter(
        function(){
            return $(this).text().toUpperCase() == text;
        }).index();

        return index;
    }
    function setCurrentDate(){
        var now = new Date();
        var day = now.getDate();

        console.log(day)
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
    }

});