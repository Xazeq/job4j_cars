function getMyAds() {
    let username = window.localStorage.getItem('user');
    if (username != null) {
        document.getElementById('exit').innerText = username + ' | Выход';
    }
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/userAds.do',
        dataType: 'json',
        data: {
        }
    }).done(function (data) {
        $('#tableBodyId tr').remove();
        for (let i = 0; i < data.length; i++) {
            let ad = data[i];
            let id = ad['id'];
            let car = ad['car'];
            let model = car['model'];
            let brand = car['brand'];
            let brandName = brand['name'];
            let body = car['body'];
            let bodyName = body['name'];
            let isActive = ad['isActive'];
            let price = ad['price'];
            let imgSrc = 'http://localhost:8080/cars/download.do?id='+id;
            let button;
            if (isActive === true) {
                button = '<button type="button" class="btn btn-primary" onClick="closeAd('+id+')">Снять с продажи</button>';
            } else {
                button = '';
            }
            $('#table tbody').append('<tr><td><img src=' + imgSrc + ' width="150px" height="150px"/></td>'
                + '<td style="text-align: center">' + brandName + '</td>'
                + '<td style="text-align: center">' + model + '</td>'
                + '<td style="text-align: center">' + bodyName + '</td>'
                + '<td style="text-align: center">' + price + '</td>'
                + '<td style="text-align: center"><a href="photoUpload.html?id=' + id + '">'
                + '<i class="fa fa-plus-square-o mr-3 fa-2x"></i>'
                +  '</a></td>'
                + '<td>' + button + '</td>'
                + '</tr>');
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function closeAd(id) {
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/closeAd.do',
        data: {
            id : id
        }
    }).done(function () {
        getMyAds();
    }).fail(function (err) {
        console.log(err);
    });
}