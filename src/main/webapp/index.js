function getAds() {
    let check = document.getElementById('checkbox');
    checkUser();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/index.do',
        dataType: 'json',
        data: {
            checkbox: check.checked
        }
    }).done(function (data) {
        addAdsToTable(data);
    }).fail(function (err) {
        console.log(err);
    });
}

function clearStorage() {
    window.localStorage.removeItem('user');
}

function getBrands() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/brand.do',
        dataType: 'json'
    }).done(function (data) {
        for (let i = 0; i < data.length; i++) {
            let brand = data[i];
            $('#brand').append($('<option>', {
                value: brand.id,
                text: brand.name
            }));
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function checkUser() {
    let username = window.localStorage.getItem('user');
    if (username != null) {
        document.getElementById('login').innerText = '';
        document.getElementById('exit').innerText = username + ' | Выход';
        document.getElementById('addAd').innerText = 'Добавить объявление';
        document.getElementById('myAds').innerText = 'Мои объявления';
    }
}

function addAdsToTable(data) {
    $('#tableBodyId tr').remove();
    for (let i = 0; i < data.length; i++) {
        let ad = data[i];
        let id = ad['id'];
        let desc = ad['description'];
        let car = ad['car'];
        let model = car['model'];
        let year = car['year'];
        let color = car['color'];
        let mileage = car['mileage'];
        let brand = car['brand'];
        let brandName = brand['name'];
        let body = car['body'];
        let bodyName = body['name'];
        let price = ad['price'];
        let imgSrc = 'http://localhost:8080/cars/download.do?id='+id;
        $('#table tbody').append('<tr><td><img src=' + imgSrc + ' width="150px" height="150px"/></td>'
            + '<td style="text-align: center">' + brandName + '</td>'
            + '<td style="text-align: center">' + model + '</td>'
            + '<td style="text-align: center">' + bodyName + '</td>'
            + '<td style="text-align: center">' + year + '</td>'
            + '<td style="text-align: center">' + color + '</td>'
            + '<td style="text-align: center">' + mileage + '</td>'
            + '<td style="text-align: center">' + price + '</td>'
            + '<td style="text-align: center">' + desc + '</td>'
            + '</tr>');
    }
}

function filterByBrand() {
    let brandId = $('#brand').val();
    checkUser();
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/brandFilter.do',
        dataType: 'json',
        data: {
            brandId : brandId
        }
    }).done(function (data) {
        addAdsToTable(data);
    }).fail(function (err) {
        console.log(err);
    });
}

function getTodayAds() {
    checkUser();
    let check = document.getElementById('checkboxToday');
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/todayAds.do',
        dataType: 'json',
        data: {
            checkbox: check.checked
        }
    }).done(function (data) {
        addAdsToTable(data);
    }).fail(function (err) {
        console.log(err);
    });
}

function clearFilter() {
    $('input[type=checkbox]').each(function (){
        this.checked = false;
    });
    location.reload();
}