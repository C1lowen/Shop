function reviewSave() {

    let formReview = document.getElementById('review-form-save')
    let usernameElement = formReview.elements.name;
    let emailElement = formReview.elements.email;
    let usernameValue = usernameElement ? String(usernameElement.value) : ''
    let emailValue = emailElement ? String(emailElement.value) : '';
    let subjectValue = String(formReview.elements.subject.value)
    let textareaValue = String(formReview.elements.textarea.value)
    const starElements = document.querySelectorAll('#star-list .text-primary');
    let stars = 5 - starElements.length;
    let idProduct = document.getElementById('id-product');
    let objectReview = {
        name: usernameValue,
        email: emailValue,
        subject: subjectValue,
        message: textareaValue,
        stars: stars,
        idProduct: idProduct.value
    }

    fetch('/review', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(objectReview)
    })
        .then(response => response.json())
        .then(data => {
           if(data.success) {
               let reviewListContainer = document.getElementById("reviewList");
               let averageStars = document.getElementById('average-stars')
               let countReview = document.getElementById('count-review')
               document.getElementById('valid-text-message').textContent = ""
               let pElem = document.getElementById('style-see-all');
               if(pElem == null) {
                   pElem = document.createElement("p");
                   pElem.id = 'style-see-all'
                   pElem.className = 'style-see-all'
               }
               generateReview(data.answer, true)
               if (data.size > 3) {
                   pElem.textContent = "See all(" + (data.size - 3) + ")";
                   pElem.removeAttribute('onclick');
                   pElem.setAttribute('onclick', 'seeAllButton()');
                   reviewListContainer.appendChild(pElem);
               }
               averageStars.textContent = (data.average % 1 === 0) ? data.average + '.0' : data.average
               countReview.textContent='('+ data.size + ' Reviews)'
               const statistics = data.statistics
               document.getElementById('five-stars').textContent = ' '+statistics.five
               document.getElementById('four-stars').textContent = ' '+statistics.four
               document.getElementById('three-stars').textContent = ' '+statistics.three
               document.getElementById('two-stars').textContent = ' '+statistics.two
               document.getElementById('one-stars').textContent = ' '+statistics.one
           }else {
                document.getElementById('valid-text-message').textContent = "Что-то пошло не так!"
           }

        })
        .catch(error => {
            console.error('Ошибка:', error);
});


}

function generateReview(data,flag) {
    var reviewListContainer = document.getElementById("reviewList");
    if(flag) reviewListContainer.innerHTML='';

   data.forEach(function (review) {
        var reviewItem = document.createElement("div");
        reviewItem.className = "review_item";

        var mediaContainer = document.createElement("div");
        mediaContainer.className = "media";

        var mediaBody = document.createElement("div");
        mediaBody.className = "media-body";
        var nameHeading = document.createElement("h4");
        nameHeading.textContent = review.name;

        var starContainer = document.createElement("div");
        for (var i = 0; i < review.stars; i++) {
            var starIcon = document.createElement("i");
            starIcon.className = "fa fa-star";
            starContainer.appendChild(starIcon);
        }

        mediaBody.appendChild(nameHeading);
        mediaBody.appendChild(starContainer);

        mediaContainer.appendChild(mediaBody);
        reviewItem.appendChild(mediaContainer);

        var textParagraph = document.createElement("p");
        textParagraph.textContent = review.message;
        reviewItem.appendChild(textParagraph);

        reviewListContainer.appendChild(reviewItem);
    });
   // if(flag) {
   //     let pElem = document.createElement("p");
   //     pElem.id = 'style-see-all'
   //     pElem.className = 'style-see-all'
   //     reviewListContainer.appendChild(pElem)
   // }
}

function seeAllButton() {
    let idProduct = document.getElementById('id-product');
    fetch('/fullreview', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: Number(idProduct.value)
        })
    })
        .then(response => response.json())
        .then(data => {
            let pElem = document.getElementById('style-see-all');
            var reviewListContainer = document.getElementById("reviewList");
            generateReview(data, false);
            pElem.textContent = "collapse";
            pElem.removeAttribute('onclick');
            pElem.setAttribute('onclick', 'seeAllQuery(' + JSON.stringify(data) + ')');
            reviewListContainer.appendChild(pElem);
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}

function seeAllQuery(allReview) {
    let idProduct = document.getElementById('id-product');
    fetch('/fistreview', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: Number(idProduct.value)
        })
    })
        .then(response => response.json())
        .then(data => {
            let pElem = document.getElementById('style-see-all');
            var reviewListContainer = document.getElementById("reviewList");
            generateReview(data, true);
            pElem.textContent = "See all(" + (allReview.length) + ")";
            pElem.removeAttribute('onclick');
            pElem.setAttribute('onclick', 'seeAllButton()');
            reviewListContainer.appendChild(pElem);
        })
        .catch(error => {
            console.error('Ошибка:', error);
        });
}