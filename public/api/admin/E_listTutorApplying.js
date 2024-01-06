var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    var idLop = getParamFromURL('id');
    getAPIListApplyByClass(idLop, '.listapply');
});

function viewDetailApply(applyid) {
    window.location.href = '/admin/chitietungtuyen?id=' + applyid;
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
            var idLop = getParamFromURL('id');
            window.location.href = '/admin/lop/ketquaungtuyen?id=' + idLop;
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
    $('.malop').text('Mã lớp: ' + data[start].lopid);
    $('.tieudelop').text('Tiêu đề lớp: ' + data[start].tieudelop);
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var loimoi = data[i].loimoi != null ? 'loimoi' : '';
        datahtml += `<tr>
                        <td class="${loimoi}">${data[i].id}</td>
                        <td class="${loimoi}">${data[i].tengs}</td>
                        <td class="${loimoi}">${data[i].emailgs}</td>
                        <td class="${loimoi}">${data[i].sdtgs}</td>
                        <td class="${loimoi}">
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailApply(${data[i].id})">Xem UT</button>
                            <button type="button" class="btn btn-outline-success btn-sm" 
                                onClick="handleGiaoLop(${data[i].id})">Giao lớp</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}