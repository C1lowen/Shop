let fullPrice = 0;
document.addEventListener('DOMContentLoaded', function () {
    // Получение данных из localStorage
    const favoriteData = localStorage.getItem('favorite');
    var cartContainer = document.getElementById('get-tbody');
    let cartTable = document.getElementById('table-cart-product')
    if (favoriteData) {
        try {


            // Преобразуйте данные в массив строк
            const favoriteArray = favoriteData ? JSON.parse(favoriteData) : [];

            // Преобразуйте массив в JSON-строку
            var jsonData = JSON.stringify({ local: favoriteArray });


            fetch('/findfavorite', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: jsonData
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Ошибка при отправке данных на сервер');
                    }

                    return response.json();
                })
                .then(cartData => {

                    let countProductCart = document.getElementById('nav-shop')
                    let authUser = cartData.auth
                    if (authUser) {
                        let textAuthElement = document.getElementById('size-cart-product').value;
                        if (parseInt(textAuthElement) === 0) {
                            cartTable.innerHTML = ''
                        }
                        return
                    }
                    let arrayProduct = cartData.products

                    countProductCart.textContent = arrayProduct.length + ''
                    if (arrayProduct.length !== 0) {
                        // Очищаем содержимое контейнера перед добавлением новых блоков
                        cartContainer.innerHTML = ''
                        // Генерируем блок для всех продуктов в корзине
                        arrayProduct.forEach(prod => {
                            fullPrice += prod.product.price * prod.count;
                            var productBlock = document.createElement('tr');
                            productBlock.innerHTML = `
                            <td>
                                <div class="media">
                                    <div class="d-flex">
                                        <img src="${prod.product.images}" width="120px" height="120px" alt="">
                                    </div>
                                    <div class="media-body">
                                        <p>${prod.product.name + '('}${prod.size + ')'}</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <h5 class="text-price" data-price="${prod.product.price}" id="price-${prod.product.id}">${prod.product.price}.00$</h5>
                                
                            </td>
                           
                           <td>
                               <div class="product_count">
                                  <input type="text" name="qty" class="sst input-text qty" maxlength="12" title="Quantity" value="${prod.count}" id="count-${prod.product.id}">
                                  <button onclick="updateQuantity(this, 1);" class="items-count" type="button"><i class="lnr lnr-chevron-up"></i></button>
                                  <button onclick="updateQuantity(this, -1);" class="reduced items-count" type="button"><i class="lnr lnr-chevron-down"></i></button>
                              </div>
                            </td>
                            <td>
                                <h5 class="text-sum-price">${prod.product.price * prod.count}.00$</h5>
                            </td>
                            <td>
                              <button class="delete-button" onclick="deleteProduct(this)" data-product-id="${prod.product.id}">
                                  <i class="fas fa-trash-alt"></i>
                              </button>
                               <input type="hidden" value="${prod.product.id}" id="prod-${prod.product.id}" class="hidden-id-product">
                              <input type="hidden" value="${prod.size}" id="size-${prod.product.id}">
                          </td>
                        `;


                            // Добавляем созданный блок в контейнер
                            cartContainer.appendChild(productBlock);
                        });
                    }
                    var productButton = document.createElement('tr');

                    productButton.className = 'bottom_button'
                    productButton.innerHTML =
                        '                              <td>\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        ' <div class="cupon_text d-flex align-items-center">' +
                        '<input type="text" placeholder="Coupon Code" id="text-promo">' +
                        '<a class="primary-btn" type="button" onclick="activatePromo()">Apply</a>' +
                        '</div>' +
                        '<span id="promo-not-active" style="color: red;"></span>' +
                        '</td>';

                    var productOne = document.createElement('tr');
                    productOne.innerHTML = '<tr>\n' +
                        '                              <td>\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '\n' +
                        '                              </td>\n' +
                        '            <td>\n' +
                        '    <h5 style="margin:10px;" id="dicount"></h5>\n' +
                        '    <h5 style="margin:10px;" id="full-price"></h5>\n' +
                        '    <h5 style="margin:10px;">Subtotal</h5>\n' +
                        '</td>\n' +
                        '<td>\n' +
                        '    <h5  id="dicount-size" style="color:red; margin:10px;"></h5>\n' +
                        '    <h5 style="margin:10px;" class="full-sum-text-price" id="full-sum-text-price">' + fullPrice.toFixed(2) + '$</h5>\n' +
                        '    <h5 style="margin:10px;" id="price-discount"></h>\n' +
                        '</td>               ' +
                        '                          </tr>'

                    var productShipping = document.createElement('tr');
                    productShipping.className = 'shipping_area'
                    productShipping.innerHTML =
                        '                              <td class="d-none d-md-block">\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '                                  <h5>Shipping</h5>\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '<div class="shipping_box">' +
                        '<ul class="list">' +
                        '<li>' +
                        '<span>Send by mail: $5.00</span>' +
                        '<input type="radio" name="shipping_method" value="send_by_mail" onclick="showSelect(1)">' +
                        '</li>' +
                        '<li>' +
                        '<span>Pick up from our branch: $0.00</span>' +
                        '<input type="radio" name="shipping_method" value="pick_up" onclick="showSelect(2)">' +
                        '</li>' +
                        '</ul>' +
                        '<h6>Delivery details <i class="fa fa-caret-down" aria-hidden="true"></i></h6>' +
                        '<select class="shipping_select" id="select1" style="display: none;" name="5">' +
                        '<option value="1">Mail 1</option>' +
                        '<option value="2">Mail 2</option>' +
                        '<option value="4">Mail 3</option>' +
                        '</select>' +
                        '<select class="shipping_select" id="select2" style="display: none;" name="0">' +
                        '<option value="1">Department 1</option>' +
                        '<option value="2">Department 2</option>' +
                        '<option value="4">Department 3</option>' +
                        '</select>' +
                        '<input type="text" class="number-text-check" id="phoneInput" placeholder="Phone Number" pattern="[0-9()+ -]*" required>' +
                        '<span id="phoneError" style="color: red;"></span>' +
                        '</div>' +
                        '                              </td>'
                    var productTwo = document.createElement('tr');
                    productTwo.className = 'out_button_area'
                    productTwo.innerHTML =
                        '                              <td class="d-none-l">\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td class="">\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '\n' +
                        '                              </td>\n' +
                        '                              <td>\n' +
                        '                                  <div class="checkout_btn_inner d-flex align-items-center">\n' +
                        '                                        <a class="gray_btn" href="/shop">Continue Shopping</a>' +
                        '                                        <a class="primary-btn ml-2" onclick="proceedToCheckout()">Proceed to checkout</a>' +
                        '                                  </div>\n' +
                        '                              </td>'
                    cartContainer.appendChild(productButton);
                    cartContainer.appendChild(productOne);
                    cartContainer.appendChild(productShipping);
                    cartContainer.appendChild(productTwo);

                })
                .catch(error => {
                    console.error(error);  // Обработка ошибок
                });
        } catch (parseError) {
            console.error('Ошибка парсинга JSON:', parseError);
        }
    }else {
        authUserCheck()
    }
});

function getFullSum() {
    return fullPrice;
}
document.addEventListener('input', function(event) {
    if (event.target.classList.contains('qty')) {
        const inputQty = event.target;
        if (inputQty.value === '') {
            inputQty.value = 1;
        } else if(parseInt(inputQty.value) > 99){
            inputQty.value = 99
        } else {
            inputQty.value = inputQty.value.replace(/[^0-9]/g, '');
        }
        jsValidInput(inputQty);
        updateTotalSum()
    }
});


function jsValidInput(input) {
    input.value = input.value.replace(/[^0-9]/g, '').replace(/^[0]*$/, 1)

    var trElement = input.closest('tr');

    var price = parseFloat(trElement.querySelector('.text-price').getAttribute('data-price'));
    var textSumPriceElement = trElement.querySelector('.text-sum-price');
    textSumPriceElement.textContent = (price * input.value).toFixed(2) + '$';
}

function authUserCheck(){
    fetch('/check/auth', {
        method: 'GET'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.text();
        })
        .then(data => {
            let textAuthElement = document.getElementById('size-cart-product').value;
            let cartTable = document.getElementById('table-cart-product')
            let tableResp = document.getElementById('table-responsive');
            let answerElem = document.createElement("h1")
            let result = data === 'true';
            if (!result) {
                cartTable.innerHTML = ''
                let img = document.createElement("img");
                img.src = "/img/product/1700495890_flomaster-top-p-grustnie-multyashnie-koti-narisovannie-mas-12.png"
                img.className = "img-cry-cat"

                answerElem.textContent = 'Cart is Empty'
                answerElem.style.margin = "0 auto"
                answerElem.style.color = "#8a8a8a"
                answerElem.style.textAlign = "center";

                tableResp.appendChild(img)
                tableResp.appendChild(answerElem)
            }
            if(parseInt(textAuthElement) === 0) {
                cartTable.innerHTML = ''
            }
        })
        .catch(error => {
            console.error(error);
        });
}


function proceedToCheckout(){
    if(!isAnyButtonChecked()){
        document.getElementById('phoneError').textContent = 'Choose a delivery method'
        return
    }
    let phoneInput = document.getElementById('phoneInput')
    if(!validateNumber(phoneInput)){
        return;
    }

    let activeOption = radioButtonElement()

    sendOrderSave(phoneInput.value, activeOption)
}


function sendOrderSave(phoneSend, deliverySend) {
    let priceSendString = getOrderPrice();
    let priceSend = parseFloat(priceSendString.replace('$', ''));
    let discountSendString = getOrderDiscount();
    let discountSend = parseInt(discountSendString.replace('%', '')) * -1
    let deliveryPriceSend = getOrderDeliveryPrice()
    let listProducts = getProductsList()
    let orderObject  =  {
        price: priceSend,
        discount: discountSend,
        products: listProducts,
        phone: phoneSend,
        delivery: deliverySend,
        deliveryPrice: deliveryPriceSend
    }
    fetch('/order/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(orderObject)
    })
        .then(response => response.json())
        .then(data => {
            if(data.validResponse) {
                let getId = data.id
                if(data.deleteLocalProduct){
                    localStorage.setItem("favorite", "");
                    localStorage.setItem("promo", "")
                    let listLocal = localStorage.getItem("orders")
                    let list = JSON.parse(listLocal) || [];
                    list.push(getId);
                    localStorage.setItem("orders", JSON.stringify(list));
                }
                window.location.href = "/order/successful?id=" + getId
            }else {
                document.getElementById('phoneError').textContent = 'Something went wrong'
            }
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}

function isAnyButtonChecked() {
    var radioButtons = document.getElementsByName("shipping_method");

    for (var i = 0; i < radioButtons.length; i++) {
        if (radioButtons[i].checked) {
            return true;
        }
    }

    return false;
}


function radioButtonElement(){
    let activeSelect = document.querySelector('.shipping_select[style="display: block;"]');
    let activeOption = activeSelect.querySelector('option:checked');

    return activeOption.textContent
}

function parseSumAndAdd(id, number){
    const selectedPrice = parseFloat(number);
    const totalElement = document.getElementById(id);

    const newTotal = parseFloat(totalElement.innerText) - previousPrice + selectedPrice;

    totalElement.innerText = newTotal + '.00$';
    previousPrice = selectedPrice;
}

let previousPrice = 0;
function setPrice(number) {
    let num = parseFloat(number)
    let discount = document.getElementById('dicount-size').textContent
    if(!discount) {
        parseSumAndAdd('full-sum-text-price', num)
    }else {
        parseSumAndAdd('price-discount', num)
    }
}

function getOrderPrice() {
    let discount = document.getElementById('dicount-size').textContent
    if(!discount) {
        return document.getElementById('full-sum-text-price').textContent
    }else {
        return document.getElementById('price-discount').textContent
    }
}

function getOrderDiscount(){
    let discount = document.getElementById('dicount-size').textContent
    if(!discount) {
        return '0'
    }else {
        return discount
    }
}

function getOrderDeliveryPrice(){
    const inputString = showSelectNew();

    const regex = /\$([0-9]+\.[0-9]{2})/;

    const match = inputString.match(regex);

    if (match && match[1]) {
        return match[1];
    } else {

        return '0.00'
    }
}

function showSelectNew() {
    const radioButtons = document.getElementsByName("shipping_method");

    let selectedRadioButton;
    for (const radioButton of radioButtons) {
        if (radioButton.checked) {
            selectedRadioButton = radioButton;
            break;
        }
    }

    if (selectedRadioButton) {
        const spanElement = selectedRadioButton.parentNode.querySelector("span");
        return spanElement.innerText;
    } else {

        return ''
    }
}

function getProductsList(){
    const hiddenIdElements = document.querySelectorAll('.hidden-id-product');

    const productIdList = [];

    hiddenIdElements.forEach(element => {
        const idValue = parseInt(element.value);
        let countProd = parseInt(document.getElementById('count-' + idValue).value)
        let sizeProd = parseInt(document.getElementById('size-' + idValue).value)

        const objectProductInfo =  {
            id: idValue,
            count: countProd,
            size: sizeProd
        }
        productIdList.push(objectProductInfo);
    });

   return productIdList;
}

// const deliveryButtons = document.querySelectorAll('.delivery-option');
// deliveryButtons.forEach((button) => {
//     button.addEventListener('change', (event) => {
//         const newPrice = parseFloat(event.target.value);
//         updatePrice(newPrice, 'full-sum-text-price');
//     });
// });