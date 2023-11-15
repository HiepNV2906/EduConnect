var listApply;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    var idLop = getParamFromURL('id');
    getAPIListApplyByClass(idLop, '.listapply');
});

function viewDetailApply(applyid) {
    window.location.href = 'E_detailApply.html?id=' + applyid;
}

function handleGiaoLop(id) {
    if (!confirm("Bạn muốn chọn gia sư nhận lớp?")) {
        return;
    }
    var token = $.cookie('token');
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token ? token : ''
        },
        method: "GET",
        url: "http://localhost:8080/api/ungtuyen/sapxepgiasu/" + id,
        success: function (response) {
            console.log(response);
            window.location.href = 'E_listTutorApplied.html?id=' + applyid;
        },
        error: function (xhr, status, error) {
            // Xử lý lỗi
            console.log(error);
            alert("Có lỗi xảy ra!!!");
        }
    });
}

// function handleDelete() {
//     window.location.href = 'E_listStudent.html';
// }

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
                                onClick="viewDetailApply(${data[i].id})">Xem</button>
                            <button type="button" class="btn btn-outline-success btn-sm" 
                                onClick="handleGiaoLop(${data[i].id})">Giao lớp</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}