$(document).ready(function () {
    if ($.cookie('role') != 'MANAGER') {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
});