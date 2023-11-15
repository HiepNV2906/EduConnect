$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getDetailApply(id, callbackHTML)
});

$('#nutgiaolop').click(function () {
    var id = getParamFromURL('id');
    selectTutorForClass(id, function () {
        location.reload();
    });
})

function callbackHTML(data) {
    createApplyHTML(data);
    createPaymentHTML(data.thanhtoan);
    getAccountTutor(data.giasuid, createTutorHTML);
    getDetailClass(data.lopid, createClassHTML);
}

function createApplyHTML(data) {
    var ttut = data.trangthaiungtuyen;
    if (ttut == 'CHO') {
        ttut = 'Chờ';
    } else if (ttut == 'THANHCONG') {
        ttut = 'Thành công';
    } else {
        ttut = 'Thất bại'
    }
    var ttcn = data.trangthaicongno;
    if (ttcn == 'KHONG') {
        ttcn = 'Không';
    } else if (ttcn == 'DATHANHTOAN') {
        ttcn = 'Đã thanh toán';
    } else if (ttcn == 'CHUATHANHTOAN') {
        ttcn = 'Chưa thanh toán';
    }
    $('.ungtuyen .maungtuyen p').text(data.id);
    $('.ungtuyen .ngayungtuyen p').text(data.ngayungtuyen);
    $('.ungtuyen .loimoi p').text(data.loimoi ? 'Có - ' + moment(data.loimoi.ngaymoi).format('DD-MM-YYYY') : 'Không');
    $('.ungtuyen .trangthaiungtuyen p').text(ttut);
    $('.ungtuyen .trangthaicongno p').text(ttcn);
    $('.ungtuyen .hanthanhtoan p').text(moment(data.hanthanhtoan).format('DD-MM-YYYY'));
}

function createPaymentHTML(data) {
    $('.thanhtoan .mathanhtoan p').text(data.id);
    $('.thanhtoan .nganhang p').text(data.nganhang);
    $('.thanhtoan .magiaodich p').text(data.magiaodich);
    $('.thanhtoan .sotien p').text(data.sotien);
    $('.thanhtoan .ngaythanhtoan p').text(moment(data.ngaythanhtoan).format('DD-MM-YYYY'));
    $('.thanhtoan .noidung p').text(data.noidung);
}

function createTutorHTML(data) {
    $('.giasu .magiasu p').text(data.id);
    $('.giasu .hoten p').text(data.hoten);
    $('.giasu .sdt p').text(data.sdt);
    $('.giasu .gioitinh p').text(data.gioitinh);
    $('.giasu .diachi p').text(data.diachi + ', ' + data.quan);
    $('.giasu .quequan p').text(data.quequan);
    $('.giasu .nghenghiep p').text(data.nghenghiep);
    $('.giasu .truong p').text(data.truong);
    $('.giasu .khuvucday p').text(data.khuvucday);
    $('.giasu .gioithieu p').text(data.gioithieu);
    $('.giasu .kinhnghiem p').text(data.kinhnghiem);
    $('.giasu .thanhtich p').text(data.thanhtich);
    var chudeday = [];
    var dscd = data.dschude;
    for (let i = 0; i < dscd.length; i++) {
        chudeday.push(dscd[i].tenmonhoc + ' ' + dscd[i].trinhdo);
    }
    $('.giasu .chudeday p').text(chudeday.join(', '));
}

function createClassHTML(data) {
    $('.giasu .malop p').text(data.id);
    $('.giasu .tieude p').text(data.hoten);
    $('.giasu .chude p').text(data.chude.tenmonhoc + ' ' + data.chude.trinhdo);
    $('.giasu .sobuoi p').text(data.sobuoi);
    $('.giasu .sogio p').text(data.sogio);
    $('.giasu .diachi p').text(data.diachi + ', ' + data.quan);
    $('.giasu .hocphi p').text(data.hocphi);
    $('.giasu .phiungtuyen p').text(data.phiungtuyen);
    $('.giasu .hanungtuyen p').text(moment(data.truong).format('DD-MM-YYYY'));
    $('.giasu .hinhthuc p').text(data.hinhthuc);
    $('.giasu .sohs p').text(data.sohs);
    $('.giasu .gioitinhhs p').text(data.gioitinhhs);
    $('.giasu .motahs p').text(data.motahs);
    $('.giasu .nghenghiepgs p').text(data.nghenghiepgs);
    $('.giasu .gioitinhgs p').text(data.gioitinhgs);
    $('.giasu .truonggs p').text(data.truonggs);
    $('.giasu .yeucaukhac p').text(data.yeucaukhac);
}