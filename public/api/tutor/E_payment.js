$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getDetailApply(id, createPaymentHTML);
});

$('#nutthanhtoan').click(function () {
    handlePay();
})

function handlePay() {
    var sotien = $('.sotien span').text();
    var noidung = $('.noidung textarea').val();
    var nganhang = 'ATM';

    if (noidung == '') {
        alert('Nhập nội dung thanh toán');
        return;
    }

    var ungtuyenid = getParamFromURL('id');

    var thanhtoan = {
        'nganhang': nganhang,
        'noidung': noidung,
        'sotien': sotien,
        'ungtuyenid': ungtuyenid
    }

    var jsonString = JSON.stringify(thanhtoan);

    payForDebt(jsonString, function (url) {
        window.location.href = url;
    })
}

function createPaymentHTML(data) {
    getDetailClass(data.lopid, function (lop) {
        var noidung = 'Thanh toan phi ung tuyen nhan lop ID ' + lop.id + '. So tien: ' + lop.phiungtuyen + '.';
        $('.sotien span').text(lop.phiungtuyen);
        $('.noidung textarea').val(noidung);
    })
}