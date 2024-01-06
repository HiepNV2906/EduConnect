

$(document).ready(function () {
    if (!$.cookie('id')) {
        window.location.href = '/dangnhap';
    }
    loadHeaderWraper();
    loadinit();
});

$('#nutthemmoi').click(function () {
    handleAdd();
})

$('#nutcapnhat').click(function () {
    handleUpdate();
})

$('#nutcapnhat').click(function () {
    loadinit();
})

function loadinit() {
    var id = getParamFromURL('id');
    if (id) {
        getAccountAdmin(id, function (data) {
            var srcAvata = data.avata ? 'http://localhost:8080/uploads/images/' + data.avata : '../../img/avatadefault.png';
            $(".avata img").attr('src', srcAvata);
            $(".hoten input").val(data.hoten);
            $(".emailacc input").val(data.email);
            $(".gioitinh select").val(data.gioitinh);
            $(".ngaysinh input").val(moment(data.ngaysinh).format("YYYY-MM-DD"));
            $(".sdt input").val(data.sdt);
            $('.title-content strong').text('Cập nhật thông tin Admin')
            $('#nutthemmoi').hide();
            $('#nutcapnhat').show();
        })
    } else {
        var srcAvata = '../../img/avatadefault.png';
        $(".avata img").attr('src', srcAvata);
        $(".hoten input").val('');
        $(".emailacc input").val('');
        $(".ngaysinh input").val('');
        $(".sdt input").val('');
        $('.title-content strong').text('Thêm tài khoản Admin');
        $('#nutthemmoi').show();
        $('#nutcapnhat').hide();
    }
}

function handleAdd() {
    if (!validateInfo()) {
        return;
    }

    var hoten, email, gioitinh, ngaysinh, sdt, avata;
    [hoten, email, gioitinh, ngaysinh, sdt, avata] = validateInfo();

    var infoGS = {
        'id': 0,
        'hoten': hoten,
        'sdt': sdt,
        'email': email,
        'gioitinh': gioitinh,
        'ngaysinh': ngaysinh
    }

    console.log(infoGS);
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    if (avata) {
        formData.append('avata', avata);
    }

    addAccountAdmin(formData);
}

function handleUpdate() {
    if (!validateInfo()) {
        return;
    }
    var id = getParamFromURL('id');

    var hoten, email, gioitinh, ngaysinh, sdt, avata;
    [hoten, email, gioitinh, ngaysinh, sdt, avata] = validateInfo();

    var infoGS = {
        'id': id,
        'hoten': hoten,
        'sdt': sdt,
        'email': email,
        'gioitinh': gioitinh,
        'ngaysinh': ngaysinh
    }

    console.log(infoGS);
    var jsonString = JSON.stringify(infoGS);

    var formData = new FormData();
    formData.append('infoGS', jsonString);
    if (avata) {
        formData.append('avata', avata);
    }

    updateAccountAdmin(id, formData);
}

function validateInfo() {
    var hoten = $(".hoten input").val();
    var email = $(".emailacc input").val();
    var gioitinh = $(".gioitinh select").val();
    var ngaysinh = $(".ngaysinh input").val();
    var sdt = $(".sdt input").val();
    var avata = $(".ipavata").prop('files')[0];

    if (hoten == '' || email == '' || gioitinh == '' || ngaysinh == '' || sdt == '') {
        alert("Vui lòng điền đầy đủ thông tin");
        return null;
    }

    return [hoten, email, gioitinh, ngaysinh, sdt, avata];
}