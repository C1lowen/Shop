document.addEventListener('DOMContentLoaded', function() {
    var container = document.querySelector('.root-size-products');
    let sizeRange = document.getElementById('size-range').value

    var rangeArray = [];

    var rangeParts = sizeRange.split('-');


    var start = parseInt(rangeParts[0]);
    var end = parseInt(rangeParts[1]);


    if (!isNaN(start) && !isNaN(end)) {
        for (var i = start; i <= end; i++) {
            rangeArray.push(i.toString());
        }
    }

    rangeArray.forEach(function(size) {
        var input = document.createElement('input');
        input.type = 'radio';
        input.id = 'size' + size;
        input.className = 'size-input';
        input.name = 'size';

        var label = document.createElement('label');
        label.htmlFor = 'size' + size;
        label.className = 'size-label';
        label.textContent = size;

        container.appendChild(input);
        container.appendChild(label);
    });
});