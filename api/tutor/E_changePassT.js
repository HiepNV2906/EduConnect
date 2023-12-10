$(document).ready(function () {
    if ($.cookie('role') != 'GIASU') {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
});