$(document).ready(function () {
    if ($.cookie('role') != 'GIASU') {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
});