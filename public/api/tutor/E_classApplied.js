var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListApplyByTutor($.cookie('id'), '.classApplied');
});

function viewDetailClass(classid) {
    window.location.href = '/chitietlop?id=' + classid;
}

function cancelApplication(id) {
    deleteAApplication(id, function () {
        location.reload();
    })
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var trangthaiungtuyen, statusLM;
        if (data[i].trangthaiungtuyen == 'THANHCONG') {
            trangthaiungtuyen = 'THÀNH CÔNG';
            statusLM = 'xacthuc';
        } else if (data[i].trangthaiungtuyen == 'TUCHOI') {
            trangthaiungtuyen = "THẤT BẠI";
            statusLM = 'dinhchi';
        } else {
            trangthaiungtuyen = 'CHỜ';
            statusLM = 'choxacthuc'
        }
        var huyhtml = `<button type="button" class="btn btn-outline-primary btn-sm" 
                            onClick="cancelApplication(${data[i].id})">Huỷ UT</button>`
        datahtml += `<tr>
                        <td>${data[i].lopid}</td>
                        <td>${data[i].tieudelop}</td>
                        <td>${moment(data[i].ngayungtuyen).format('DD-MM-YYYY')}</td>
                        <td class='${statusLM}'>${trangthaiungtuyen}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].lopid})">Xem lớp</button>
                            ${statusLM == 'choxacthuc' ? huyhtml : ''}
                        </td>
                    </tr>`
    }
    return datahtml;
}