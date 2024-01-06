$("#gui").click(function () {
    quenmatkhau();
})

function quenmatkhau() {
    var email = $('.email').val();

    if (email == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }
    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!email.match(validRegex)) {
        alert("Email không hợp lệ");
        return null;
    }

    var infoAcc = {
        "email": email
    }

    var jsonString = JSON.stringify(infoAcc);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        url: "http://localhost:8080/forgetPassword",
        method: "POST",
        data: jsonString,
        success: function (response) {
            console.log(response);
            if (response.httpStatus == 'OK') {
                var data = response.data;
                alert("Vui lòng kiểm tra email");
                window.location.href = "/dangnhap";
            } else {
                alert(response.message);
            }
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}