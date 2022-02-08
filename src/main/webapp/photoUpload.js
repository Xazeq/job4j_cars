function upd() {
    $("form[name='uploader']").submit(function (e) {
        let param = window.location.search.replace( '?', '');
        let id = param.split('=')[1];
        let formData = new FormData($(this)[0]);
        $.ajax({
            url: 'http://localhost:8080/cars/upload.do?id=' + id,
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
                window.location.href = 'http://localhost:8080/cars/index.html';
            },
            error: function(msg) {
                alert('Ошибка!');
            }
        });
        e.preventDefault();
    });
}