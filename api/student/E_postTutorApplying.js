var listApply;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    var id = getParamFromURL('id');
    getAPIListApplyByClass(id, '.listApply');
});

function viewDetailTutor(tutorid) {
    window.location.href = '../home/E_candidate_details.html?id=' + tutorid;
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var trangthaiut, statusUT;
        if (data[i].trangthailop == 'THANHCONG') {
            trangthaiut = 'THÀNH CÔNG';
            statusUT = 'xacthuc';
        } else if (data[i].trangthailop == 'TUCHOI') {
            trangthaiut = "THẤT BẠI";
            statusUT = 'dinhchi';
        } else {
            trangthaiut = 'CHỜ';
            statusUT = 'choxacthuc'
        }
        var loimoi = data[i].loimoi != null ? 'loimoi' : '';
        datahtml += `<tr>
                        <td class="${loimoi}">${data[i].giasuid}</td>
                        <td class="${loimoi}">${data[i].tengs}</td>
                        <td class="${loimoi}">${data[i].ngayungtuyen}</td>
                        <td class="${statusUT} ${loimoi}">${trangthaiut}</td>
                        <td class="${loimoi}">
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailTutor(${data[i].giasuid})">Xem</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}