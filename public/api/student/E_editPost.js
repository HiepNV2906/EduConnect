
$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getDetailClass(id, createDataHTML);
});

$('#nutcapnhat').click(function () {
    handleUpdate();
})

$('#nutxoa').click(function () {
    var id = $('.maId p').text();
    deleteAClass(id, handleDelete);
})

$('#nutquaylai').click(function () {
    var id = getParamFromURL('id');
    getDetailClass(id, createDataHTML);
})

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

function setQuanHuyen(value) {
    var quanhuyenHTML = createDistrictFilterHTML();
    $(".quanhuyen select").html(quanhuyenHTML);
    $(".quanhuyen select").val(value);
}

function handleDelete() {
    window.location.href = "/hocvien/lopdangtim";
}

function createDataHTML(data) {
    if (data.trangthailop != 'CHOPHEDUYET' && !(data.trangthailop == 'CANGIAO' && data.dsungtuyen.length == 0)) {
        alert('Không thể sửa thông tin lớp đang tìm hoặc đã giao');
        window.location.href = '/hocvien';
    }
    getAPIListSubject(() => {
        renderSubject('.mon select');
        $(".mon select").val(data.chude.tenmonhoc);
        reloadSubjectLevel();
        $(".trinhdo select").val(data.chude.id);
    })
    $(".maId").html(`<p class="form-control-static">${data.id}</p>`);
    $(".tieude input").val(data.tieude);
    $(".diachi input").val(data.diachi);
    $(".hocphi input").val(data.hocphi);
    $(".phiungtuyen input").val(data.phiungtuyen);
    $(".sobuoi input").val(data.sobuoi);
    $(".sogio input").val(data.sogio);
    $(".sohs input").val(data.sohs);
    $(".gioitinhhs select").val(data.gioitinhhs);
    $(".motahs textarea").val(data.motahs);
    $(".hinhthuc select").val(data.hinhthuc);
    $(".hanungtuyen input").val(moment(data.hanungtuyen).format("YYYY-MM-DD"));
    $(".nghenghiepgs select").val(data.nghenghiepgs);
    $(".gioitinhgs select").val(data.gioitinhgs);
    $(".truonggs input").val(data.truonggs);
    $(".yeucaukhac textarea").val(data.yeucaukhac);
    setQuanHuyen(data.quan);
    var lichhtml = ``;
    var lich = data.dslichtrong
    for (let i = 0; i < lich.length; i++) {
        lichhtml += `<div id="${lich[i].thu}" class="row ml-2 mb-2 time-in-day">
                        <span>Thứ ${lich[i].thu == '8' ? 'CN' : lich[i].thu}</span>
                        <div class="form-check-inline form-check">
                            <label for="inline-checkbox1" class="form-check-label ml-5">
                                <input type="checkbox" id="inline-checkbox1"
                                    name="inline-checkbox1" value="sang" ${lich[i].sang == '1' ? 'checked' : ''}
                                    class="form-check-input">Sáng
                            </label>
                            <label for="inline-checkbox2" class="form-check-label ml-5">
                                <input type="checkbox" id="inline-checkbox2"
                                    name="inline-checkbox2" value="chieu" ${lich[i].chieu == '1' ? 'checked' : ''}
                                    class="form-check-input">Chiều
                            </label>
                            <label for="inline-checkbox3" class="form-check-label ml-5">
                                <input type="checkbox" id="inline-checkbox3" ${lich[i].toi == '1' ? 'checked' : ''}
                                    name="inline-checkbox3" value="toi"
                                    class="form-check-input">Tối
                            </label>
                        </div>
                    </div>`
    }
    $(".lichtrong").html(lichhtml);
    if (data.trangthailop == 'CHOPHEDUYET') {
        $('.trangthai').html(`<p class="form-control-static choxacthuc">CHỜ PHÊ DUYỆT</p>`)
    } else {
        $('.trangthai').html(`<p class="form-control-static dinhchi">KHÔNG CÓ ỨNG TUYỂN</p>`)
    }
}

function handleUpdate() {
    if (!validateInfo()) {
        return;
    }
    var tieude, quan, diachi, hocphi, phiungtuyen, sobuoi, sogio, sohs, gioitinhhs, motahs, hinhthuc,
        chude, hanungtuyen, nghenghiepgs, gioitinhgs, truonggs, yeucaukhac, lichtrong;
    [tieude, quan, diachi, hocphi, phiungtuyen, sobuoi, sogio, sohs, gioitinhhs, motahs, hinhthuc,
        chude, hanungtuyen, nghenghiepgs, gioitinhgs, truonggs, yeucaukhac, lichtrong] = validateInfo();

    if (phiungtuyen == '') {
        phiungtuyen = null;
    }

    var id = $('.maId p').text();

    var infoLop = {
        "id": id,
        "tieude": tieude,
        "quan": quan,
        "diachi": diachi,
        "sobuoi": sobuoi,
        "sogio": sogio,
        "hocphi": hocphi,
        "phiungtuyen": phiungtuyen,
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
        "chude": {
            "id": chude
        },
        "trangthailop": "CHOPHEDUYET"
    }
    var jsonString = JSON.stringify(infoLop);

    updateDetailClass(id, jsonString);
}

function validateInfo() {
    var tieude = $(".tieude input").val();
    var quan = $(".quanhuyen select").val();
    var diachi = $(".diachi input").val();
    var hocphi = $(".hocphi input").val();
    var phiungtuyen = $(".phiungtuyen input").val();
    var sobuoi = $(".sobuoi input").val();
    var sogio = $(".sogio input").val();
    var sohs = $(".sohs input").val();
    var gioitinhhs = $(".gioitinhhs select").val();
    var motahs = $(".motahs textarea").val();
    var hinhthuc = $(".hinhthuc select").val();
    var chude = $(".trinhdo select").val();
    var hanungtuyen = $(".hanungtuyen input").val();
    var nghenghiepgs = $(".nghenghiepgs select").val();
    var gioitinhgs = $(".gioitinhgs select").val();
    var truonggs = $(".truonggs input").val();
    var yeucaukhac = $(".yeucaukhac textarea").val();

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

    return [tieude, quan, diachi, hocphi, phiungtuyen, sobuoi, sogio, sohs, gioitinhhs, motahs, hinhthuc,
        chude, hanungtuyen, nghenghiepgs, gioitinhgs, truonggs, yeucaukhac, lichtrong];
}