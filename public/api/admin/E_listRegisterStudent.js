var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListStudentByStatus("CHUAPHEDUYET", '.liststudent');
});

function viewDetailStudent(studentid) {
    window.location.href = '/admin/chitiethocvien?id=' + studentid;
}

function handleDelete() {
    window.location.href = '/admin/dangkyhocvien';
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
                                onClick="viewDetailStudent(${data[i].id})">Xem</button>
                            <button type="button" class="btn btn-outline-danger btn-sm" 
                                onClick="deleteAStudent(${data[i].id}, ${handleDelete})">Xoá</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}