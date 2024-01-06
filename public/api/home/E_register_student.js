

$(document).ready(function () {
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen").html(quanhuyenHTML);

});

$("#nutdangky").click(function () {
    dangKyHocVien();
})

function dangKyHocVien() {
    if (!validateInfo()) {
        return;
    }
    var hoten, sdt, email, password, avata, gioitinh, ngaysinh, quan, diachi, cccd;
    [hoten, sdt, email, password, avata, gioitinh, ngaysinh, quan, diachi, cccd] = validateInfo();

    var infoGS = {
        'hoten': hoten,
        'sdt': sdt,
        'email': email,
        'password': password,
        'gioitinh': gioitinh,
        'ngaysinh': ngaysinh,
        'quan': quan,
        'diachi': diachi
    }
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    formData.append('avata', avata);
    formData.append('cccd', cccd);

    $.ajax({
        url: "http://localhost:8080/dangkyhocvien",
        type: "POST",
        data: formData,
        enctype: 'multipart/form-data',
        contentType: false,
        processData: false,
        success: function (response) {
            console.log(response);
            alert(response.message);
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function validateInfo() {
    var hoten = $(".hoten").val();
    var sdt = $(".sdt").val();
    var email = $(".email").val();
    var password = $(".password").val();
    var repassword = $(".repassword").val();
    var avata = $(".ipavata").prop('files')[0];
    var gioitinh = $(".gioitinh").val();
    var ngaysinh = $(".ngaysinh").val();
    var quan = $(".quanhuyen").val();
    var diachi = $(".diachi").val();
    var cccd = $(".cccd").prop('files')[0];

    if (hoten == '' || sdt == '' || email == '' || password == '' ||
        repassword == '' || gioitinh == '' || ngaysinh == '' || diachi == '' ||
        quan == '' || !avata || !cccd) {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }
    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!email.match(validRegex)) {
        alert("Email không hợp lệ");
        return null;
    }
    if (password.length < 6) {
        alert("Mật khẩu tối thiểu 6 ký tự");
        return null;
    }
    if (password != repassword) {
        alert("Nhập lại mật khẩu không trùng khớp");
        return null;
    }
    return [hoten, sdt, email, password, avata, gioitinh, ngaysinh, quan, diachi, cccd];
}