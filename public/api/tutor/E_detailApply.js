$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getDetailApply(id, callbackHTML)
});

function callbackHTML(data) {
    console.log(data);
    createApplyHTML(data);
    createPaymentHTML(data.thanhtoan);
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
    $('.ungtuyen .ngayungtuyen p').text(moment(data.ngayungtuyen).format('DD-MM-YYYY'));
    $('.ungtuyen .loimoiut p').text(data.loimoi ? 'Có  (' + moment(data.loimoi.ngaymoi).format('DD-MM-YYYY') + ')' : 'Không');
    $('.ungtuyen .trangthaiungtuyen p').text(ttut);
    $('.ungtuyen .trangthaicongno p').text(ttcn);
    $('.ungtuyen .hanthanhtoan p').text(data.hanthanhtoan ? moment(data.hanthanhtoan).format('DD-MM-YYYY') : '');
}

function createPaymentHTML(data) {
    if (!data) {
        $('.thanhtoan').html(`<label class=" form-control-label choxacthuc">Không có giao dịch</label>`);
        return;
    }
    $('.thanhtoan .mathanhtoan p').text(data.id);
    $('.thanhtoan .nganhang p').text(data.nganhang);
    $('.thanhtoan .magiaodich p').text(data.magiaodichnganhang);
    $('.thanhtoan .sotien p').text(data.sotien);
    $('.thanhtoan .ngaythanhtoan p').text(moment(data.ngaythanhtoan).format('DD-MM-YYYY'));
    $('.thanhtoan .noidung p').text(data.noidung);
}

function createClassHTML(data) {
    $('.lop .malop p').text(data.id);
    $('.lop .tieude p').text(data.tieude);
    $('.lop .chude p').text(data.chude.tenmonhoc + ' ' + data.chude.trinhdo);
    $('.lop .sobuoi p').text(data.sobuoi);
    $('.lop .sogio p').text(data.sogio);
    $('.lop .diachi p').text(data.diachi + ', ' + data.quan);
    $('.lop .hocphi p').text(data.hocphi + " VND");
    $('.lop .phiungtuyen p').text(data.phiungtuyen + " VND");
    $('.lop .hanungtuyen p').text(moment(data.truong).format('DD-MM-YYYY'));
    $('.lop .hinhthuc p').text(data.hinhthuc);
    $('.lop .sohs p').text(data.sohs);
    $('.lop .gioitinhhs p').text(data.gioitinhhs);
    $('.lop .motahs p').text(data.motahs);
    $('.lop .nghenghiepgs p').text(data.nghenghiepgs);
    $('.lop .gioitinhgs p').text(data.gioitinhgs);
    $('.lop .truonggs p').text(data.truonggs);
    $('.lop .yeucaukhac p').text(data.yeucaukhac);
}