document.addEventListener('DOMContentLoaded', function () {
    let countProductCart = document.getElementById('nav-shop')
    let countLikesCart = document.getElementById('nav-likes')
    fetch('/page/count', {
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
               countProductCart.textContent = user.countPage
                countLikesCart.textContent = user.countLikes
            }else {
                const storedDataString = localStorage.getItem('favorite');
                const favoriteArray = storedDataString ? JSON.parse(storedDataString) : [];
                countProductCart.textContent = favoriteArray.length === 0 ? '' : favoriteArray.length
            }
        })
        .catch(error => {
            console.error(error);  // Обработка ошибок
        });

})