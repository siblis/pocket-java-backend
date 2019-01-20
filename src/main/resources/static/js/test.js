'use strict';

var stompClient = null;
var username = null;

var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('#connecting');

var inputForm = document.querySelector('#inputForm');
var inputField1 = document.querySelector('#field1');

var inputForm2 = document.querySelector('#inputForm2');
var inputField2 = document.querySelector('#field2');

var textMessageBlock = document.querySelector('#textMessage');
var textField1Block = document.querySelector('#textField1');
var textField2Block = document.querySelector('#textField2');

var button2 = document.querySelector('#button2');
var button3 = document.querySelector('#button3');

//var messageArea2 = document.querySelector('#messageArea2');

function connect() {
    username = document.querySelector('#username').innerText.trim();

    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}


function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/test', onMessageReceived);
    //stompClient.subscribe('/topic/testsubscribe', onMessageReceived2);

    // Tell your username to the server
    stompClient.send("/app/test",
        {},
        JSON.stringify({username: username, email: 'a@m.com', text: 'start test'})
    )

//    stompClient.send("/app/testsubscribe",
//        {},
//        JSON.stringify({username: username, email: 'aa@mm.com', text: 'start test testSubscribe'})
//    )

    connectingElement.classList.add('hidden');
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    //messageArea2.appendChild(messageContent);
    if(messageContent && stompClient) {
        var testMessage = {
            username: username,
            email: 'b@m.com',
            text: messageContent
        };
        stompClient.send("/app/test", {}, JSON.stringify(testMessage));

//        // создаем новый параграф
//        var pElement = document.createElement("p");
//        // устанавливаем в параграф текст
//        pElement.textContent = messageInput.value;
        // добавляем параграф в Block
        textMessageBlock.appendChild(document.createTextNode(messageContent));
        messageInput.value = '';
    }
    event.preventDefault();
}

function sendMessage2(event) {
    var messageContent = inputField1.value.trim();
        if(messageContent && stompClient) {
        var testMessage = {
            username: username,
            email: 'c@m.com',
            text: messageContent
        };
        stompClient.send("/app/test", {}, JSON.stringify(testMessage));

        textField1Block.appendChild(document.createTextNode(messageContent));
        inputField1.value = '';
    }
    event.preventDefault();
 }

function sendMessage3(event) {
    var messageContent = inputField1.value.trim();
    if(messageContent && stompClient) {
        var testMessage = {
            username: username,
            email: 'd@m.com',
            text: 'sendMessage3 ' + messageContent
        };
        stompClient.send("/app/test", {}, JSON.stringify(testMessage));

        textField1Block.appendChild(document.createTextNode(messageContent));
        inputField1.value = '';
    }
    event.preventDefault();
}

function sendMessage4(event) {
    var messageContent = inputField1.value.trim();
    if(messageContent && stompClient) {
        var testMessage = {
            username: username,
            email: 'e@m.com',
            text: 'sendMessage4 ' + messageContent
        };
        stompClient.subscribe('/topic/testsubscribe', onMessageReceived2);
        //stompClient.send("/app/testsubscribe", {}, JSON.stringify(testMessage));

        textField1Block.appendChild(document.createTextNode(messageContent));
        inputField1.value = '';
    }
    event.preventDefault();
}

function sendMessage5(event) {
    var messageContent = inputField2.value.trim();
    if(messageContent && stompClient) {
        var testMessage = {
            username: username,
            email: 'f@m.com',
            text: 'sendMessage5 ' + messageContent
        };
        stompClient.send("/app/testsubscribe", {}, JSON.stringify(testMessage));

        textField2Block.appendChild(document.createTextNode(messageContent));
        inputField2.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    var usernameText = document.createTextNode(message.username);
    var emailText = document.createTextNode(message.email);
    var messageText = document.createTextNode(message.text);

    if(message.email === 'aa@m.com') {
        messageElement.classList.add('event-message');
        message.text = message.username + ' joined!';
    } else if (message.email === 'bb@m.com') {
        messageElement.classList.add('event-message');
        message.text = message.username + ' left!';
    } else {
        messageElement.classList.add('test-message');

        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');
        usernameElement.appendChild(usernameText);
        usernameElement.appendChild(document.createTextNode('\n('));
        usernameElement.appendChild(emailText);
        usernameElement.appendChild(document.createTextNode(')'));

        messageElement.appendChild(usernameElement);
    }

    var textElement = document.createElement('span');
//    textElement.appendChild(emailText);
//    textElement.appendChild(document.createTextNode(', '));
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;

//    messageArea2.appendChild(messageElement);
//    messageArea2.scrollTop = messageArea.scrollHeight;

}

function onMessageReceived2(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    var usernameText = document.createTextNode(message.username);
    var emailText = document.createTextNode(message.email);
    var messageText = document.createTextNode(message.text);

    messageElement.classList.add('test-message');

    var usernameElement = document.createElement('strong');
    usernameElement.classList.add('nickname');
    usernameElement.appendChild(usernameText);

    usernameElement.appendChild(document.createTextNode('\f('));
    usernameElement.appendChild(emailText);
    usernameElement.appendChild(document.createTextNode(')'));

    messageElement.appendChild(usernameElement);

    var textElement = document.createElement('span');
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;

}


// Connect to WebSocket Server.
connect();


messageForm.addEventListener('submit', sendMessage, true);
inputForm.addEventListener('submit', sendMessage2, true);
button2.addEventListener("click", sendMessage3, true);
button3.addEventListener("click", sendMessage4, true);
inputForm2.addEventListener('submit', sendMessage5, true);
