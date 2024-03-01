document.addEventListener('DOMContentLoaded', function () {
    let productId = document.getElementById('id-product').value
    let idArrays = localStorage.getItem("favorite")
    let button = document.querySelector('.primary-btn');
    fetch('/authuser/' + productId, {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(user => {
            if(user.auth){
                if(user.findProduct) {
                    button.innerHTML = 'Delete to Cart';
                    button.classList.add('added');
                    button.setAttribute('disabled', true)
                }else {
                    button.innerHTML = 'Add to Cart';
                    button.classList.remove('added');
                    button.removeAttribute('disabled');
                }
            }else {
                const storedDataString = localStorage.getItem('favorite');
                const favoriteArray = storedDataString ? JSON.parse(storedDataString) : [];
                if(favoriteArray.some(item => item.productId === Number(productId))) {
                    button.innerHTML = 'Delete to Cart';
                    button.classList.add('added');
                    button.setAttribute('disabled', true)
                }else {
                    button.innerHTML = 'Add to Cart';
                    button.classList.remove('added');
                    button.removeAttribute('disabled');
                }
            }
        })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });
})

function addToFavorites() {
    let productId = document.getElementById('id-product').value
    var button = document.querySelector('.primary-btn');
    let countProductCart = document.getElementById('nav-shop')
    let numberStr = countProductCart.textContent
    if(numberStr === '') numberStr = 0;
    if (button.classList.contains('added')) {
        // Если кнопка уже была добавлена, возвращаем её к исходному состоянию
        button.innerHTML = 'Add to Cart';
        button.classList.remove('added');
        button.removeAttribute('disabled');
        countProductCart.textContent = parseInt(numberStr) - 1;
        deleteFavoritesToServer(productId)
    } else {
        let textRadioButton = radioButtonActive();

        if(textRadioButton === null) {
            document.getElementById('error-size').textContent = "You haven't chosen your shoe size"
            return
        }
        let numberRadioButton = parseInt(textRadioButton)
        let count =  parseInt(document.getElementById('sst').value)

        button.innerHTML = 'Delete to Cart';
        button.classList.add('added');
        button.setAttribute('disabled', true);
        countProductCart.textContent = parseInt(numberStr) + 1;
        sendFavoritesToServer(productId,numberRadioButton, count);
    }
}
function radioButtonActive() {
    var rootSizeProducts = document.querySelector('.root-size-products');

// Проверяем, какая из радиокнопок внутри этого элемента активна
    var radioButtons = rootSizeProducts.querySelectorAll('.size-input');
    let result = null;
    radioButtons.forEach(function(radioButton) {
        if (radioButton.checked) {
            var label = rootSizeProducts.querySelector('[for="' + radioButton.id + '"]');
            result = label.textContent;
        }
    });

    return result;
}
function addToFavoritesCategory(button) {
    var productId = button.closest('.col-md-6.col-lg-4').querySelector('.product-id').value;
    if (button.classList.contains('added')) {
        changeButton(button, 'ti-shopping-cart')
        button.classList.remove('added');

        deleteFavoritesToServer(productId)
    } else {
        changeButton(button, 'fa fa-trash')
        button.classList.add('added');

        sendFavoritesToServer(productId, 0, 1);
    }
}

function changeButton(button, nameClass){
    var newIcon = document.createElement('i');
    newIcon.className = nameClass;
    var existingIcon = button.querySelector('i');
    if (existingIcon) {
        button.removeChild(existingIcon);
    }
    button.appendChild(newIcon);
}

// Функция для отправки данных на сервер
function sendFavoritesToServer(productId, textRadioButton, count) {
    let countProductCart = document.getElementById('nav-shop')
    let numberStr = countProductCart.textContent
    // Пример с использованием Fetch API
    fetch('/addfavorite/' + productId + '?size=' + textRadioButton + '&count=' + count, {
        method: 'POST',
        credentials: 'same-origin'// Важно для передачи Cookies
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(data => {

            if(!data.auth) {
                const storedDataString = localStorage.getItem('favorite');
                const favoriteArray = storedDataString ? JSON.parse(storedDataString) : [];
                const { auth, products } = data;
                let finalArray = mergeUniqueElements(favoriteArray, products);
                countProductCart.textContent = finalArray.length;
                localStorage.setItem("favorite", JSON.stringify(finalArray));
            }else {
                countProductCart.textContent = data.products.length
            }
        })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });
}

const inputQtyTwo = document.querySelector('.product_count input[type="text"]');

inputQtyTwo.addEventListener('input', () => {
    if (inputQtyTwo.value === '') {
        inputQtyTwo.value = 1;
    } else {
        let parsedValue = parseInt(inputQtyTwo.value);
        if (isNaN(parsedValue) || parsedValue < 1) {
            inputQtyTwo.value = 1;
        } else if (parsedValue > 99) {
            inputQtyTwo.value = 99;
        } else {
            inputQtyTwo.value = parsedValue;
        }
    }
});

function updateQuantity2(button, action) {
    var inputElement = button.parentNode.querySelector('input[name="qty"]');
    var currentValue = parseInt(inputElement.value) || 0;

    if (action === 'add') {
        inputElement.value = Math.min(currentValue + 1, 99);
    } else if (action === 'subtract') {
        inputElement.value = Math.max(currentValue - 1, 1);
    }

    // Дополнительная проверка, чтобы предотвратить ввод значений больше 99
    if (parseInt(inputElement.value) > 99) {
        inputElement.value = 99;
    }
}


function deleteFavoritesToServer(productId) {
    let countProductCart = document.getElementById('nav-shop')
    let numberStr = countProductCart.textContent
    fetch('/deletefavorite/' + productId, {
        method: 'POST',
        credentials: 'same-origin'// Важно для передачи Cookies
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(data => {
            if (!data.auth) {
                const storedDataString = localStorage.getItem('favorite');
                const favoriteArray = storedDataString ? JSON.parse(storedDataString) : [];
                const updatedFavoriteArray = favoriteArray.filter(
                    (favorite) => favorite.productId !== Number(productId)
                );
                const { auth, products } = data;
                let finalArray = mergeUniqueElements(updatedFavoriteArray, products);
                if(numberStr === '') numberStr = 0;
                countProductCart.textContent = finalArray.length;
                localStorage.setItem("favorite", JSON.stringify(finalArray));
            }else {
                countProductCart.textContent = data.products.length
            }

        })
        .catch(error => {
            console.error(error);
        });
}

function mergeUniqueElements(target, source) {
    source.forEach(sourceItem => {
        const isUnique = !target.some(targetItem => isEqual(targetItem, sourceItem));
        if (isUnique) {
            target.push(sourceItem);
        }
    });

    return target;
}

function isEqual(obj1, obj2) {
    return JSON.stringify(obj1) === JSON.stringify(obj2);
}
