
$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAccountTutor($.cookie('id'), createDataHTML);
});

$('#nutcapnhat').click(function () {
    if (confirm('Lưu thay đổi?')) {
        handleUpdate();
    }
})

$('#nutquaylai').click(function () {
    getAccountTutor($.cookie('id'), createDataHTML);
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

function setQuanHuyen(value) {
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen select").html(quanhuyenHTML);
    $(".quanhuyen select").val(value);
}

function createDataHTML(data) {
    $(".maId p").text(data.id);
    $(".avata img").attr('src', data.avata ? 'http://localhost:8080/uploads/images/' + data.avata :
        '../../img/avatadefault.png');
    $(".cccd img").attr('src', 'http://localhost:8080/uploads/images/' + data.cccd);
    $(".hoten p").text(data.hoten);
    $(".emailacc p").text(data.email);
    $(".gioitinh p").text(data.gioitinh);
    $(".ngaysinh p").text(moment(data.ngaysinh).format('DD-MM-YYYY'));
    $(".sdt input").val(data.sdt);
    $(".diachi input").val(data.diachi);
    $(".quequan p").text(data.quequan);
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
                        </span>`;
        h3Element.innerHTML = h3Child;
        document.querySelector('.dschude').appendChild(h3Element);
    }
    var trangthai;
    if (data.trangthai == 'CHUAPHEDUYET') {
        trangthai = `<p class="form-control-static choxacthuc">CHƯA PHÊ DUYỆT</p>`;
    } else if (data.trangthai == 'DAPHEDUYET') {
        trangthai = `<p class="form-control-static xacthuc">ĐÃ PHÊ DUYỆT</p>`;
    } else {
        trangthai = `<p class="form-control-static dinhchi">ĐÌNH CHỈ</p>`;
    }
    $('.trangthai').html(trangthai);
}

function handleUpdate() {
    if (!validateInfo()) {
        return;
    }
    var hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude;
    [hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude] = validateInfo();

    var id = $('.maId p').text();

    var ngaysinh = $('.ngaysinh p').text();
    var formattedDate = convertDateYYYYMMDD(ngaysinh);

    var infoGS = {
        'id': id,
        'hoten': hoten,
        'sdt': sdt,
        'email': email,
        'gioitinh': gioitinh,
        'ngaysinh': formattedDate,
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
    if (avata) {
        formData.append('avata', avata);
    }

    updateAccountTutor(id, formData);
}

function validateInfo() {
    var hoten = $(".hoten p").text();
    var sdt = $(".sdt input").val();
    var email = $(".emailacc p").text();
    var avata = $(".ipavata").prop('files')[0];
    var gioitinh = $(".gioitinh p").text();
    var ngaysinh = $(".ngaysinh p").text();
    var quan = $(".quanhuyen select").val();
    var diachi = $(".diachi input").val();
    var quequan = $(".quequan p").text();
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

    return [hoten, sdt, email, avata, gioitinh, ngaysinh, quan, diachi,
        quequan, nghenghiep, truong, gioithieu, kinhnghiem, thanhtich, khuvuc, chude];
}