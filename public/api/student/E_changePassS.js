$(document).ready(function () {
    if ($.cookie('role') != 'HOCVIEN') {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
});