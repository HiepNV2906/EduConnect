$(document).ready(function () {
    if ($.cookie('role') != 'MANAGER') {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
});