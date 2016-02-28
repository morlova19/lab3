 if ('serviceWorker' in navigator) {
        console.log('Service Worker is supported');
        navigator.serviceWorker.register('/lab3_war_exploded/js/sw.js').then(function (reg) {
            console.log(':^)', reg);
            reg.pushManager.subscribe({
                userVisibleOnly: true
            }).then(function (sub) {
                console.log('endpoint:', sub.endpoint);
                $.get("regId", {'endpoint': sub.endpoint});
            });

        }).catch(function (error) {
            console.log(':^(', error);
        });
    }
