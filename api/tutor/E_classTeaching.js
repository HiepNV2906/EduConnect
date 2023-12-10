var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListClassByGiaSuAndStatusApply("THANHCONG", '.classTeaching');
});

function viewDetailClass(classid) {
    window.location.href = '../home/E_jobs_details.html?id=' + classid;
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${data[i].id}</td> 
                        <td>${data[i].tieude}</td>
                        <td>${data[i].chude.tenmonhoc + ' ' + data[i].chude.trinhdo}</td>
                        <td>${data[i].hocphi} VND</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].id})">Xem lớp</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}

