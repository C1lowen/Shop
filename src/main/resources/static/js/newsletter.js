function validateEmail() {
    let inputEmail = document.getElementById('email-text')
    let textEmail = inputEmail.value
    let error = document.getElementById('error')
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (textEmail.length <= 5 || !emailPattern.test(textEmail)) {
        error.textContent = 'Enter correct email'
        error.style.color = "red"
        return
    }

    sendToSaveServer(textEmail)
}

function sendToSaveServer(email) {
    let error = document.getElementById('error')
    let obj = {email:email}

    fetch('/newsletter/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(obj)
    })
        .then(response => response.text())
        .then(data => {
            if(data === 'true') {
                error.textContent = 'Congratulations! You have subscribed to the newsletter'
                error.style.color = "green"
            }else {
                error.textContent = 'It looks like you have already subscribed to the newsletter from this email'
                error.style.color = "red"
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}