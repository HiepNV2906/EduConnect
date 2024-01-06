

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    var to = new Date();
    $('.to input').val(moment(to).format('YYYY-MM-DD'));
    to.setMonth(to.getMonth() - 11);
    $('.from input').val(moment(to).format('YYYY-MM-DD'));
});

$('#nutxuatExcel').click(function () {
    var type = $('.loaibaocao select').val();
    if (type == 1) {
        console.log(type);
        report(type, exportDoanhthu);
        return;
    }
    if (type == 2) {
        console.log(type);
        report(type, exportNewTutor);
        return;
    }
});

function doDownload(url, namefile) {

    // Tạo một thẻ <a> ẩn để tải xuống tệp Excel
    const downloadLink = document.createElement('a');
    downloadLink.href = url;
    downloadLink.download = namefile;

    // Thêm thẻ <a> vào DOM và kích hoạt sự kiện click để tải xuống tệp Excel
    document.body.appendChild(downloadLink);
    downloadLink.click();

    // Xóa thẻ <a> sau khi tệp đã được tải xuống
    document.body.removeChild(downloadLink);
}

function exportDoanhthu(data) {
    console.log(data);
    var cost = [0, 0, 0, 0, 0, 0];
    var detail = []
    for (let i = 0; i < data.length; i++) {
        let index = Math.floor(data[i].hocphi / 100000);
        console.log(data[i].hocphi / 100000);
        if (index > 5) {
            index = 5;
        }
        cost[index] += data[i].phinhanlop;
        detail.push([data[i].mags, data[i].hotengs, data[i].malop, data[i].chudeday, data[i].hocphi,
        moment(data[i].ngaythanhtoan).format("DD-MM-YYYY"), data[i].phinhanlop])
    }
    const newData = {
        'from': moment($('.from input').val()).format('DD-MM-YYYY'),
        'to': moment($('.to input').val()).format('DD-MM-YYYY'),
        'cost': cost,
        'detail': detail
    }
    fetch('/exportdoanhthu', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Chỉ định kiểu dữ liệu là JSON
        },
        body: JSON.stringify(newData), // Chuyển đổi dữ liệu thành chuỗi JSON
    })
        .then(response => response.blob()) // Chuyển đổi phản hồi thành một đối tượng Blob
        .then(blob => {
            // Tạo URL đến blob
            const url = URL.createObjectURL(blob);
            doDownload(url, 'BCDoanhThu.xlsx');
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function exportNewTutor(data) {
    console.log(data);
    var detail = []
    for (let i = 0; i < data.length; i++) {
        let dschude = data[i].dschude;
        let chude = [];
        for (let j = 0; j < dschude.length; j++) {
            chude.push(dschude[j].tenmonhoc + ' ' + dschude[j].trinhdo);
        }
        detail.push([data[i].id, data[i].hoten, data[i].gioitinh, moment(data[i].ngaysinh).format("DD-MM-YYYY"),
        data[i].email, data[i].sdt, data[i].quan, data[i].diachi, chude.join(', '), data[i].khuvucday,
        moment(data[i].ngaytao).format("DD-MM-YYYY"), data[i].trangthai])
    }
    const newData = {
        'from': moment($('.from input').val()).format('DD-MM-YYYY'),
        'to': moment($('.to input').val()).format('DD-MM-YYYY'),
        'detail': detail
    }
    fetch('/exportgiasumoi', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json', // Chỉ định kiểu dữ liệu là JSON
        },
        body: JSON.stringify(newData), // Chuyển đổi dữ liệu thành chuỗi JSON
    })
        .then(response => response.blob()) // Chuyển đổi phản hồi thành một đối tượng Blob
        .then(blob => {
            // Tạo URL đến blob
            const url = URL.createObjectURL(blob);
            doDownload(url, 'Giasumoi.xlsx');
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
