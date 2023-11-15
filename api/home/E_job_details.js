

$(document).ready(function () {
    var idLop = getParamFromURL('id');
    getInfoDetailsClass(idLop);
    if ($.cookie('role') != "GIASU") {
        $('#nutungtuyen').hide();
    }
});

$('#nutungtuyen').click(function () {
    if ($.cookie('role') == "GIASU") {
        ungtuyen();
    } else {
        alert("Đăng ký làm gia sư để ứng tuyển nhận lớp");
    }
});

function ungtuyen() {
    var token = $.cookie('token');

    var infoAcc = {
        "giasuid": $.cookie('id'),
        "lopid": getParamFromURL('id')
    }

    var jsonString = JSON.stringify(infoAcc);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "POST",
        url: "http://localhost:8080/api/ungtuyen",
        data: jsonString,
        success: function (response) {
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

function getInfoDetailsClass(id) {
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/lop/" + id,
        success: function (response) {
            console.log(response);
            var data = response.data;
            var coban = `<div class="thumb">
                            <img src="${data.chude.anh}" alt="">
                        </div>
                        <div class="jobs_conetent">
                            <a href="#">
                                <h4>${data.tieude}</h4>
                            </a>
                            <div class="links_locat">
                                <div class="location">
                                    <p> <i class="fa fa-map-marker"></i> ${data.diachi}, ${data.quan}</p>
                                </div>
                                <div class="location">
                                    <p> <i class="fa fa-leanpub"></i> ${data.chude.tenmonhoc} ${data.chude.trinhdo}</p>
                                </div>
                                <div class="location">
                                    <p> <i class="fa fa-money"></i> Học phí: ${data.hocphi} VND/buổi</p>
                                </div>
                                <div class="location">
                                    <p> <i class="fa fa-money"></i> Phí nhận lớp: ${data.phiungtuyen}</p>
                                </div>
                            </div>
                        </div>`
            var motahs = `<h4>Thông tin học sinh</h4>
                        <p>${data.motahs}</p>`
            var yeucaugs = `<li>Trình độ: ${data.nghenghiepgs}</li>
                            <li>Giới tính: ${data.gioitinhgs}</li>
                            <li>Trường: ${data.truonggs}</li>
                            <li>Yêu cầu khác: ${data.yeucaukhac}</li>`
            var block_calender = ``
            var lich = data.dslichtrong
            for (let i = 0; i < lich.length; i++) {
                block_calender += `<div class="row day-in-week" ng-repeat="day in days">
                                        <p class="ng-binding col-lg-2">${lich[i].thu == '8' ? 'CN:' : 'Thứ ' + lich[i].thu} </p>
                                        <div class="col-lg-10">
                                            <div class="row time-in-day">
                                                <span class="genric-btn primary-border ng-view col-md-3 ${lich[i].sang == '1' ? 'calendar-active' : ''}">
                                                    Sáng</span>
                                                <span class="genric-btn primary-border ng-view col-md-3 ${lich[i].chieu == '1' ? 'calendar-active' : ''}">Chiều
                                                </span>
                                                <span class="genric-btn primary-border ng-view col-md-3 ${lich[i].toi == '1' ? 'calendar-active' : ''}">Tối
                                                </span>
                                            </div>
                                        </div>
                                    </div>`
            }
            var tomtatlop = `<li>Ngày tạo: <span>${moment(data.ngaytao).format('DD-MM-YYYY')}</span></li>
                            <li>Số buổi/tuần: <span>${data.sobuoi}</span></li>
                            <li>Số giờ/buổi: <span>${data.sogio}</span></li>
                            <li>Số học sinh: <span>${data.sohs}</span></li>
                            <li>Giới tính: <span>${data.gioitinhhs}</span></li>
                            <li>Hình thức: <span>${data.hinhthuc}</span></li>`
            var role = $.cookie('role');
            if (role == "GIASU") {
                var idgs = $.cookie('id');
                var dsungtuyen = data.dsungtuyen;
                var isApply = false;
                for (let i = 0; i < dsungtuyen.length; i++) {
                    if (idgs == dsungtuyen[i].giasuid) {
                        $('#nutungtuyen').hide();
                        isApply = true;
                        createStatusHTML(dsungtuyen[i].trangthaiungtuyen);
                        break;
                    }
                }
                if (!isApply) {
                    $(".ungtuyen").html(`<button id="nutungtuyen" class="boxed-btn3 w-100">Ứng tuyển</button>`);
                }
            }
            $(".coban").html(coban);
            $(".motahs").html(motahs);
            $(".yeucaugs").html(yeucaugs);
            $(".block-calender").html(block_calender);
            $(".tomtatlop").html(tomtatlop);
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

function createStatusHTML(status) {
    var html = ``;
    if (status == 'CHO') {
        html += `<div class="text-warning">
                    <i class="fa fa-hourglass-half" aria-hidden="true"></i>
                    <span>Đang chờ kết quả</span>
                </div>`
    } else if (status == "THANHCONG") {
        html += `<div class="text-success">
                    <i class="fa fa-check-square-o" aria-hidden="true"></i>
                    <span>Ứng tuyển thành công</span>
                </div>`
    } else {
        html += `<div class="text-danger">
                    <i class="fa fa-times" aria-hidden="true"></i>
                    <span>Ứng tuyển thất bại</span>
                </div>`
    }
    $(".ketquaungtuyen").html(html);
}