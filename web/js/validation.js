$(document).ready(function(){
    $('#register-form').submit(function(){

        var dept = $('#dept option:selected').text();
        var job = $('#job option:selected').text();
        var fname = $.trim($('#fname').val()).length;
        var lname = $.trim($('#lname').val()).length;
        var username = $.trim($('#username').val()).length;
        var pass = $.trim($('#pass').val()).length;

        if(fname != 0 && lname!=0 && username!=0 && pass != 0 && dept != 'Select dept' && job != 'Select job')
        {
            return true;
        }
        else {

            if(fname==0) $('#fname-error').show();
            else $('#fname-error').hide();

            if(lname==0) $('#lname-error').show();
            else $('#lname-error').hide();

            if(username==0) $('#username-error').show();
            else $('#username-error').hide();

            if(pass==0) $('#pass-error').show();
            else $('#pass-error').hide();

            if(dept=='Select dept') $('#dept-error').show();
            else $('#dept-error').hide();

            if(job=='Select job') $('#job-error').show();
            else $('#job-error').hide();

            return false;
        }
    });

    $('#auth-form').submit(function(){
        var username = $.trim($('#username').val()).length;
        var pass = $.trim($('#pass').val()).length;
        if(username!=0 && pass != 0)
        {
            console.log($('#username').val());
            return true;
        }
        else {
            if(username==0) $('#username-error').show();
            else $('#username-error').hide();

            if(pass==0) $('#pass-error').show();
            else $('#pass-error').hide();

            return false;
        }
    });

    function validDate(date) {
        if(date.length==0)
        {
            return false;
        }
        var q = date.split(" ");
        var d = q[0].split(".");
        var t = q[1].split(":");

        var ddd = new Date(d[2],d[1]-1,d[0],t[0],t[1],0,0);
        var delta = ddd.getTime() - Date.now();
        if( delta <= 0) {

            return false;
        }
        else if(delta > 0) {

            return true;
        }
    }

    $('#newtask-form').submit(function(){
        var name = $('#name').val().length;
        var date = $('.date-cell').val();
        if(name!=0 && validDate(date))
        {
            return true;
        }
        else {
            if(name==0) $('#name-error').show();
            else $('#name-error').hide();

            if(!validDate(date)) $('#date-error').show();
            else $('#date-error').hide();
            return false;
        }
    });

    $('#delete-form').submit(function(){
        var checked_count = $('#tasks td input[type=checkbox]').filter(":checked").length;
        if(checked_count > 0)
        {
            if(confirm('Are you sure?'))
            {
                return true;
            }
            else {
                return false;
            }

        }
        else {
            alert('Please select task to delete');
            return false;
        }
    });

    $('#newsubtask-form').submit(function(){
        var name = $.trim($('#name').val()).length;
        var date = $('#date').val();
        if(name !=0 && validSubtaskDate(date))
        {
            return true;
        }
        else {
            if(name==0) $('#name-error').show();
            else $('#name-error').hide();

            if(!validSubtaskDate(date)) $('#date-error').show();
            else $('#date-error').hide();
            return false;
        }
    });

    function validSubtaskDate(date) {
        if(date.length ==0)
        {
            return false;
        }
        var q = date.split(" ");
        var d = q[0].split(".");
        var t = q[1].split(":");

        var stask_date = new Date(d[2],d[1]-1,d[0],t[0],t[1],0,0);

        var tdate = $('#task-date').val();
        var q1 = tdate.split(" ");
        var d1 = q1[0].split(".");
        var t1 = q1[1].split(":");

        var task_date = new Date(d1[2],d1[1]-1,d1[0],t1[0],t1[1],0,0);
        var delta = task_date.getTime() - stask_date.getTime();
        var delta1 = stask_date.getTime() - Date.now();

        if( delta <= 0 || delta1<=0) {

            return false;
        }
        else if(delta > 0 && delta1>0) {

            return true;
        }
    }
});
