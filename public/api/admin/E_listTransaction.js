var listItem;
var totalPages;
var sizeOfPage = 20;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListTransaction('.listtransaction');
});

function viewDetailTransaction(transactionid) {
    window.location.href = '/admin/lichsugiaodich?id=' + transactionid
}

// function handleDelete() {
//     window.location.href = 'E_listStudent.html';
// }

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].nganhang}</td>
                        <td>${data[i].sotien}</td>
                        <td>${moment(data[i].ngaythanhtoan).format('DD-MM-YYYY HH:mm:ss')}</td>
                        <td>${data[i].noidung}</td>
                    </tr>`
    }
    return datahtml;
}