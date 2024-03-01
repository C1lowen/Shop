function showSelect(selectNumber) {
    // Скрываем все shipping_select
    document.getElementById('select1').style.display = 'none';
    document.getElementById('select2').style.display = 'none';

    // Отображаем выбранный shipping_select
    let select = document.getElementById('select' + selectNumber)
    select.style.display = 'block'
    setPrice(select.name)
}


document.getElementById('phoneInput').addEventListener('input', function () {
    var phoneInput = this;
    validateNumber(phoneInput)
});

function validateNumber(phoneInputNow){
    let phoneInput = phoneInputNow.value
    var phonePattern = /^[0-9()+ -]*$/;
    let phoneError = document.getElementById('phoneError')
    let checkPlus = phoneInput.indexOf('+')
    if((checkPlus !== -1 && phoneInput.length !== 13) ||
        (checkPlus === -1 && phoneInput.length !== 10) ||
        phoneInput.length > 13 || phoneInput < 10){
        phoneError.textContent = 'Invalid phone number'
        phoneInputNow.style.border = "red 1px solid"
        return false;
    }else if (phonePattern.test(phoneInput)) {
        phoneError.textContent = '';
        phoneInputNow.style.border = "1px solid #eeeeee"
        return true;
    } else {
        phoneError.textContent = 'Invalid phone number'
        phoneInputNow.style.border = "red 1px solid"
        return false;
    }
}