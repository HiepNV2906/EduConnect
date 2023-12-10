$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getDetailTransaction(id, createHTML);
});

function createHTML(data) {
    var datahtml = `<p>Mã giao dịch: <b>${data.magiaodichnganhang}</b></p>
                    <p>Ngân hàng: <b>${data.nganhang}</b></p>
                    <p>Số tiền: <b>${data.sotien} VND</b></p>
                    <p>Ngày giao dịch: <b>${moment(data.ngaythanhtoan).format('DD-MM-YYYY HH:mm:ss')}</b></p>
                    <p>Nội dung: <b>${data.noidung}</b></p>`;
    $('.infoPay').html(datahtml);
}