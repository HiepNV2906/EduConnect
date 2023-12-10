$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
});