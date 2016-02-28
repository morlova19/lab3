console.log('Started', self);
var SOME_API_ENDPOINT = 'http://localhost:8081/lab3_war_exploded/PushServlet?endpoint='
registration.pushManager.getSubscription()
    .then(function(subscription) {
        SOME_API_ENDPOINT += subscription.endpoint;
    });
;
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

                return self.registration.showNotification(title, {
                    body: message,
                    icon: 'images/icon.png'
                });
            });
        }).catch(function(err) {
            var title = 'An error occurred';
            var message = 'We were unable to get the information for this push message';
            var icon = 'images/icon.png';
            var notificationTag = 'notification-error';
            return self.registration.showNotification(title, {
                body: message,
                icon: icon,
                tag: notificationTag
            });
        })
    );
});
self.addEventListener('notificationclick', function(event) {

});