document.addEventListener('DOMContentLoaded', function() {
    let namePromo= localStorage.getItem("promo")
    if (!namePromo) namePromo = "NULL"

    fetch('promo/loadpage/activation/' + namePromo, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(promoDTO => {
            if(promoDTO.active && promoDTO.auth) {
                setDiscount(promoDTO)
            } else if(promoDTO.active && !promoDTO.auth){
                setDiscount(promoDTO)
            }else if(!promoDTO.active && !promoDTO.auth) {
                 localStorage.removeItem("promo")
            }
        })
        .catch(error => {
            console.error(error);
        });
});

function activatePromo(){
    let promo = document.getElementById('text-promo').value
    if(!promo) return
    let namePromo= localStorage.getItem("promo");

    fetch('activation/promo?print=' + promo + '&local=' + namePromo, {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(promoDTO => {


            if(promoDTO.active && promoDTO.auth) {
                setDiscount(promoDTO)
            }else if(promoDTO.active && !promoDTO.auth){
                localStorage.setItem("promo", promoDTO.promo)
                setDiscount(promoDTO)
            } else {
                let promoNotActive = document.getElementById('promo-not-active');
                promoNotActive.textContent = promoDTO.reason
            }
        })
        .catch(error => {
            console.error(error);
        });
}


function setDiscount(promoDTO){
    let fullPrice = document.getElementById('full-sum-text-price')
    let priceDiscount = document.getElementById('price-discount')
    let sizeDiscount = document.getElementById('dicount-size');
    let textDiscount = document.getElementById('dicount')
    let priceNoDiscount = document.getElementById('full-price')
    let inputPromo = document.getElementById('text-promo')
    let numericFullPrice = parseFloat(fullPrice.textContent.replace('$', ''));
    let discountPercentage = promoDTO.discount;
    let sum = sumAddFullPrice()
    let fullPriceOriginalNoAuth = getFullSum();
    let fullPriceOriginalAuth = getTotalSum();
    let fullPriceOriginal = fullPriceOriginalNoAuth === 0 ? fullPriceOriginalAuth : fullPriceOriginalNoAuth



    if(fullPriceOriginal !== numericFullPrice) {
        numericFullPrice -= sum;
        fullPrice.textContent = numericFullPrice.toFixed(2) + '$'
    }
    let discountAmount = (numericFullPrice * discountPercentage) / 100;
    let discountedPrice = numericFullPrice - discountAmount + sum;
    fullPrice.style.textDecoration = 'line-through';
    inputPromo.style.border = '1px solid green'
    sizeDiscount.textContent = '-' + promoDTO.discount + '%'
    priceNoDiscount.textContent = 'Full price'
    textDiscount.textContent = 'Discount'
    priceDiscount.textContent = discountedPrice.toFixed(2) + '$';
}


function sumAddFullPrice() {

    let sum;
    let activeRadioButton = showSelectNew();
    let regex = /\$([\d,]+(?:\.\d{1,2})?)/;
    let matches = activeRadioButton.match(regex);

    if (matches && matches.length > 1) {
        sum = parseFloat(matches[1].replace(',', ''));
    } else {
        sum = 0;
    }
    return sum;
}
