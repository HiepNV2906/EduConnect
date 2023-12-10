
$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAccountAdmin($.cookie('id'), createDataHTML);
});

$('#nutcapnhat').click(function () {
    handleUpdate();
})

$('#nutquaylai').click(function () {
    getAccountAdmin($.cookie('id'), createDataHTML);
})

function createDataHTML(data) {
    var srcAvata = data.avata ? 'http://localhost:8080/uploads/images/' + data.avata : '../../img/avatadefault.png';
    var avata = `<img src="${srcAvata}" class="form-avata" alt="">`
    var hoten = `<p class="form-control-static">${data.hoten}</p>`
    var email = `<p class="form-control-static">${data.email}</p>`
    var gioitinh = `<p class="form-control-static">${data.gioitinh}</p>`
    var ngaysinh = `<p class="form-control-static">${moment(data.ngaysinh).format("DD-MM-YYYY")}</p>`
    var sdt = `<input type="text" id="text-input" name="text-input" value="${data.sdt}" class="form-control">`

    $(".avata").html(avata);
    $(".hoten").html(hoten);
    $(".emailacc").html(email);
    $(".gioitinh").html(gioitinh);
    $(".ngaysinh").html(ngaysinh);
    $(".sdt").html(sdt);
}

function handleUpdate() {
    if (!validateInfo()) {
        return;
    }
    var id = $.cookie('id');

    var sdt, avata;
    [sdt, avata] = validateInfo();

    var ngaysinh = $('.ngaysinh p').text();
    var formattedDate = convertDateYYYYMMDD(ngaysinh);

    var infoGS = {
        'id': id,
        'hoten': $('.hoten p').text(),
        'sdt': sdt,
        'email': $('.email p').text(),
        'gioitinh': $('.gioitinh p').text(),
        'ngaysinh': formattedDate
    }

    console.log(infoGS);
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    if (avata) {
        formData.append('avata', avata);
    }

    updateAccountAdmin(id, formData);
}

function validateInfo() {
    var sdt = $(".sdt input").val();
    var avata = $(".ipavata").prop('files')[0];

    if (sdt == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }

    return [sdt, avata];
}