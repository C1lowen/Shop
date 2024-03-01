function showVerificationField() {
    let emailInput = document.querySelector('input[name="email"]');
    let email = emailInput.value;
    sendVerificationRequest(email);
}

function sendVerificationRequest(email) {
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/send/email/activator", true);

    // Установка заголовка Content-Type
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                handleVerificationResponse(xhr.responseText, email);
            } else {
                console.error("Ошибка при отправке запроса");
            }
        }
    };


    xhr.send("email=" + encodeURIComponent(email));
}

function handleVerificationResponse(responseText, email) {
    let infoEmail = document.getElementById('infoEmail');
    if (responseText === 'true') {
        infoEmail.textContent = "Successfully sent"
        infoEmail.style.color = "green"
    } else {
        infoEmail.textContent = "This email is not registered!";
        infoEmail.style.color = "red"
    }
}

