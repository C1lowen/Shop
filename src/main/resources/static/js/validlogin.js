function validFromRegister(){

    let contactForm = document.getElementById('register_form');
    let usernameValue = String(contactForm.elements.name.value);
    let emailValue = String(contactForm.elements.email.value);
    let passValue = String(contactForm.elements.password.value);
    let passValueRepeat = String(contactForm.elements.confirmPassword.value);
    let messageElement = document.getElementById('valid-text-message');
    messageElement.style.color = "red";

    if(usernameValue.length < 3) {
        messageElement.textContent = 'Ник должен сожержать больше 3 символов';
        return false;
    }
    if(passValue.length < 8) {
        messageElement.textContent = 'Пароль должен сожержать больше 8 символов';
        return false;
    }
    if(passValue !== passValueRepeat) {
        messageElement.textContent = 'Пароли не совпадают';
        return false;
    }
    var userObject = {
        username: usernameValue,
        password: passValue,
        passwordRepeat: passValueRepeat,
        email: emailValue
    };

    fetch('/checksaveuser', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userObject)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = '/';
            }
            if(!data.success) {
                messageElement.textContent = data.answer;
            }

        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}