function validateTrackingCode(){
    let valueId =  document.getElementById('value-tracking-id').value

    if(isNumericString(valueId)) {
        validateTrackingCodeToServer(valueId)
    } else {
        let errorValue = document.getElementById('error-value-tracking')
        errorValue.textContent = "No such order exists"
    }
}

function isNumericString(str) {
    var numericPattern = /^\d+$/;

    return numericPattern.test(str);
}

function validateTrackingCodeToServer(codeId){
    fetch('/order/tracking/code/' + codeId, {
        method: 'GET'
    })
        .then(response => response.json())
        .then(data => {
            let errorValue = document.getElementById('error-value-tracking')

            if(data.length !== 0) {
                createAnswer(data)
                errorValue.textContent = ''
            }else {
                const productContainer = document.getElementById('root-product-container')
                productContainer.innerHTML = ''
                errorValue.textContent = "No such order exists"
            }

        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}

function createAnswer(orders) {

    // Создаем контейнер для товаров
    const productContainer = document.getElementById('root-product-container')
    productContainer.innerHTML = ''
    // Проходим по каждому заказу и создаем блок с данными
    orders.forEach(order => {
        // Создаем элементы
        const productItem = document.createElement('div');
        const productImage = document.createElement('div');
        const productDetails = document.createElement('div');
        const img = document.createElement('img');
        const productName = document.createElement('div');
        const productQuantity = document.createElement('div');
        const productTotal = document.createElement('div');

        // Заполняем элементы данными из заказа
        img.src = order.pathImages;
        img.alt = 'Product Image';
        productName.textContent = order.name;
        productQuantity.textContent = 'x' + order.count;
        productTotal.textContent = '$' + order.price.toFixed(2);

        // Добавляем классы и структуру блока
        productItem.classList.add('product-item');
        productImage.classList.add('product-image');
        productDetails.classList.add('product-details');
        productName.classList.add('product-name')
        productQuantity.classList.add('product-quantity')
        productTotal.classList.add('product-total')

        productImage.appendChild(img);
        productDetails.appendChild(productName);
        productDetails.appendChild(productQuantity);
        productDetails.appendChild(productTotal);

        productItem.appendChild(productImage);
        productItem.appendChild(productDetails);

        // Добавляем блок в контейнер для товаров
        productContainer.appendChild(productItem);
    });
}