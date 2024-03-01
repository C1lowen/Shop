document.addEventListener('DOMContentLoaded', function() {
    let local = localStorage.getItem("orders");
    let list = JSON.parse(local) || [];
    fetch('/order/get/list?orders=' + list, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    })
        .then(response => response.json())
        .then(data => {

            var ordersContainer = document.getElementById("ordersContainer");

            // Проходим по каждому заказу
            data.forEach(function (order) {
                // Создаем элементы для заказа
                var orderBlock = document.createElement("div");
                orderBlock.className = "order-block";

                var orderTitle = document.createElement("h2");
                orderTitle.textContent = "Order #" + order.id;
                orderBlock.appendChild(orderTitle);

                // Проходим по каждому продукту в заказе
                order.products.forEach(function (product) {
                    var productRow = document.createElement("div");
                    productRow.className = "product-row";

                    var productInfo = document.createElement("div");
                    productInfo.className = "product-info";
                    var productImage = document.createElement("img");
                    productImage.src = product.images;
                    productImage.style.border = "#1dc116"
                    var productName = document.createElement("p");
                    productName.textContent = product.name;
                    productName.style.fontWeight = "bold"

                    productInfo.appendChild(productImage);
                    productInfo.appendChild(productName);

                    var productDetails = document.createElement("div");
                    productDetails.className = "product-details";
                    var countElement = document.createElement("p");
                    countElement.textContent = "Count: " + product.count;
                    countElement.style.color = "#4168e3"
                    countElement.style.fontWeight = "bold"
                    var sizeElement = document.createElement("p");
                    sizeElement.textContent = "Size: " + product.size;
                    sizeElement.style.color = "#4168e3"
                    sizeElement.style.fontWeight = "bold"
                    var priceElement = document.createElement("p");
                    priceElement.textContent = "Price: " + product.price * product.count + ".00$";
                    priceElement.style.fontWeight = "bold"
                    priceElement.style.color = "#60a960"

                    productDetails.appendChild(countElement);
                    productDetails.appendChild(sizeElement);
                    productDetails.appendChild(priceElement);

                    productRow.appendChild(productInfo);
                    productRow.appendChild(productDetails);

                    orderBlock.appendChild(productRow);
                });

                // Создаем элемент для общей информации по заказу
                var orderTotal = document.createElement("div");
                orderTotal.className = "order-total";
                var subtotalElement = document.createElement("p");
                subtotalElement.textContent = "Subtotal: " + order.subtotal + ".00$";
                var discountElement = document.createElement("p");
                discountElement.textContent = "Discount: -" + order.discount + "%";
                discountElement.style.color = "red"
                orderTotal.appendChild(subtotalElement);
                orderTotal.appendChild(discountElement);

                orderBlock.appendChild(orderTotal);

                // Добавляем заказ в контейнер
                ordersContainer.appendChild(orderBlock);
            });
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
});