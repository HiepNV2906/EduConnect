

$(document).ready(function () {
    var idLop = getParamFromURL('id');
    getInfoDetailsTutor(idLop);
    if ($.cookie('role') != "HOCVIEN") {
        $('.moiungtuyen').hide();
    } else {
        getMyClass("DANGTIM");
    }
});

$('#nutmoi').click(function () {
    if ($.cookie('role') == "HOCVIEN") {
        moi();
    } else {
        alert("Đăng ký làm gia sư để ứng tuyển nhận lớp");
    }
});

function moi() {
    var token = $.cookie('token');
    var lopid = $("#myclass").val();
    if (lopid == '') {
        alert('Vui lòng chọn lớp muốn mời');
        return;
    }

    var infoAcc = {
        "giasuid": getParamFromURL('id'),
        "lopid": lopid
    }

    var jsonString = JSON.stringify(infoAcc);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "POST",
        url: "http://localhost:8080/api/loimoi",
        data: jsonString,
        success: function (response) {
            var data = response.data;
            console.log(response);
            alert(response.message);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getMyClass(status) {
    var hocvienid = $.cookie('id');
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/hocvien?hocvienid=" + hocvienid + "&status=" + status,
        success: function (response) {
            var data = response.data;
            var myclass = `<option value="" data-display="Lớp của tôi">Lớp của tôi</option>`;
            for (let i = 0; i < data.length; i++) {
                myclass += `<option value="${data[i].id}">ID: ${data[i].id} - ${data[i].tieude}</option>`;
            }
            $("#myclass").html(myclass);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function getInfoDetailsTutor(id) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/giasu/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            var avata = `<img src="http://localhost:8080/uploads/images/${data.avata}" alt="" class="img-responsive">`
            var thongtin = `<h2>${data.hoten}<span>${data.nghenghiep}</span></h2>
                            <p>${data.truong}</p>
                            <ul class="information">
                                <li><span>Họ và tên:</span>${data.hoten}</li>
                                <li><span>Giới tính:</span>${data.gioitinh}</li>
                                <li><span>Ngày sinh:</span>${moment(data.ngaysinh).format('DD-MM-YYYY')}</li>
                                <li><span>Quê quán:</span>${data.quequan}</li>
                                <li><span>Địa chỉ:</span>${data.diachi}, ${data.quan}</li>
                                <li><span>Số điện thoại:</span>${data.sdt}</li>
                                <li><span>Khu vực dạy:</span>${data.khuvucday}</li>
                            </ul>`
            var gioithieu = `<p>${data.gioithieu}</p>`
            var dscd = data.dschude;
            var chude = ``
            for (let i = 0; i < dscd.length; i++) {
                chude += `<span class="service-tag">${dscd[i].tenmonhoc} ${dscd[i].trinhdo}</span>`
            }
            var kinhnghiem = `<p>${data.kinhnghiem}</p>`
            var thanhtich = `<p>${data.thanhtich}</p>`
            $(".avata").html(avata);
            $(".thongtin").html(thongtin);
            $(".gioithieu").html(gioithieu);
            $(".chude").html(chude);
            $(".kinhnghiem").html(kinhnghiem);
            $(".thanhtich").html(thanhtich);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}