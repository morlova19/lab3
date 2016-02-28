$(document).ready(function() {

    $('#dept').change(function() {

        $("#select-dept").remove();
        $.post( "jobs", { dept: $('#dept option:selected').text()})
            .done(function( data ) {
                var select = $("#job"), options = '';
                select.empty();
                options += "<option>"+'Select job'+"</option>";
                for(var i=0;i<data.length; i++)
                {
                    options += "<option>"+ data[i] +"</option>";
                }
                select.append(options);
            });

    });
    $('#job').change(function() {
        $("#select-job").remove();
    });
});