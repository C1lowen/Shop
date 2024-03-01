function deleteProduct(button){
    var productId = button.getAttribute('data-product-id');
    fetch('/deletefavorite/' + productId, {
        method: 'POST'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Ошибка при отправке данных на сервер');
            }
            return response.json();
        })
        .then(data => {
            if(!data.auth){
                const storedDataString = localStorage.getItem('favorite');
                const favoriteArray = storedDataString ? JSON.parse(storedDataString) : [];
                const updatedFavoriteArray = favoriteArray.filter(
                    (favorite) => favorite.productId !== Number(productId)
                );
                if(updatedFavoriteArray.length === 0) {
                    localStorage.setItem("favorite", "")
                }else {
                    localStorage.setItem("favorite", JSON.stringify(updatedFavoriteArray));
                }
            }
            location.reload();
        })
        .catch(error => {
            console.error(error);
        });
}