
$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAccountStudent($.cookie('id'), createDataHTML);
});

$('#nutcapnhat').click(function () {
    updateInfoStudent();
})

$('#nutquaylai').click(function () {
    getAccountStudent($.cookie('id'), createDataHTML);
})

function setQuanHuyen(value) {
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen").html(quanhuyenHTML);
    $(".quanhuyen").val(value);
}

function createDataHTML(data) {
    var avata = `<img src="http://localhost:8080/uploads/images/${data.avata}" class="form-avata" alt="">`
    var cccd = `<img src="http://localhost:8080/uploads/images/${data.cccd}" class="form-cccd" alt="">`
    var hoten = `<p class="form-control-static">${data.hoten}</p>`
    var email = `<p class="form-control-static">${data.email}</p>`
    var gioitinh = `<p class="form-control-static">${data.gioitinh}</p>`
    var ngaysinh = `<p class="form-control-static">${moment(data.ngaysinh).format('DD-MM-YYYY')}</p>`
    var sdt = `<input type="text" id="text-input" name="text-input" value="${data.sdt}" class="form-control">`
    var diachi = `<input type="text" id="text-input" name="text-input" value="${data.diachi}" class="form-control">`
    var trangthai
    if (data.trangthai == 'CHUAPHEDUYET') {
        trangthai = `<p class="form-control-static choxacthuc">Chưa xác thực</p>`
    } else if (data.trangthai == 'DAPHEDUYET') {
        trangthai = `<p class="form-control-static xacthuc">Đã xác thực</p>`
    } else {
        trangthai = `<p class="form-control-static dinhchi">Đình chỉ</p>`
    }
    $(".avata").html(avata);
    $(".cccd").html(cccd);
    $(".hoten").html(hoten);
    $(".emailacc").html(email);
    $(".gioitinh").html(gioitinh);
    $(".ngaysinh").html(ngaysinh);
    $(".sdt").html(sdt);
    $(".diachi").html(diachi);
    $(".trangthai").html(trangthai);
    setQuanHuyen(data.quan);
}

function updateInfoStudent() {
    if (!validateInfo()) {
        return;
    }
    var sdt, avata, quan, diachi;
    [sdt, avata, quan, diachi] = validateInfo();

    var ngaysinh = $('.ngaysinh p').text();
    var formattedDate = convertDateYYYYMMDD(ngaysinh);

    var id = $.cookie('id');

    var infoGS = {
        'id': id,
        'hoten': $('.hoten p').text(),
        'sdt': sdt,
        'email': $('.emailacc p').text(),
        'gioitinh': $('.gioitinh p').text(),
        'ngaysinh': formattedDate,
        'quan': quan,
        'diachi': diachi
    }
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    if (avata) {
        formData.append('avata', avata);
    }

    updateAccountStudent(id, formData)
}

function validateInfo() {
    var sdt = $(".sdt input").val();
    var avata = $(".ipavata").prop('files')[0];
    var quan = $(".quanhuyen").val();
    var diachi = $(".diachi input").val();

    if (sdt == '' || diachi == '' || quan == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }

    return [sdt, avata, quan, diachi];
}