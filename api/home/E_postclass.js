var lich = ["Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "CN"]

$(document).ready(function () {
    addCalender(lich, '.block-calender')
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen").html(quanhuyenHTML);
    getAPIListSubject(() => {
        renderSubject('.mon');
    })
});

$("#nutdangbai").click(function () {
    dangKyGiaSu();
})

$(".mon").change(function () {
    reloadSubjectLevel();
})


function renderSubject(cssSelector) {
    var monhocHTML = createSubjectFilterHTML(listSubjects);
    $(cssSelector).html(monhocHTML);
}

function reloadSubjectLevel() {
    var mon = $(".mon").val();
    var data = createSubjectLevelFilterHTML(listLevelSubjects[mon]);
    $(".trinhdo").html(data);
}

function addCalender(list, classElement) {
    var result = ``;
    list.forEach((value, index) => {
        result += `<div class="row day-in-week" ng-repeat="day in days">
                        <p class="ng-binding col-lg-2">${value}: </p>
                        <div class="col-lg-10">
                            <div class="row time-in-day" id=${index + 2}>
                                <label class="form-check-label col-md-3">
                                    <div class="row">
                                        <div class="col-2">
                                            <input type="checkbox" name="" value="sang" class="form-check-input">
                                        </div>
                                        <span class="col-10">Sáng</span>
                                    </div>
                                </label>
                                <label class="form-check-label col-md-3">
                                    <div class="row">
                                        <div class="col-2">
                                            <input type="checkbox" name="" value="chieu" class="form-check-input">
                                        </div>
                                        <span class="col-10">Chiều</span>
                                    </div>
                                </label>
                                <label class="form-check-label col-md-3">
                                    <div class="row">
                                        <div class="col-2">
                                            <input type="checkbox" name="" value="toi" class="form-check-input">
                                        </div>
                                        <span class="col-10">Tối</span>
                                    </div>
                                </label>
                            </div>
                        </div>
                    </div>`
    })
    $(classElement).html(result);
}

function dangKyGiaSu() {
    if (!validateInfo()) {
        return;
    }
    var tieude, quan, diachi, hocphi, sobuoi, sogio, sohs, gioitinhhs, motahs, hinhthuc,
        chude, hanungtuyen, nghenghiepgs, gioitinhgs, truonggs, yeucaukhac, lichtrong;
    [tieude, quan, diachi, hocphi, sobuoi, sogio, sohs, gioitinhhs, motahs, hinhthuc,
        chude, hanungtuyen, nghenghiepgs, gioitinhgs, truonggs, yeucaukhac, lichtrong] = validateInfo();

    var userid = $.cookie('id');
    var token = $.cookie('token');
    var role = $.cookie('role');

    if (!userid) {
        window.location.href = "E_login.html";
    }
    if (role != "HOCVIEN") {
        alert("Tạo tài khoản học viên để đăng bài tìm lớp");
        window.location.href = "E_register_student.html";
    }

    var infoLop = {
        "tieude": tieude,
        "quan": quan,
        "diachi": diachi,
        "sobuoi": sobuoi,
        "sogio": sogio,
        "hocphi": hocphi,
        "sohs": sohs,
        "gioitinhhs": gioitinhhs,
        "motahs": motahs,
        "nghenghiepgs": nghenghiepgs,
        "gioitinhgs": gioitinhgs,
        "truonggs": truonggs,
        "yeucaukhac": yeucaukhac,
        "hanungtuyen": hanungtuyen,
        "hinhthuc": hinhthuc,
        "dslichtrong": lichtrong,
        "hocvienid": userid,
        "chude": {
            "id": chude
        }
    }
    var jsonString = JSON.stringify(infoLop);

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        url: "http://localhost:8080/api/lop",
        method: "POST",
        data: jsonString,
        success: function (response) {
            console.log(response);
            alert(response.message);
            location.reload();
        },
        error: function (xhr, status, error) {
            alert(error);
        }
    });
}

function validateInfo() {
    var tieude = $(".tieude").val();
    var quan = $(".quanhuyen").val();
    var diachi = $(".diachi").val();
    var hocphi = $(".hocphi").val();
    var sobuoi = $(".sobuoi").val();
    var sogio = $(".sogio").val();
    var sohs = $(".sohs").val();
    var gioitinhhs = $(".gioitinhhs").val();
    var motahs = $(".motahs").val();
    var hinhthuc = $(".hinhthuc").val();
    var chude = $(".trinhdo").val();
    var hanungtuyen = $(".hanungtuyen").val();
    var nghenghiepgs = $(".nghenghiepgs").val();
    var gioitinhgs = $(".gioitinhgs").val();
    var truonggs = $(".truonggs").val();
    var yeucaukhac = $(".yeucaukhac").val();

    if (tieude == '' || quan == '' || diachi == '' || hocphi == '' ||
        sobuoi == '' || sogio == '' || sohs == '' || gioitinhhs == '' ||
        hinhthuc == '' || chude == '' || hanungtuyen == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }

    var lichtrong = [];

    var dsthu = document.querySelectorAll(".time-in-day");
    dsthu.forEach((element) => {
        var day = {
            'thu': element.id
        }
        dsbuoi = element.querySelectorAll("input");
        dsbuoi.forEach((buoi) => {
            day[buoi.value] = buoi.checked ? 1 : 0;
        })
        lichtrong.push(day);
    })

    return [tieude, quan, diachi, hocphi, sobuoi, sogio, sohs, gioitinhhs, motahs, hinhthuc,
        chude, hanungtuyen, nghenghiepgs, gioitinhgs, truonggs, yeucaukhac, lichtrong];
}