var listInvitation;
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
        var huyhtml = `<button type="button" class="btn btn-outline-primary btn-sm" 
                            onClick="cancelInvitation(${data[i].id})">Huỷ</button>`
        datahtml += `<tr>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailTutor(${data[i].giasuid})">Xem</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].lopid})">Xem</button>
                        </td>
                        <td>${moment(data[i].ngaymoi).format('DD-MM-YYYY')}</td>
                        <td class='${statusLM}'>${trangthailoimoi}</td>
                        <td>
                            ${statusLM == 'choxacthuc' ? huyhtml : ''}
                        </td>
                    </tr>`
    }
    return datahtml;
}