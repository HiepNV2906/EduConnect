

$("#nutdangnhap").click(function () {
    console.log("***");
    login();
});

function login() {
    var email = $('.email').val();
    var password = $('.password').val();

    if (email == '' || password == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }
    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!email.match(validRegex)) {
        alert("Email không hợp lệ");
        return null;
    }

    var infoAcc = {
        "username": email,
        "password": password
    }

    var jsonString = JSON.stringify(infoAcc);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: "http://localhost:8080/login",
        method: "POST",
        data: jsonString,
        success: function (response) {
            console.log(response);
            if (response.httpStatus == 'OK') {
                var data = response.data;
                saveCookies(data);
                alert(response.message);
                window.location.href = "/";
            } else {
                alert(response.message);
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}