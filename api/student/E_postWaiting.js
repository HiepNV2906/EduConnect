var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '../E_login.html';
    }
    loadHeaderWraper();
    getAPIListClassByHocVienAndStatus("CHOPHEDUYET", '.listclasswaiting');
});

function viewDetailClass(classid) {
    window.location.href = 'E_editPost.html?id=' + classid;
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${moment(data[i].ngaytao).format('DD-MM-YYYY')}</td>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td>${data[i].quan}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].id})">Xem lớp</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}

