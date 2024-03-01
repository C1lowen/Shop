document.addEventListener('DOMContentLoaded', function() {
    updateTotalSum();
});

function updateQuantity(button, delta) {
    var productCountDiv = button.closest('.product_count');
    var input = productCountDiv.querySelector('.sst');
    var value = parseInt(input.value) + delta;

    if (!isNaN(value) && value > 0 && value < 100) {
        input.value = value;

        // Получение цены товара
        var priceElement = productCountDiv.closest('tr').querySelector('.text-price');
        var price = parseFloat(priceElement.innerText.replace('$', ''));

        // Вычисление и обновление индивидуальной суммы
        var sumPriceElement = productCountDiv.closest('tr').querySelector('.text-sum-price');
        var sumPrice = price * value;
        sumPriceElement.innerText = sumPrice.toFixed(2) + '$';

        // Обновление общей суммы
        updateTotalSum();
    }
}


let totalSum = 0;
function updateTotalSum() {
    const hiddenIdProducts = document.querySelectorAll('.hidden-id-product');
    const discount = document.getElementById('dicount-size');
    const discountString = discount.textContent;
    let result = 0
    hiddenIdProducts.forEach((element) => {

        const value = element.value;
        let count = parseFloat(document.getElementById('count-' + value).value)
        let sum = document.getElementById('price-' + value)
        const dataPriceValue = parseFloat(sum.getAttribute('data-price'))

        result += count * dataPriceValue;

    });
    totalSum = result
    let delivery = 0;
    if (discountString === '') {
        delivery = parseFloat(getOrderDeliveryPrice())
    }
    let fullSumTextPriceElement = document.querySelector('.full-sum-text-price');
    fullSumTextPriceElement.innerText = (totalSum + delivery).toFixed(2) + '$';
    updateDiscountPrice()
}

function updateDiscountPrice() {
    let fullSumTextPrice = document.getElementById('full-sum-text-price');
    let priceDiscount = document.getElementById('price-discount');
    const discount = document.getElementById('dicount-size');

    const newPrice = parseFloat(fullSumTextPrice.textContent.replace(/[^0-9.-]+/g, ''));
    const discountString = discount.textContent;

    if (discountString !== '') {
        const discountString = discount.textContent;
        let parsedDiscount = parseFloat(discountString.replace(/[^0-9.-]+/g, ''));
        parsedDiscount *= -1
        let discountPrice = newPrice - (newPrice * (parsedDiscount / 100));
        let delivery = parseFloat(getOrderDeliveryPrice())
        discountPrice += delivery

        priceDiscount.textContent = discountPrice.toFixed(2) + '$';
    }
}

function getTotalSum() {
    return totalSum;
}

function deleteAllByLikes(){
    fetch('/likes/delete', {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(resp => {
            if(resp.answer === 'Your products have been successfully deleted!') {
                location.reload();
            }
         })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });
}

function saveAllByLikesFromCart(){
    fetch('/likes/save/all', {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(answer => {
            if(answer.success === true) {
                location.reload();
            }
        })
        .catch(error => {
            console.error(error);
        });
}