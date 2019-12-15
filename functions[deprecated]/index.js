const functions = require('firebase-functions');

const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

//Listens for new messages added to CustomerList/Customer1
exports.pushNotification = functions.database.ref('/CustomerList/Customer1/{pushId}').onWrite(event => {
    console.log('Push notification event triggered');

    var valueObject = event.data.val();

    const payload = {
        notification: {
            title: "Order ready!",
            body: "One of your orders is read for collection!",
            sound: "default"
        },
    };
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24 //24 hours
    };
    return admin.messaging().sendToTopic("notifications", payload, options);
});

// // Create and Deploy Your First Cloud Functions
// // https://firebase.google.com/docs/functions/write-firebase-functions
//
// exports.helloWorld = functions.https.onRequest((request, response) => {
//  response.send("Hello from Firebase!");
// });
