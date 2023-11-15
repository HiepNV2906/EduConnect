$(document).ready(function () {
    if ($.cookie('role') != 'ADMIN') {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
});