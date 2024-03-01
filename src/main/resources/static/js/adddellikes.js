function addLikesToCart(button){
    var productId = button.getAttribute('data-product-id');
    fetch('likes/save/cart/' + productId, {
        method: 'GET'
    })
        .then(response => {
        if (!response.ok) {
            throw new Error('Ошибка при отправке данных на сервер');
        }
        return response.text(); // Извлекаем текстовое содержимое ответа
    })
        .then(answer => {
            var result = answer === 'true';
            if (result) {
                location.reload();
            }
        })
        .catch(error => {
            console.error(error);
        });
}

function deleteLikes(button){
    var productId = button.getAttribute('data-product-id');
    fetch('likes/delete/' + productId, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.text(); // Извлекаем текстовое содержимое ответа
        })
        .then(answer => {
            var result = answer === 'true';
            if (result) {
                location.reload();
            }
        })
        .catch(error => {
            console.error(error);
        });
}