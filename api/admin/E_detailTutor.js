
$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getAccountTutor(id, createDataHTML);
    getAPIListSubject(() => {
        renderSubject('.mon select');
    })
});

$('#nutcapnhat').click(function () {
    handleUpdate();
})

$('#nutxoa').click(function () {
    var id = $('.maId p').text();
    deleteATutor(id, handleDelete);
})

$('#nutquaylai').click(function () {
    var id = getParamFromURL('id');
    getAccountTutor(id, createDataHTML);
})

$('.nghenghiep select').change(function (event) {
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

$('.nutthemchude button').click(function () {
    var mon = $('.mon select').val();
    var trinhdo = $('.trinhdo select').val();
    if (trinhdo == '') return;
    var trinhdotext = $('.trinhdo select option:selected').text();
    var h3Element = document.createElement('h3');
    h3Element.classList.add('ml-3');
    var h3Child = `<span id="${trinhdo}" class="badge badge-info chudetag">
                        <span>${mon + ' ' + trinhdotext}</span>
                        <button class="badge badge-secondary" onClick="handleDeleteTagChuDe('${trinhdo}')">X</button>
                    </span>`;
    h3Element.innerHTML = h3Child;
    document.querySelector('.dschude').appendChild(h3Element);
});

$(".mon select").change(function () {
    reloadSubjectLevel();
})

function renderSubject(cssSelector) {
    var monhocHTML = createSubjectFilterHTML(listSubjects);
    $(cssSelector).html(monhocHTML);
}

function reloadSubjectLevel() {
    var mon = $(".mon select").val();
    var data = createSubjectLevelFilterHTML(listLevelSubjects[mon]);
    $(".trinhdo select").html(data);
}

function handleDeleteTagChuDe(id) {
    document.getElementById(id).remove();
}

function handleDelete() {
    window.location.href = "E_listTutor.html";
}

function setQuanHuyen(value) {
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen select").html(quanhuyenHTML);
    $(".quanhuyen select").val(value);
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
    $(".quequan input").val(data.quequan);
    $(".truong input").val(data.truong);
    $(".gioithieu textarea").val(data.gioithieu);
    $(".kinhnghiem textarea").val(data.kinhnghiem);
    $(".thanhtich textarea").val(data.thanhtich);
    if (data.nghenghiep == 'Sinh viên' || data.nghenghiep == 'Gia sư') {
        $(".nghenghiep select").val(data.nghenghiep);
        $(".nghenghiepkhac").css({
            "display": "none"
        })
    } else {
        $(".nghenghiep select").val('Khác');
        $(".nghenghiepkhac").css({
            "display": "flex"
        })
        $(".nghenghiepkhac input").val(data.nghenghiep);
    }
    setQuanHuyen(data.quan);
    var khuvucday = data.khuvucday.split(", ");
    for (let i = 0; i < khuvucday.length; i++) {
        var cssSlt = 'input[type="checkbox"][value="' + khuvucday[i] + '"]';
        var checkboxA = $(cssSlt);
        checkboxA.prop('checked', true);
    }
    var dschude = data.dschude;
    document.querySelector('.dschude').innerHTML = ``;
    for (let i = 0; i < dschude.length; i++) {
        var h3Element = document.createElement('h3');
        h3Element.classList.add('ml-3');
        var h3Child = `<span id="${dschude[i].id}" class="badge badge-info chudetag">
                        <span>${dschude[i].tenmonhoc + ' ' + dschude[i].trinhdo}</span>
                        <button class="badge badge-secondary" onClick="handleDeleteTagChuDe('${dschude[i].id}')">X</button>
                        </span>`;
        h3Element.innerHTML = h3Child;
        document.querySelector('.dschude').appendChild(h3Element);
    }
    $(".trangthai select").val(data.trangthai)
}

function handleUpdate() {
    if (!validateInfo()) {
        return;
    }
    var hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi, cccd,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude;
    [hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi, cccd,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude] = validateInfo();

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
        'quequan': quequan,
        'nghenghiep': nghenghiep,
        'truong': truong,
        'gioithieu': gioithieu,
        'kinhnghiem': kinhnghiem,
        'thanhtich': thanhtich,
        'khuvucday': khuvuc,
        'dschude': chude,
        'trangthai': trangthai
    }
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    if (avata) {
        formData.append('avata', avata);
    }
    if (cccd) {
        formData.append('cccd', cccd);
    }

    updateAccountTutor(id, formData);
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
    var quequan = $(".quequan input").val();
    var nghenghiep = $(".nghenghiep select").val();
    var truong = $(".truong input").val();
    var gioithieu = $(".gioithieu textarea").val();
    var kinhnghiem = $(".kinhnghiem textarea").val();
    var thanhtich = $(".thanhtich textarea").val();

    if (hoten == '' || sdt == '' || email == '' || gioitinh == '' || ngaysinh == '' ||
        diachi == '' || quan == '' || quequan == '' || truong == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }

    var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    if (!email.match(validRegex)) {
        alert("Email không hợp lệ");
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
    $(".qhcheck:checked").each(function () {
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
    $(".chudetag").each(function () {
        var ma = $(this).attr('id');
        if (ma != '') {
            chude.push({ "id": ma })
        }
    });

    if (chude.length == 0) {
        alert("Vui lòng chọn chủ đề có thể dạy");
        return null;
    }

    chude = [...new Set(chude)];

    return [hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi, cccd,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude];
}