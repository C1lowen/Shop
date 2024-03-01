function changePassword(){
    let passwordOrig = document.getElementById('password-orig').value
    let passwordRepeat = document.getElementById('password-repeat').value
    let verifPass = document.getElementById('verifPass');
    if(passwordOrig !== passwordRepeat){
        verifPass.textContent = "Passwords don't match"
        return;
    }else if(passwordOrig.length < 8 || passwordRepeat.length < 8) {
        verifPass.textContent = 'The password cannot be less than 8 characters'
        return;
    }
    var currentUrl = window.location.href;
    var url = new URL(currentUrl);
    var lastPathSegment = url.pathname.split('/').pop();
    sendNewPassword(passwordRepeat, passwordOrig, lastPathSegment);
}

function sendNewPassword(passwordRepeat, passwordOrig, code){
    let passObject = {
        originalCode: code,
        passOrig: passwordOrig,
        passRepeat: passwordRepeat
    };
    let verifPass = document.getElementById('verifPass');
    fetch('/change/password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(passObject)
    })
        .then(response => response.json())
        .then(data => {
            if(data.change) {
                window.location.href = '/login'
            }else {
                verifPass.textContent = data.answer;
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}