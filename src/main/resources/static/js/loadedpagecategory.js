document.addEventListener('DOMContentLoaded', function () {

    fetch('/retrieve/user/products', {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(user => {
            if(user.auth) {
                let arrayIdProducts = user.products
                let arrayIdLikes = user.likes
                arrayIdProducts.forEach(number => {
                    setAddButton(number,'fa fa-trash')
                })
                arrayIdLikes.forEach(number => {
                    setAddButton(number+ '-heart', 'fa-regular fa-heart')
                })

            }else {
                const storedDataString = localStorage.getItem('favorite');
                const favoriteArray = storedDataString ? JSON.parse(storedDataString) : [];
                favoriteArray.forEach(item => {
                    setAddButton(item.productId, 'fa fa-trash')
                })
            }
        })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });

})


function setAddButton(number, className) {
    let buttonElem = document.getElementById(number)
    if(buttonElem != null) {
        changeButton(buttonElem, className)
        buttonElem.classList.add('added');

    }
}