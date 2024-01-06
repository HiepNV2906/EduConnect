var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getAPIListClassByStatus("CANGIAO", '.listclasshanding');
});

function viewTutorApply(classid) {
    window.location.href = '/admin/lop/ungtuyen?id=' + classid;
}

function viewDetailClass(classid) {
    window.location.href = '/admin/chitietlop?id=' + classid;
}

function handleDelete() {
    window.location.href = '/admin/lopcangiao';
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        var dsutbtn = `<button type="button" class="btn btn-outline-success btn-sm" 
                        onClick="viewTutorApply(${data[i].id})">Xem</button>`
        datahtml += `<tr>
                        <td>${moment(data[i].hanungtuyen).format('DD-MM-YYYY')}</td>
                        <td>${data[i].id}</td>
                        <td>${data[i].tieude}</td>
                        <td class="${data[i].dsungtuyen.length > 0 ? 'process' : 'denied'}">
                            ${data[i].dsungtuyen.length}/6
                            ${data[i].dsungtuyen.length > 0 ? dsutbtn : ''}
                        </td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="viewDetailClass(${data[i].id})">Xem</button>
                            <button type="button" class="btn btn-outline-danger btn-sm" 
                                onClick="deleteAClass(${data[i].id}, ${handleDelete})">Xoá</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}