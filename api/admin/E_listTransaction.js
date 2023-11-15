var listTransaction;
var totalPages;
var sizeOfPage = 20;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListTransaction('.listtransaction');
});

function viewDetailTransaction(transactionid) {
    window.location.href = 'E_listTransaction.html?id=' + transactionid
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
                        <td>${data[i].ngaythanhtoan}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailTransaction(${data[i].id})">Xem</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}