var listPost;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListClassByHocVienAndStatus("DAGIAO", '.listclasshanded');
});

function viewTutorApply(classid) {
    window.location.href = 'E_postTutorApplying.html?id=' + classid;
}

function viewDetailClass(classid, edit = false) {
    if (edit) {
        window.location.href = '../home/E_job_details.html?id=' + classid;
    } else {
        window.location.href = 'E_editPost.html?id=' + classid;
    }
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var dsungtuyen = data[i].dsungtuyen;
        var idgiasu = -1;
        for (let j = 0; j < dsungtuyen.length; j++) {
            if (dsungtuyen[j].trangthaiungtuyen == 'THANHCONG') {
                idgiasu = dsungtuyen[j].giasuid;
                break;
            }
        }
        var trangthailop, statusClass;
        if (data[i].trangthailop == 'DAGIAO') {
            trangthailop = 'Đã giao';
            statusClass = 'xacthuc';
        } else {
            if (dsungtuyen.length == 0) {
                trangthailop = "Không có ứng tuyển";
                statusClass = 'dinhchi';
            } else {
                trangthailop = 'Đang xếp gia sư';
                statusClass = 'choxacthuc'
            }
        }
        var ketquahtml = `<button type="button" class="btn btn-outline-primary btn-sm" 
                            onClick="viewTutorApply(${data[i].id})">Xem</button>`
        datahtml += `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td class=${statusClass}>${trangthailop}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].id, statusClass == 'dinhchi'})">Xem</button>
                        </td>
                        <td>
                            ${idgiasu >= 0 ? ketquahtml : ''}
                        </td>
                    </tr>`
    }
    return datahtml;
}
