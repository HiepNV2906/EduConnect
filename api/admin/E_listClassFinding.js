var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListClassByStatus("DANGTIM", '.listclassfinding');
});

function viewTutorApply(classid) {
    window.location.href = 'E_listTutorApplying.html?id=' + classid;
}

function viewDetailClass(classid) {
    window.location.href = 'E_detailClass.html?id=' + classid;
}

function handleDelete() {
    window.location.href = 'E_listClassFinding.html';
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${moment(data[i].hanungtuyen).format('DD-MM-YYYY')}</td>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td class="process">
                            ${data[i].dsungtuyen.length}/6
                            <button type="button" class="btn btn-outline-success btn-sm" 
                                onClick="viewTutorApply(${data[i].id})">Xem</button>
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