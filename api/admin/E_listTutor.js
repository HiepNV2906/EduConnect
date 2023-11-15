
var listTutor;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListTutorByStatus("DAPHEDUYET", '.listtutor');
});

function viewDetailTutor(tutorid) {
    window.location.href = 'E_detailTutor.html?id=' + tutorid;
}

function handleDelete() {
    window.location.href = 'E_listTutor.html';
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].hoten}</td>
                        <td>${data[i].email}</td>
                        <td>${data[i].sdt}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailTutor(${data[i].id})">Xem</button>
                            <button type="button" class="btn btn-outline-danger btn-sm" 
                                onClick="deleteATutor(${data[i].id}, ${handleDelete})">Xoá</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}