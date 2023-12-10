var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListInvitationByStudent('.listInvited');
});

function viewDetailTutor(tutorid) {
    window.location.href = '../home/E_candidate_details.html?id=' + tutorid;
}

function viewDetailClass(classid) {
    window.location.href = '../home/E_job_details.html?id=' + classid;
}

function cancelInvitation(id) {
    deleteAInvitation(id, function () {
        location.reload();
    });
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var trangthailoimoi, statusLM;
        if (data[i].trangthailoimoi == 'THANHCONG') {
            trangthailoimoi = 'Thành công';
            statusLM = 'xacthuc';
        } else if (data[i].trangthailoimoi == 'TUCHOI') {
            trangthailoimoi = "Thất bại";
            statusLM = 'dinhchi';
        } else {
            trangthailoimoi = 'Chờ';
            statusLM = 'choxacthuc'
        }
        var huyhtml = `<button type="button" class="btn btn-outline-danger btn-sm" 
                            onClick="cancelInvitation(${data[i].id})">Huỷ mời</button>`
        datahtml += `<tr>
                        <td>${moment(data[i].ngaymoi).format('DD-MM-YYYY')}</td>
                        <td>${data[i].tengs}</td>
                        <td>${data[i].tieudelop}</td>
                        <td class='${statusLM}'>${trangthailoimoi}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailTutor(${data[i].giasuid})">Xem GS</button>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].lopid})">Xem lớp</button>
                            ${statusLM == 'choxacthuc' ? huyhtml : ''}
                        </td>
                    </tr>`
    }
    return datahtml;
}