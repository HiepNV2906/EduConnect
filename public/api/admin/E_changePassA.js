$(document).ready(function () {
    if ($.cookie('role') != 'ADMIN') {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
});