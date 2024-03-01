document.addEventListener('DOMContentLoaded', function () {
    let productId = document.getElementById('id-product').value
    let element = document.getElementById('icon-heart')
    fetch('/likes/product/' + productId, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(data => {
            if(data.success){
                changeButton(element, 'fa-regular fa-heart')
                element.classList.add('added');
            }
        })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });

})

function addToLikesCategory(button) {
    let productId = button.closest('.col-md-6.col-lg-4').querySelector('.product-id').value;
    let countLikesCart = document.getElementById('nav-likes')
    let numberStr = countLikesCart.textContent
    if (button.classList.contains('added')) {
        changeButton(button, 'ti-heart')
        button.classList.remove('added');
        countLikesCart.textContent = parseInt(numberStr) - 1;
        deleteLikesToServer(productId)
    } else {
        changeButton(button, 'fa-regular fa-heart')
        button.classList.add('added');
        countLikesCart.textContent = parseInt(numberStr) + 1;
        sendLikesToServer(productId);
    }
}

function clickLikes(element) {
    let productId = document.getElementById('id-product').value
    let countLikesCart = document.getElementById('nav-likes')
    let numberStr = countLikesCart.textContent
    if (element.classList.contains('added')) {
        changeButton(element, 'lnr lnr lnr-heart')
        element.classList.remove('added');
        countLikesCart.textContent = parseInt(numberStr) - 1;
        deleteLikesToServer(productId)
    } else {
        changeButton(element, 'fa-regular fa-heart')
        element.classList.add('added');
        countLikesCart.textContent = parseInt(numberStr) + 1;
        sendLikesToServer(productId);
    }
}

function sendLikesToServer(id) {
    fetch('/likes/save/' + id, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
        })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });
}

function deleteLikesToServer(id) {
    fetch('/likes/delete/' + id, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
        })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });
}
