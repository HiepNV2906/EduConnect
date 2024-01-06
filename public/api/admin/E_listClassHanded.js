var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListClassByStatus("DAGIAO", '.listclasshanded');
});

function viewListTutorApplied(classid) {
    window.location.href = '/admin/lop/ketquaungtuyen?id=' + classid;
}

function viewDetailClass(classid) {
    window.location.href = '/admin/chitietlop?id=' + classid;
}

function handleDelete() {
    window.location.href = '/admin/lopdagiao';
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var dsungtuyen = data[i].dsungtuyen;
        var idgiasu;
        for (let j = 0; j < dsungtuyen.length; j++) {
            if (dsungtuyen[j].trangthaiungtuyen == 'THANHCONG') {
                idgiasu = dsungtuyen[j].giasuid;
                break;
            }
        }
        datahtml += `<tr>
                        <td>${moment(data[i].ngaygiao).format('DD-MM-YYYY')}</td>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewListTutorApplied(${data[i].id})">Xem</button>
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].id})">Xem lớp</button>
                            <button type="button" class="btn btn-outline-danger btn-sm" 
                                onClick="deleteAClass(${data[i].id}, ${handleDelete})">Xoá</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}