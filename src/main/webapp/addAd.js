function getBodies() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/cars/body.do',
        dataType: 'json'
    }).done(function (data) {
        for (let i = 0; i < data.length; i++) {
            let body = data[i];
            $('#body').append($('<option>', {
                value: body.id,
                text: body.name
            }));
        }
    }).fail(function (err) {
        console.log(err);
    });
}

function validate() {
    if ($('#model').val() === '') {
        alert($('#model').attr('title'));
        return false;
    }
    if ($('#year').val() === '') {
        alert($('#year').attr('title'));
        return false;
    }
    if ($('#color').val() === '') {
        alert($('#color').attr('title'));
        return false;
    }
    if ($('#mileage').val() === '') {
        alert($('#mileage').attr('title'));
        return false;
    }
    if ($('#desc').val() === '') {
        alert($('#desc').attr('title'));
        return false;
    }
    if ($('#price').val() === '') {
        alert($('#price').attr('title'));
        return false;
    }
    return true;
}

function addAd() {
    if (!validate()) {
        return false;
    }
    let username = window.localStorage.getItem('user');
    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/cars/addAd.do',
        data: {
            model : $('#model').val(),
            year : $('#year').val(),
            color : $('#color').val(),
            mileage : $('#mileage').val(),
            desc : $('#desc').val(),
            price : $('#price').val(),
            brand : $('#brand').val(),
            body : $('#body').val(),
            username : username,
        }
    }).done(function () {
        window.location.href = "http://localhost:8080/cars/myAds.html";
    }).fail(function (err) {
        console.log(err);
    });
}