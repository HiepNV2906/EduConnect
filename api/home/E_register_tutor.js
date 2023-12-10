var quan = ['Ba Đình', 'Bắc Từ Liêm', 'Cầu Giấy', 'Đống Đa', 'Hà Đông', 'Hai Bà Trưng',
    'Hoàn Kiếm', 'Hoàng Mai', 'Long Biên', 'Nam Từ Liêm', 'Tây Hồ', 'Thanh Xuân']
var huyen = ['Ba Vì', 'Chương Mỹ', 'Đan Phượng', 'Đông Anh', 'Gia Lâm', 'Hoài Đức',
    'Mê Linh', 'Mỹ Đức', 'Phúc Thọ', 'Quốc Oai', 'Sóc Sơn', 'Sơn Tây',
    'Thạch Thất', 'Thanh Oai', 'Thanh Trì', 'Thường Tín', 'Ứng Hoà']


$(document).ready(function () {
    addListDistrictCheckbox(quan, '.quanHN');
    addListDistrictCheckbox(huyen, '.huyenHN');
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen").html(quanhuyenHTML);
    getAPIListSubject(() => {
        renderSubject('#subject_row1 .mon');
    })
});

// Handle event

$(".nghenghiep").change(function (event) {
    if (event.target.value == "Khác") {
        $(".nghenghiepkhac").css({
            "display": "flex"
        })
    } else {
        $(".nghenghiepkhac").css({
            "display": "none"
        })
    }
})

$("#nutdangky").click(function () {
    dangKyGiaSu();
});

function addListDistrictCheckbox(list, classElement) {
    var result = ``;
    list.forEach((value) => {
        result += `<div class="col-lg-3 col-md-4 col-sm-6 col-12 mt-10">
                                    <div class="row">
                                        <input type="checkbox" value="${value}" name="region" required class="single-input col-3 khuvuc">
                                        <span>${value}</span>
                                    </div>
                                </div>`
    })
    $(classElement).html(result);
}

function renderSubject(cssSelector) {
    var monhocHTML = createSubjectFilterHTML(listSubjects);
    $(cssSelector).html(monhocHTML);
}

function reloadSubjectLevel(idElement) {
    var idSelector = "#" + idElement;
    var mon = $(idSelector + " .mon").val();
    console.log(mon);
    console.log(listLevelSubjects);
    var data = createSubjectLevelFilterHTML(listLevelSubjects[mon]);
    $(idSelector + " .trinhdo").html(data);
}

function addNewSubject() {
    var listRow = $('.multi_subject .row').length + 1;
    var newId = "subject_row" + listRow;
    var newRow = `<div class="mt-10 row chude" id=${newId}>
                    <span class="col-lg-1 label_form"> ${listRow}:</span>
                    <div class="default-select col-lg-3" id="default-select">
                        <select class="mon" onchange="reloadSubjectLevel('${newId}')">
                            
                        </select>
                    </div>
                    <div class="default-select col-lg-3" id="default-select">
                        <select class="trinhdo">
                            
                        </select>
                    </div>
                    <span class="col-lg-1 genric-btn danger-border circle medium deleteSubjectBtn"
                            onclick="removeSubject('${newId}')">
                        x
                    </span>
                </div>`;
    $('.multi_subject').append(newRow);
    renderSubject('#' + newId + ' .mon');
    reloadSubjectLevel(newId);
}

function removeSubject(id) {
    $('#' + id).remove();
}

function dangKyGiaSu() {
    if (!validateInfo()) {
        return;
    }
    var hoten, sdt, email, password, avata, gioitinh, ngaysinh, quan, diachi, cccd,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude;
    [hoten, sdt, email, password, avata, gioitinh, ngaysinh, quan, diachi, cccd,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude] = validateInfo();

    var infoGS = {
        'hoten': hoten,
        'sdt': sdt,
        'email': email,
        'password': password,
        'gioitinh': gioitinh,
        'ngaysinh': ngaysinh,
        'quan': quan,
        'diachi': diachi,
        'quequan': quequan,
        'nghenghiep': nghenghiep,
        'truong': truong,
        'gioithieu': gioithieu,
        'kinhnghiem': kinhnghiem,
        'thanhtich': thanhtich,
        'khuvucday': khuvuc,
        'dschude': chude
    }
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    formData.append('avata', avata);
    formData.append('cccd', cccd);

    $.ajax({
        url: "http://localhost:8080/dangkygiasu",
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
    var quequan = $(".quequan").val();
    var nghenghiep = $(".nghenghiep").val();
    var truong = $(".truong").val();
    var gioithieu = $(".gioithieu").val();
    var kinhnghiem = $(".kinhnghiem").val();
    var thanhtich = $(".thanhtich").val();

    if (hoten == '' || sdt == '' || email == '' || password == '' ||
        repassword == '' || gioitinh == '' || ngaysinh == '' || diachi == '' ||
        quan == '' || quequan == '' || truong == '' || !avata || !cccd) {
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

    if (nghenghiep == 'Khác') {
        var nghenghiepkhac = $(".nghenghiepkhac input").val();
        if (nghenghiepkhac == '') {
            alert("Vui lòng điền đầy đủ thông tin nghề nghiệp");
            return null;
        } else {
            nghenghiep = nghenghiepkhac;
        }
    }

    var khuvuc = '';
    $(".khuvuc:checked").each(function () {
        var checkboxValue = $(this).val();
        khuvuc += checkboxValue + ", ";
    });
    if (khuvuc == '') {
        alert("Vui lòng chọn khu vực có thể dạy");
        return null;
    } else {
        khuvuc = khuvuc.slice(0, -2);
    }

    var chude = []
    $(".trinhdo").each(function () {
        var ma = $(this).val();
        if (ma != '') {
            chude.push({ "id": ma })
        }
    });

    if (chude.length == 0) {
        alert("Vui lòng chọn chủ đề có thể dạy");
        return null;
    }

    chude = [...new Set(chude)];

    return [hoten, sdt, email, password, avata, gioitinh, ngaysinh, quan, diachi, cccd,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude];
}
