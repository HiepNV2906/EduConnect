var listItem;
var totalPages;
var sizeOfPage = 10;

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    getListSubject('.listsubject');
});

$('#nutthemchude').click(function () {
    handleAdd();
})

function handleAdd() {
    var mon = $('.tenmonhoc input').val();
    var trinhdo = $('.trinhdo input').val();

    if (mon == '' || trinhdo == '') {
        alert('Vui lòng điền đầy đủ thông tin');
        return
    }

    var chude = {
        'tenmonhoc': mon,
        'trinhdo': trinhdo
    }

    var jsonString = JSON.stringify(chude);

    addASubject(jsonString);

}

function deleteSubject(id) {
    deleteASubject(id, function () {
        location.reload();
    })
}

function createListDataHTML(data, start, end) {
    var datahtml = ``;
    for (let i = start; i < end; i++) {
        datahtml += `<tr>
                        <td>${data[i].id}</td>
                        <td>${data[i].tenmonhoc}</td>
                        <td>${data[i].trinhdo}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm" 
                                onClick="deleteSubject(${data[i].id})">Xoá</button>
                        </td>
                    </tr>`
    }
    return datahtml;
}