
$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getAccountStudent(id, createDataHTML);
});

$('#nutcapnhat').click(function () {
    handleUpdate();
})

$('#nutxoa').click(function () {
    var id = $('.maId p').text();
    deleteAStudent(id, handleDelete);
})

$('#nutquaylai').click(function () {
    var id = getParamFromURL('id');
    getAccountStudent(id, createDataHTML);
})

function setQuanHuyen(value) {
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen select").html(quanhuyenHTML);
    $(".quanhuyen select").val(value);
}

function handleDelete() {
    window.location.href = "E_listStudent.html";
}

function createDataHTML(data) {
    $(".maId").html(`<p class="form-control-static">${data.id}</p>`);
    $(".avata img").attr('src', data.avata ? 'http://localhost:8080/uploads/images/' + data.avata :
        '../../img/avatadefault.png');
    $(".cccd img").attr('src', 'http://localhost:8080/uploads/images/' + data.cccd);
    $(".hoten input").val(data.hoten);
    $(".emailacc input").val(data.email);
    $(".gioitinh select").val(data.gioitinh);
    $(".ngaysinh input").val(moment(data.ngaysinh).format('YYYY-MM-DD'));
    $(".sdt input").val(data.sdt);
    $(".diachi input").val(data.diachi);
    $(".trangthai select").val(data.trangthai);
    setQuanHuyen(data.quan);
}

function handleUpdate() {
    if (!validateInfo()) {
        return;
    }
    var hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi, cccd;
    [hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi, cccd] = validateInfo();

    var id = $('.maId p').text();
    var trangthai = $('.trangthai select').val();

    var infoGS = {
        'id': id,
        'hoten': hoten,
        'sdt': sdt,
        'email': email,
        'gioitinh': gioitinh,
        'ngaysinh': ngaysinh,
        'quan': quan,
        'diachi': diachi,
        'trangthai': trangthai
    }
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    if (avata) {
        formData.append('avata', avata);
    }

    updateAccountStudent(id, formData);
}

function validateInfo() {
    var hoten = $(".hoten input").val();
    var sdt = $(".sdt input").val();
    var email = $(".emailacc input").val();
    var avata = $(".ipavata").prop('files')[0];
    var gioitinh = $(".gioitinh select").val();
    var ngaysinh = $(".ngaysinh input").val();
    var quan = $(".quanhuyen select").val();
    var diachi = $(".diachi input").val();
    var cccd = $(".ipcccd").prop('files')[0];

    if (hoten == '' || sdt == '' || email == '' || gioitinh == '' || ngaysinh == '' ||
        diachi == '' || quan == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }
    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!email.match(validRegex)) {
        alert("Email không hợp lệ");
        return null;
    }
    return [hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi, cccd];
}