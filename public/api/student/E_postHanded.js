var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListClassByHocVienAndFinishApply('.listclasshanded');
});

function viewTutorApply(classid) {
    window.location.href = '/hocvien/lop/ungtuyen?id=' + classid;
}

function viewDetailClass(classid, edit = false) {
    if (edit) {
        window.location.href = '/hocvien/thongtinlop?id=' + classid;
    } else {
        window.location.href = '/chitietlop?id=' + classid;
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
        var ketquahtml = `<button type="button" class="btn btn-outline-success btn-sm" 
                            onClick="viewTutorApply(${data[i].id})">Danh sách</button>`
        datahtml += `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td class=${statusClass}>${trangthailop}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].id}, ${statusClass == 'dinhchi'})">Xem</button>
                        </td>
                        <td>
                            ${dsungtuyen.length >= 0 ? ketquahtml : ''}
                        </td>
                    </tr>`
    }
    return datahtml;
}
