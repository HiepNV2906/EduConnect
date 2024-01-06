var listItem;
var totalPages;
var sizeOfPage = 20;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListTransactionByGiaSu($.cookie('id'), '.listtransaction');
});

function viewDetailTransaction(transactionid) {
    window.location.href = '/giasu/giaodichthanhcong?id=' + transactionid
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].nganhang}</td>
                        <td>${data[i].sotien}</td>
                        <td>${moment(data.ngaythanhtoan).format('DD-MM-YYYY HH:mm:ss')}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailTransaction(${data[i].id})">Xem</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}