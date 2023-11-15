var listPost;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListClassByStatus("DAGIAO", '.listclasshanded');
});

function viewTutorApply(tutorid) {
    window.location.href = 'E_detailTutor.html?id=' + tutorid;
}

function viewDetailClass(classid) {
    window.location.href = 'E_detailClass.html?id=' + classid;
}

function handleDelete() {
    window.location.href = 'E_listClassHanded.html';
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
                        <td>${data[i].ngaygiao}</td>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewTutorApply(${idgiasu})">Xem gia sư</button>
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