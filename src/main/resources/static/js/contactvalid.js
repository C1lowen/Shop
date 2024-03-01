function validateSendServer() {
    let name = document.getElementById('name')
    let email = document.getElementById('email')
    let subject = document.getElementById('subject')
    let message = document.getElementById('message')
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    let error = document.getElementById('error-form')

    if(name.value.length <= 2 && name.value.length > 20) {
        name.style.border = 'red 1px solid'
        error.textContent = 'The name must contain more than 2 characters and less than 20'
        return
    }else {
        name.style.border = ''
        error.textContent = ''
    }

    if(message.value.length <= 5) {
        message.style.border = 'red 1px solid'
        error.textContent = 'The message should not be short'
        return
    }else {
        message.style.border = ''
        error.textContent = ''
    }

    if(subject.value.length <= 5) {
        subject.style.border = 'red 1px solid'
        error.textContent = 'The topic should not be short'
        return
    }else {
        subject.style.border = ''
        error.textContent = ''
    }
    if(email.value.length <= 5 || !emailPattern.test(email.value)) {
        email.style.border = 'red 1px solid'
        error.textContent = 'Enter correct email'
        return
    }else {
        email.style.border = ''
        error.textContent = ''
    }

    sendFormToServer(name.value, email.value, subject.value, message.value);


}

function sendFormToServer(name, email, subject, message) {
    let error = document.getElementById('error-form')
    let objectFormContact = {
        name: name,
        email: email,
        subject: subject,
        message: message
    }
    fetch('/contact/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objectFormContact)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.text();
        })
        .then(answerString => {
            if(answerString === 'true') {
                error.textContent = 'Your request has been received! The answer will be sent to you by email within 24 hours'
                error.style.color = 'green'
                error.style.fontWeight = 'bold'
            }else {
                error.textContent = 'Most likely you entered an invalid email'
                error.style.color = 'red'
                error.style.fontWeight = 'bold'
            }
        })
        .catch(error => {
            console.error(error);
        });
}

