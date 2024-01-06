var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListInvitationByTutor('.classInvited');
});

function viewDetailClass(classid) {
    window.location.href = '/chitietlop?id=' + classid;
}

function applyInvitation(lopid) {
    applyForClass($.cookie('id'), lopid, function () {
        location.reload();
    })
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var trangthailoimoi, statusLM;
        if (data[i].trangthailoimoi == 'THANHCONG') {
            trangthailoimoi = 'ĐÃ ỨNG TUYỂN';
            statusLM = 'xacthuc';
        } else if (data[i].trangthailoimoi == 'TUCHOI') {
            trangthailoimoi = "KHÔNG ỨNG TUYỂN";
            statusLM = 'dinhchi';
        } else {
            trangthailoimoi = 'CHỜ';
            statusLM = 'choxacthuc'
        }
        var huyhtml = `<button type="button" class="btn btn-outline-primary btn-sm" 
                            onClick="applyInvitation(${data[i].lopid})">Ứng tuyển</button>`
        datahtml += `<tr>
                        <td>${moment(data[i].ngaymoi).format('DD-MM-YYYY')}</td>
                        <td>${data[i].lopid}</td>
                        <td class='${statusLM}'>${trangthailoimoi}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].lopid})">Xem</button>
                        </td>
                        <td>
                            ${statusLM == 'choxacthuc' ? huyhtml : ''}
                        </td>
                    </tr>`
    }
    return datahtml;
}