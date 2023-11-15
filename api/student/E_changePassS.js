$(document).ready(function () {
    if ($.cookie('role') != 'HOCVIEN') {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
});