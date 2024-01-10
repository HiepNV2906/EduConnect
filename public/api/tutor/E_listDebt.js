var listItem;
var totalPages;
var sizeOfPage = 20;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListApplyByDebtAndTutor($.cookie('id'), 'CHUATHANHTOAN', '.listdebt');
});

function pay(debtid) {
    window.location.href = '/giasu/thanhtoan?id=' + debtid;
}

function viewDetailClass(classid) {
    window.location.href = '/chitietlop?id=' + classid;
}

function viewDetailUngtuyen(ungtuyenid) {
    window.location.href = '/giasu/ungtuyen?id=' + ungtuyenid;
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].phinhanlop}</td>
                        <td>${moment(data[i].hanthanhtoan).format('DD-MM-YYYY')}</td>
                        <td>${data[i].noidungcongno}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailUngtuyen(${data[i].id})">Xem ứng tuyển</button>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="pay(${data[i].id})">Thanh toán</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}