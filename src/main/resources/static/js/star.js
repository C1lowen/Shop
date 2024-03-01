function starCheck(count) {
    // Элемент ul, в который будут добавлены звезды
    const starList = document.getElementById('star-list');
    starList.innerHTML = '';
    // Генерация звезд с помощью цикла
    for (let i = 1; i <= count; i++) {
        const li = document.createElement('li');
        const a = document.createElement('a');
        const iElement = document.createElement('i');

        iElement.classList.add('fa', 'fa-star');
        a.appendChild(iElement);
        li.appendChild(a);


        li.onclick = function () {
            starCheck(i);
        };


        starList.appendChild(li);
    }

    for (let k = count; k < 5; k++) {
        const liR = document.createElement('li');
        const aR = document.createElement('a');
        const iElementR = document.createElement('i');

        iElementR.classList.add('fa', 'fa-star', 'text-primary');
        aR.appendChild(iElementR);
        liR.appendChild(aR);

        // Добавление обработчика события при клике на звезду
        liR.onclick = function () {
            starCheck(k+1);
        };

        // Добавление элемента li в список звезд
        starList.appendChild(liR);
    }
}