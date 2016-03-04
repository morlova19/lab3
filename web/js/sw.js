console.log('Started', self);
var SOME_API_ENDPOINT = 'http://localhost:8081/lab3_war_exploded/PushServlet?endpoint='
registration.pushManager.getSubscription()
    .then(function(subscription) {
        SOME_API_ENDPOINT += subscription.endpoint;
    });
var empid = -1;
var taskid = -1;
var subtaskid = -1;
self.addEventListener('push', function(event) {
    event.waitUntil(
        fetch(SOME_API_ENDPOINT).then(function(response) {
            if (response.status !== 200) {
                // Either show a message to the user explaining the error
                // or enter a generic message and handle the
                // onnotificationclick event to direct the user to a web page
                console.log('Looks like there was a problem. Status Code: ' + response.status);
                throw new Error();
            }

            // Examine the text in the response
            return response.json().then(function(data) {
                console.log(data);
                if (data.error || !data.name) {
                    console.error('The API returned an error.', data.error);
                    throw new Error();
                }

                var title = data.name;
                var message = data.description;
                var data1 = {
                    empid: data.empid,
                    taskid : data.taskid,
                    subtaskid : data.subtaskid,
                };
                return self.registration.showNotification(title, {
                    body: message,
                    icon: 'http://localhost:8081/lab3_war_exploded/images/icon.png',
                    data:data1
                });
            });
        }).catch(function(err) {
            var title = 'An error occurred';
            var message = 'We were unable to get the information for this push message';
            var icon = 'images/icon.png';
            var notificationTag = 'notification-error';
            return self.registration.showNotification(title, {
                body: message,
                icon: icon
            });
        })
    );
});
self.addEventListener('notificationclick', function(event) {
    console.log("ffffff", event.notification);
    console.log('Notification click: tag ', event.notification.tag);
    event.notification.close();
    var empid = event.notification.data.empid;
    var taskid = event.notification.data.taskid;
    var subtaskid = event.notification.data.subtaskid;
    var url;
    if(empid==-1 && taskid==-1 && subtaskid==-1)
    {
        url ='http://localhost:8081/lab3_war_exploded/start.jsp';
    }
    else {
        if (subtaskid == -1) {
            url = 'http://localhost:8081/lab3_war_exploded/complete_task.jsp?empid=' + empid + '&taskid=' + taskid;
        }
        else {
            url = 'http://localhost:8081/lab3_war_exploded/complete_subtask.jsp?empid=' + empid + '&taskid=' + taskid + '&stid=' + subtaskid;
        }
    }
    event.waitUntil(
        clients.matchAll({
                type: 'window'
            })
            .then(function(windowClients) {
                for (var i = 0; i < windowClients.length; i++) {
                    var client = windowClients[i];
                    if (client.url === url && 'focus' in client) {
                        return client.focus();
                    }
                }
                if (clients.openWindow) {
                    return clients.openWindow(url);
                }
            })
    );
});