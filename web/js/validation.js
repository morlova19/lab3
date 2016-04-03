$(document).ready(function() {
    $('#register-form').submit(function () {

        var dept = $('#dept').find('option:selected').text();
        var job = $('#job').find('option:selected').text();
        var fname = $.trim($('#fname').val()).length;
        var lname = $.trim($('#lname').val()).length;
        var username = $.trim($('#username').val()).length;
        var pass = $.trim($('#pass').val()).length;

        if (fname != 0 && lname != 0 && username != 0 && pass != 0 && dept != 'Select dept' && job != 'Select job') {
            return true;
        }
        else {

            if (fname == 0) $('#fname-error').show();
            else $('#fname-error').hide();

            if (lname == 0) $('#lname-error').show();
            else $('#lname-error').hide();

            if (username == 0) $('#username-error').show();
            else $('#username-error').hide();

            if (pass == 0) $('#pass-error').show();
            else $('#pass-error').hide();

            if (dept == 'Select dept') $('#dept-error').show();
            else $('#dept-error').hide();

            if (job == 'Select job') $('#job-error').show();
            else $('#job-error').hide();

            return false;
        }
    });

    $('#search-form').submit(function () {
        var search = $.trim($('#search-input').val()).length;
        if (search != 0) {
            return true;
        }
        else {
            alert('Please enter text for searching tasks');
            return false;
        }
    });
    $('#auth-form').submit(function () {
        var username = $.trim($('#username').val()).length;
        var pass = $.trim($('#pass').val()).length;
        if (username != 0 && pass != 0) {
            return true;
        }
        else {
            if (username == 0) $('#username-error').show();
            else $('#username-error').hide();

            if (pass == 0) $('#pass-error').show();
            else $('#pass-error').hide();

            return false;
        }
    });



    $('#newtask-form').submit(function () {
        var name = $.trim($('#name').val()).length;
        var date = $('#tdate').val();
        var isSubtask = $('#pt_id').length;
        if (isSubtask == 0) {

            if (name != 0 && validDate(date)) {
                $('#tdate').css('border', '1px solid #ccc');
                $('#name').css('border', '1px solid #ccc');
                return true;
            }
            else {
                if (name == 0) {
                    $('#name-error').show();
                    $('#name').css('border', '1px solid red');
                }

                else {
                    $('#name-error').hide();
                    $('#name').css('border', '1px solid #ccc');
                }

                if (!validDate(date)) {
                    $('#date-error').show();
                    $('#tdate').css('border', '1px solid red');
                }
                else {
                    $('#date-error').hide();
                    $('#tdate').css('border', '1px solid #ccc');
                }
                return false;
            }
        }
        else {
            if (name != 0 && validSubtaskDate(date)) {
                $('#tdate').css('border', '1px solid #ccc');
                $('#name').css('border', '1px solid #ccc');
                return true;
            }
            else {
                if (name == 0) {
                    $('#name-error').show();
                    $('#name').css('border', '1px solid red');
                }
                else {
                    $('#name-error').hide();
                    $('#name').css('border', '1px solid #ccc');
                }

                if (!validSubtaskDate(date)) {
                    $('#date-error').show();
                    $('#tdate').css('border', '1px solid red');
                }
                else {
                    $('#date-error').hide();
                    $('#tdate').css('border', '1px solid #ccc');
                }
                return false;
            }
        }

    });

    $('.delete-form').submit(function () {
        return confirm('Are you sure?');

    });

    $('#update-task-form').submit(function () {
        var isSubtask = $('#pt_id').length;

        var name = $.trim($('#name').val()).length;
        var date = $('#tdate').val();
        if (isSubtask != 0) {
            if (name != 0 && validNewDate(date) && validSubtaskDate(date)) {
                $('#tdate').css('border', '1px solid #ccc');

                return true;
            }
            else {
                if (name == 0) $('#name-error').show();
                else $('#name-error').hide();

                if (!validNewDate(date) || !validSubtaskDate(date)) {
                    $('#date-error').show();
                    $('#tdate').css('border', '1px solid red');
                }
                else {
                    $('#date-error').hide();
                }
                return false;
            }
        }
        else {

            if (name != 0 && validNewDate(date)) {
                return true;
            }
            else {
                if (name == 0) $('#name-error').show();
                else $('#name-error').hide();

                if (!validNewDate(date)) $('#date-error').show();
                else $('#date-error').hide();
                return false;
            }
        }


    });
    function validDate(date) {
        if (date.length == 0) {
            return false;
        }
        var q = date.split(" ");
        var d = q[0].split(".");
        var t = q[1].split(":");

        var ddd = new Date(d[2], d[1] - 1, d[0], t[0], t[1], 0, 0);

        if(t[0]>23 || t[1]>59 || d[0]>31 || d[1] > 13 ||  d[0]==0 || d[1]==0 || d[2]==0)
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

        var tdate = $('#task-date').val();
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

    function validNewDate(date) {
        if (date.length == 0) {
            return false;
        }
        var q = date.split(" ");
        var d = q[0].split(".");
        var t = q[1].split(":");

        var ddd = new Date(d[2], d[1] - 1, d[0], t[0], t[1], 0, 0);
        var delta = ddd.getTime() - Date.now();
        var max_stask_date = maxSubtaskDate();

        var delta1 = ddd.getTime() - max_stask_date;
        var r = delta > 0 && delta1 > 0;
        return r == true;
    }

    function maxSubtaskDate() {
        var items = [];
        $('#tasks').find('tbody  tr td:nth-child(3)').each(function () {
            var str1 = $.trim($(this).text());

            if (str1.length != 0) {
                A = parseDate(str1);
                items.push(A);
            }
        });
        return Math.max.apply(Math, items);
    }
    function parseDate(date) {

        var q = date.split(" ");

        var d = q[0].split(".");

        var t = q[1].split(":");
        var ddd = new Date(d[2], d[1] - 1, d[0], t[0], t[1], 0, 0);
        return ddd.getTime();
    }
});
  