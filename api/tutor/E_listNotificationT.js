var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListNotificationByUser('.listnotification');
});

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<a href="${data[i].link ? data[i].link : ''}" 
                        class="anotify sufee-alert alert with-close alert-success alert-dismissible fade show">
                        <span class="badge badge-pill badge-danger">${data[i].trangthaithongbao == 'CHUAXEM' ? 'New' : ''}</span>
                        <span class="titlenotify">${data[i].tieude}</span>
                        <span class="timenotify">${moment(data[i].ngay).format("DD-MM-YYYY HH:mm:ss")}</span>
                        <p>${data[i].noidung}</p>
                    </a>`
    }
    return datahtml;
}
