var listItem;
var totalPages;
var sizeOfPage = 20;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListApplyByDebt('CHUATHANHTOAN', '.listdebt');
});

function viewDetailTutor(tutorid) {
    window.location.href = 'E_detailTutor.html?id=' + tutorid;
}

function viewDetailClass(classid) {
    window.location.href = 'E_detailClass.html?id=' + classid;
}

function viewDetailUngtuyen(ungtuyenid) {
    window.location.href = 'E_detailApply.html?id=' + ungtuyenid;
}

// function handleDelete() {
//     window.location.href = 'E_listNotificationA.html';
// }

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${data[i].giasuid}</td>
                        <td>${data[i].tengs}</td>
                        <td>${data[i].phinhanlop}</td>
                        <td>${moment(data[i].hanthanhtoan).format('DD-MM-YYYY')}</td>
                        <td>${data[i].noidungcongno}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailUngtuyen(${data[i].id})">Xem</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}