function filterCheck(page) {

    var checkboxesBrands = document.querySelectorAll('#brands-form input[type="checkbox"]');
    var checkboxesGender = document.querySelectorAll('#gender-root-answer input[type="checkbox"]');
    var checkboxesColor = document.querySelectorAll('#color-form input[type="checkbox"]');
    var startPrice = document.getElementById("lower-value").textContent;
    var endPrice = document.getElementById("upper-value").textContent;
    var sorted = document.getElementById("sorted").value;
    var select = document.getElementById("mySelect");
    var selectedValue = select.options[select.selectedIndex].value;
    var checkedCheckboxesBrand = [];
    var checkedCheckboxesGender = [];
    var checkedCheckboxesColor = [];
    checkboxesBrands.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCheckboxesBrand.push(checkbox.name);
        }
    });

    checkboxesGender.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCheckboxesGender.push(checkbox.name);
        }
    });

    checkboxesColor.forEach(function (checkbox) {
        if (checkbox.checked) {
            checkedCheckboxesColor.push(checkbox.name);
        }
    });
    const queryString = window.location.search;
    const endPoint = window.location.pathname;
    const urlParams = new URLSearchParams(queryString);
    const product = urlParams.get('page');

    const pageRootForm = document.getElementById('page-root-form');
    if (page != null) {
        pageRootForm.value = page.textContent;
    } else if ((endPoint === '/shop' && urlParams.keys().next().done) || page == null) {
        pageRootForm.value = 1;
    } else if (product != null && page == null) {
        pageRootForm.value = product;
    }

    document.getElementById('gender-root-form').value = checkedCheckboxesGender
    document.getElementById('category-root-form').value = checkedCheckboxesBrand
    document.getElementById('color-root-form').value = checkedCheckboxesColor
    document.getElementById('sorted-root-form').value = sorted
    document.getElementById('size-root-form').value = selectedValue
    document.getElementById('start-price-root-form').value = startPrice
    document.getElementById('end-price-root-form').value = endPrice

    document.getElementById('shop-root-form').submit()
}


